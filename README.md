# MyAnime

This is an Android application for browsing and discovering new anime.

myanine apk file is provided in apks folder if you wish to try the app

## Architecture

This project follows the MVVM (Model-View-ViewModel) architecture pattern and makes use of modern Android development libraries and practices.

*   **UI Layer:** Built with Jetpack Compose for a declarative and modern UI.
*   **ViewModel:** Manages the UI-related data and state, exposing it to the UI via Kotlin Flows.
*   **Repository:** Handles data operations, providing a clean API for the ViewModels to access data from multiple sources (network and local).
*   **Data Layer:** Consists of a remote API service (using Retrofit) to fetch anime data and a local database (using Room) to cache data for offline access.
*   **Dependency Injection:** Hilt is used for dependency injection to manage dependencies and improve testability.
*   **Asynchronous Operations:** Kotlin Coroutines are used for managing background threads and asynchronous operations.

## Features

*   Browse top-rated anime.
*   Search for anime by title.
*   View detailed information about a specific anime.
*   Offline caching of anime data.

## Build Instructions

To build and run this project, you will need Android Studio Iguana | 2023.2.1 or later.

1.  Clone the repository: `git clone https://github.com/your-username/MyAnime.git`
2.  Open the project in Android Studio.
3.  Let Gradle sync and download the required dependencies.
4.  Run the `app` configuration on an Android emulator or a physical device.

## Dependencies

This project uses the following major libraries:

*   [Jetpack Compose](https://developer.android.com/jetpack/compose) for the UI.
*   [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.
*   [Hilt](https://dagger.dev/hilt/) for dependency injection.
*   [Retrofit](https://square.github.io/retrofit/) for networking.
*   [Room](https://developer.android.com/training/data-storage/room) for local database.
*   [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for pagination.
