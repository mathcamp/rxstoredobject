package ht.highlig.storedobject.test;

// import org.robolectric.Robolectric;

import android.app.Activity;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ht.highlig.storedobject.Database;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by revant on 6/24/14.
 */
@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {

    private Context context;

    private List<Person> makePeople() {
        return Arrays.asList(
            new Person("frank", "an id", "http://aurl", 5, true),
            new Person("fred", "another id", "http://anotherurl", 4, false),
            new Person("harold", "id man", "http://aprourl", 2, false),
            new Person("james", "some id", "http://someurl", 7, true),
            new Person("zach", "ids", "http://zachsurl", 19, false)
        );
    }

    private List<Dog> makeDogs() {
        return Arrays.asList(
            new Dog("dasher", "dog_1", 8, false),
            new Dog("spot", "dog_2", 3, false),
            new Dog("joey", "dog_3", 11, true)
        );
    }

    @Before
    public void setup() {
        context = new Activity();
    }

    @Test
    public void testLoadOfSaveOfXisX() {
        Person p = makePeople().get(0);
        Database db = Database.with(context);
        db.saveObject(p);
        Person result = db.load(TYPE.person).getFirst();

        assertEquals(p, result);
    }

    @Test
    public void testLoadOfSaveOfXYisXY() {
        Database db = Database.with(context);
        List<Person> ps = makePeople();
        db.saveObjects(ps);
        List<Person> results = (List<Person>)(List<?>)db.load(TYPE.person).execute();
        assertEquals(ps, results);
    }

    @Test
    public void testSortedLoad() {
        List<Person> people = makePeople();
        Database db = Database.with(context);
        Person first = people.get(0);
        Person second = people.get(1);
        Person third = people.get(2);
        db.saveObject(first);
        db.saveObject(second);
        db.saveObject(third);
        List<Person> results = (List<Person>)(List<?>)db.load(TYPE.person)
                .orderByTs(Database.SORT_ORDER.DESC)
                .execute();

        assertEquals(results.get(0), third);
        assertEquals(results.get(1), second);
        assertEquals(results.get(2), first);
    }

    @Test
    public void testDeleteOne() {
        List<Person> people = makePeople();
        Database db = Database.with(context);
        db.saveObjects(people);
        // delete the first object
        db.deleteObject(people.get(0));
        // take all but the first
        List<Person> ps = people.subList(1, people.size());
        List<Person> results = (List<Person>)(List<?>)db.load(TYPE.person).execute();

        assertTrue(ps.equals(results));
    }

    @Test
    public void testDeleteMany() {
        List<Person> people = makePeople();
        Database db = Database.with(context);
        db.saveObjects(people);
        // delete the first few objects
        db.deleteObjects(people.subList(0, 3));
        // take the rest
        List<Person> ps = people.subList(3, people.size());
        List<Person> results = (List<Person>)(List<?>)db.load(TYPE.person).execute();

        assertEquals(ps, results);
    }

    @Test
    public void testClearType() {
        List<Person> people = makePeople();
        List<Dog> dogs = makeDogs();

        Database db = Database.with(context);
        db.saveObjects(people);
        db.saveObjects(dogs);

        db.clearObjectsOfType(TYPE.person);

        List<Person> peopleResults = (List<Person>)(List<?>)db.load(TYPE.person).execute();
        List<Dog> dogResults = (List<Dog>)(List<?>)db.load(TYPE.dog).execute();

        assertEquals(Collections.<Person>emptyList(), peopleResults);
        assertEquals(dogs, dogResults);
    }
}
