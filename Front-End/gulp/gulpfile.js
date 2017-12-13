// Modules 호출
var gulp      = require('gulp'),
		csslint   = require('gulp-csslint'),
		concatcss = require('gulp-concat-css'),
		uglifycss = require('gulp-uglifycss'),
		jshint    = require('gulp-jshint'),
		uglify    = require('gulp-uglify'),
		concat    = require('gulp-concat'),
		rename    = require('gulp-rename'),
		gulpif    = require('gulp-if'),
		del       = require('del'),
		config    = require('./config.json'); // 환경설정

/**
 * 기본(Default) & 관찰(Watch) 업무 정의
 * clean > styles > scripts 순으로 업무 실행
 */
// 기본 (Default) 업무
gulp.task('default', ['clean', 'styles', 'scripts', 'watch']);

// 지속적 관찰(Watch) 업무
gulp.task('watch', ['clean'], function() {
	gulp.watch(config.path.css.watchSrc, ['styles']);
	gulp.watch(config.path.js.src, ['scripts']);
});

/**
 * 폴더/파일 제거
 * NPM 설치 모듈: del
 */
gulp.task('clean', function() {
	del(['dist/css/*', 'dist/js/*']);
});

/**
 * CSS 업무
 * NPM 설치 모듈: gulp-csslint, gulp-concat-css, gulp-uglifycss
 * 문법 검사 > 병합 > 압축
 */
gulp.task('styles', function() {
	gulp.src( config.path.css.src )
		// 문법검사
		.pipe( gulpif(config.lint, csslint(config.cssLintRules)) )
		.pipe( gulpif(config.lint, csslint.reporter()) )
		// 파일 병합
		.pipe( gulpif(config.concat, concatcss( config.path.css.filename )) ) 
		// 압축하지 않은 파일 출력
		.pipe( gulpif(config.rename, gulp.dest( config.path.css.dest )) )
		// 압축
		.pipe( gulpif(config.uglify, uglifycss()) )
		// 압축한 파일 이름 바꿔 출력
		.pipe( gulpif(config.rename, rename({suffix: '.min'})) )
		.pipe(gulp.dest( config.path.css.dest ));
});

/**
 * Javascript 업무
 * NPM 설치 모듈: gulp-jslint, gulp-concat, gulp-uglify
 * 문법 검사 > 병합 > 압축
 */
gulp.task('scripts', function() {
	gulp.src( config.path.js.src )
		// 문법검사
		.pipe( gulpif(config.lint, jshint()) )
		.pipe( gulpif(config.lint, jshint.reporter('jshint-stylish')) )
		// 파일 병합
		.pipe( gulpif(config.concat, concat( config.path.js.filename )) )
		// 압축하지 않은 파일 출력
		.pipe( gulpif(config.rename, gulp.dest( config.path.js.dest )) )
		// 압축
		.pipe( gulpif(config.uglify, uglify(config.jsUglifyOptions)) )
		// 압축한 파일 이름 바꿔 출력
		.pipe(gulpif(config.rename, rename({suffix: '.min'})) )
		.pipe(gulp.dest( config.path.js.dest ));
});