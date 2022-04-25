
# Testing Challenge

## Introduction

Thank your for participating in the Android Testing MiniBootcamp!  
Here, you'll find instructions for completing the challenge.

&nbsp;
## The Challenge

The purpose of the challenge is for you to practice your Testing skills.  
You are given the base code of a movies app and you will create its unit and ui tests.

&nbsp;
## Getting started

### The app
- **Data**: The data layer fetches popular and favorite movies in the following way:
  - Popular movies are fetched from a movies [REST API](https://developers.themoviedb.org/3/getting-started/introduction) using Retrofit, Gson, coroutines and RxJava.
  - Favorite movies are stored and fetched from a local database built with Room.

- **Domain**: Contains Repository interface and domain models used in the app.

- **ui**: The app consist of two main screens, each screen with its own Fragment & ViewModel.
  - *Home screen*: Shows a list of popular movies and can open a filter dialog to display either popular or favorite movies.
  - *Details screen*: Shows the detail of a specific movie selected from the home screen.

- **di**: The app uses hilt as dependency injection frameworks. In this package you will find the Hilt modules for the dependency injection.

  &nbsp;
### TMDB API Key

This app uses the [TMDB API](https://www.themoviedb.org/) to load the movies on the home screen. To use the API, you will need to obtain a free API key. See the [TMDB API documentation](https://developers.themoviedb.org/3/getting-started/introduction) for instructions.

Once you have the key, add this line to the local.properties file in the project's root folder:

&emsp;&emsp;`TMDB_KEY = <your_api_key>`

*Just replace *<your_api_key>* with your own value without adding additional quotes.

&nbsp;
## Your tasks

You will create tests against:

- FavoritesDao
  - Create an inMemoryDatabase and close it on every test.
  - Test every method and its edge cases.

- MoviesRepositoryImpl
  - You will require to mock the moviesApi and the favoritesDao by using Mockito and MockWebServer.
  - Save a json response from the API in the test/resources folder, get and parse in the tests for the mocks.
  - To test add and remove favorite, you can use Mockito.verify()
  - You will practice how to create tests for functions using coroutines and RxJava.

- HomeViewModel & DetailsViewModel
  - You will usually Unit
  - When working with coroutines, you will commonly require to swap to a test dispatcher in your viewModel tests. Create a MainCoroutineRule and add it to your viewModel tests.
  - To test liveData, create a getOrAwaitValue() extension function.

- HomeFragment & DetailsFragment
  - Set up hilt for testing.
  - Create a FakeMoviesApi and inject in your test classes by replacing the NetworkModule using @TestInstallIn from Hilt.
  - Make use of the Robot pattern.
  - Create a Matcher to test images by tag.
  - Test that an error message is shown in the HomeFragment when the FakeMoviesApi throws an exception.
  - Test the movies list.
  - Test navigation from HomeFragment to MovieDetails when clicking a movie in the RecyclerView.

- Additional
  - Create a sharedTest directory and configure it in build.gradle to share resources between test and androidTest directories. You will centralize test data / test fakes in this directory.