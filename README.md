
# Nasa Space Gallery
The project features Clean Model-View-ViewModel architectures with Dagger-Hilt as dependency injection tool.


## Screenshots
![](https://github.com/pratikmhatre/SpaceGallery/blob/main/list.gif) ![](https://github.com/pratikmhatre/SpaceGallery/blob/main/details.gif)


## Libraries Used
- AndroidX Navigation Architecture with SafeArgs
- Kotlin Coroutines, Flows for async processing
- Room Db for offline storage
- Dagger - Hilt for dependency injection
- GSON for serialization
- Google Truth library for Unit Testing
- Turbine for testing Kotlin Flows



## Working Of The App
- The Space Gallery app displays a list of images with respective titles from a JSON file stored inside the project.
- Clicking any image from the list opens a details page showing full size image with respective meta data.


## Running Tests
Unit Tests and Instrumented Tests are present in the Test and AndroidTest directories respectively. Unit tests are present for testing the business logic while Instrumented tests checks for proper functioning of the DAO class.

```
1. **UnitTestSuite.class** can be used to run all unit tests

2. **InstrumentedTestSuite.class** can be used to run all instrumented tests.

```

## References
- FlowObserver.kt code snippet is taken from "https://gist.github.com/gmk57/330a7d214f5d710811c6b5ce27ceedaa"