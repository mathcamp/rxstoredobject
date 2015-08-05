android_library(
	name = 'code',
	srcs = glob(['src/main/**/*.java']),
	visibility = [ 'PUBLIC' ],
	deps = [
		':timber',
    ':rxjava',
    ':gson',
	],
)

maven('com.jakewharton.timber:timber:2.7.1')
maven('io.reactivex:rxjava:1.0.13')
maven('com.google.code.gson:gson:2.3.1')

project_config(src_target = ':code')

