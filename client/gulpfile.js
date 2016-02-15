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
var sass = require('gulp-sass');

var paths = {
  scripts: ['js/**/*.js'],
  images: 'img/**/*',
  css: 'css/**/*.scss',
  index: 'index.html'
};

// Not all tasks need to use streams
// A gulpfile is just another node program and you can use any package available on npm
gulp.task('clean', function() {
  // You can use multiple globbing patterns as you would with `gulp.src`
  return del(['build']);
});

gulp.task('scripts', function() {
  del.sync(['build/js/*', '!build/js/vendor.js']);
  // Minify and copy all JavaScript (except vendor scripts)
  // with sourcemaps all the way down
  return gulp.src(paths.scripts)
    .pipe(sourcemaps.init())
      .pipe(uglify())
      .pipe(concat('all.min.js'))
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('build/js'));
});

gulp.task('sass', function () {
  del.sync(['build/css/*', '!build/css/components.css']);

  gulp.src(paths.css)
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest('./build/css'));
});

gulp.task('vendor', ['vendor-js', 'vendor-css']);

gulp.task('vendor-js', function() {
  del.sync(['build/js/vendor.js']);

  var filter = gulpFilter(['**/*.min.js']);

  return gulp.src(gnf(), {base:'./'})
    .pipe(filter)
    .pipe(concat('vendor.js'))
    .pipe(gulp.dest('build/js'))
})

gulp.task('vendor-css', function() {
  del.sync(['build/css/components.css']);

  return drFrankenstyle()
    .pipe(gulp.dest('build/css'));
});

// Copy all static images
gulp.task('images', function() {
  del.sync(['build/img/*']);

  return gulp.src(paths.images)
    // Pass in options to the task
    .pipe(imagemin({optimizationLevel: 5}))
    .pipe(gulp.dest('build/img'));
});

// Rerun the task when a file changes
gulp.task('watch', function() {
  gulp.watch(paths.scripts, ['scripts']);
  gulp.watch(paths.images, ['images']);
  gulp.watch(paths.css, ['sass']);
  gulp.watch(paths.index, ['copy-index']);
});

gulp.task('copy-index', function() {
  del.sync(['build/index.html']);

  return gulp.src(paths.index)
    .pipe(gulp.dest('build'));
});

gulp.task('live-reload', ['watch', 'scripts', 'images', 'vendor', 'sass', 'copy-index']);

gulp.task('webserver', ['copy-index', 'live-reload'], function() {
  gulp.src('build')
    .pipe(webserver({
      open: true
    }));
});
// The default task (called when you run `gulp` from cli)
gulp.task('default', ['webserver']);
