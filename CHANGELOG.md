# Changelog

This changelog goes through all the changes that have been made in each release.

## [1.0.4]() - 2023-05-29

### Added

* Possibility to add ISBN to metadata
* Possibility to add publisher to metadata
* Possibility to add copyright remark to metadata

### Fixed

* Fix problem with validation of non-linear TOC

### Changed

* Update dependencies
* Minor refactoring in some classes
* Update EpubCreatorTest

### Removed

* Apache dependencies removed

## [1.0.3]() - 2022-06-05

### Added

* New classes and methods to add meta tags to the book
* Add cover meta tag for addCoverImage method

### Fixed

* Fix changelog date for version 1.0.2
* Fix some compiler warnings

### Changed

* Rename content folder to OPS, according to de facto standard
* Update README.md

## [1.0.2]() - 2022-06-03

### Fixed

* Fix problem with children in table of contents

## [1.0.1]() - 2022-05-31

* Refactoring release

### Changed

* Improve and update README.md
* Improve and refactor tests
* Move EpubConstants to model package

### Fixed

* Use real UUID for test
* Create xml compliant content id
* Fix empty toc / landmark blocks in EPUB

## [1.0.0]() - 2022-03-13

* Initial release (fork from epub-creator from OpenCollabZA)

### Changed

* Renamed to epubwriter
* Update project to Java 17
* Update project to JUnit 5
* Cleanup code

### Fixed

* Fix some SonarLint code smells
* Fix some compiler warnings