var gulp = require('gulp');
var concat = require('gulp-concat');
var gnf = require('gulp-npm-files');
var uglify = require('gulp-uglify');
var imagemin = require('gulp-imagemin');
var sourcemaps = require('gulp-sourcemaps');
var del = require('del');
var gulpFilter = require('gulp-filter');
var drFrankenstyle = require('dr-frankenstyle');
var webserver = require('gulp-webserver');

var paths = {
  scripts: ['js/**/*.js'],
  images: 'img/**/*'
};

// Not all tasks need to use streams
// A gulpfile is just another node program and you can use any package available on npm
gulp.task('clean', function() {
  // You can use multiple globbing patterns as you would with `gulp.src`
  return del(['build']);
});

gulp.task('scripts', ['clean'], function() {
  // Minify and copy all JavaScript (except vendor scripts)
  // with sourcemaps all the way down
  return gulp.src(paths.scripts)
    .pipe(sourcemaps.init())
      .pipe(uglify())
      .pipe(concat('all.min.js'))
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('build/js'));
});


gulp.task('vendor', ['vendor-js', 'vendor-css']);

gulp.task('vendor-js', ['clean'], function() {
  var filter = gulpFilter(['**/*.min.js']);

  return gulp.src(gnf(), {base:'./'})
    .pipe(filter)
    .pipe(concat('vendor.js'))
    .pipe(gulp.dest('build/js'))
})

gulp.task('vendor-css', ['clean'], function() {
  return drFrankenstyle()
    .pipe(gulp.dest('build/css'));
});

// Copy all static images
gulp.task('images', ['clean'], function() {
  return gulp.src(paths.images)
    // Pass in options to the task
    .pipe(imagemin({optimizationLevel: 5}))
    .pipe(gulp.dest('build/img'));
});

// Rerun the task when a file changes
gulp.task('watch', function() {
  gulp.watch(paths.scripts, ['scripts']);
  gulp.watch(paths.images, ['images']);
});

gulp.task('copy-index', ['clean'], function() {
  return gulp.src('index.html')
    .pipe(gulp.dest('build'));
});

gulp.task('live-reload', ['watch', 'scripts', 'images', 'vendor']);

gulp.task('webserver', ['copy-index', 'live-reload'], function() {
  gulp.src('build')
    .pipe(webserver({
      open: true
    }));
});
// The default task (called when you run `gulp` from cli)
gulp.task('default', ['webserver']);


