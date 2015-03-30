An easy way to store & fetch objects on disk in android.

NOTE:
This fork makes everything return observables which need to be subscribed to for the computation to start.

Usage:
```java
//Storing:
Database.with(context).saveObject(obj).subscribe();

//Fetching with id:
Database.with(context).load(TYPE.person).withId(id).execute().subscribe();

//Fetching with tags:
Database.with(context).load(TYPE.person).tagEquals("name", "john").execute().subscribe();

//Fetching a list of objects sorted by ts:
Database.with(context).load(TYPE.person).orderByTs(SORT_ORDER.DESC).limit(20).execute().subscribe();
```

Here obj implements ```Database.StoredObject```
and TYPE implements ```Database.StoredObject.TYPE```

Example:
```java
public enum TYPE implements Database.StoredObject.TYPE {
  person(Person.class);
  
  private Class cls;
  
  TYPE(Class cls) {
    this.cls = cls;
  }
  
  @Override
  public Class getTypeClass() {
    return cls;
  }
  
  @Override
  public String getTypeName() {
    return name();
  }
}
```

```java
public class Person implements Database.StoredObject {
  String name;
  String id;
  String image_url;
  ...
  ...
  
  TYPE getStoredObjectType() {
    return TYPE.person;
  }
  
  /** Unique id for the object. **/
  String getStoredObjectId() {
    return id;
  }
  
  /** Return a list of tags that you want to be able to search for this object by **/
  List<Pair<String, String>> getStoredObjectSearchableTags() {
    List<Pair<String, String>> retval = new LinkedList<Pair<String, String>>();
    retval.add(new Pair("name", name));
    return retval;
  }
  
  // Timestamp in seconds
  Double getStoredObjectTimestamp() {
    return System.currentTimeMillis()/1000;
  }

}
```
