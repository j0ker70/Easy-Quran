# EasyQuran

EasyQuran is an Android application that provides an easy way to explore the chapters and verses of the Quran. Built using modern Android development technologies, it offers a seamless and intuitive user experience.

## Features

-   **Chapter Listing:** Browse through a comprehensive list of all Quran chapters.
-   **Verse Exploration:** Dive into any chapter to view its verses.
-   **Offline Access:** Uses an offline-first strategy, allowing access to the Quran even without an internet connection.
-   **Loading Indicator:** Displays a circular progress bar to indicate loading status.

## Technologies Used

-   **Kotlin:** The primary programming language for the project.
-   **Jetpack Compose:** Utilized for building the UI with a declarative approach.
-   **Clean Architecture:** Implements the clean architecture to separate concerns and improve maintainability.
-   **MVVM (Model-View-ViewModel):** The architectural pattern for separating the UI from the business logic.
-   **Hilt:** For dependency injection, streamlining the management of dependencies.
-   **Room Database:** For local data persistence, enabling offline access to the Quran.
-   **Retrofit:** For network data fetching.

## Architecture

The project is structured using Clean Architecture principles:

-   **Presentation Layer:** Contains ViewModels and UI elements (Jetpack Compose).
-   **Domain Layer:** Houses the business logic and use cases.
-   **Data Layer:** Manages data access through repositories, utilizing Room for local data and Retrofit for network requests.

## Online-First Strategy

EasyQuran adopts an offline-first strategy. This means:

1.  **Network Sync:** If connected, it fetches data from the network (using Retrofit) and updates the local database.
3.  **Offline Availability:** If the network is unavailable, the app functions correctly using only the local data.

## Loading Progress

A circular progress bar is used to indicate the loading progress to the user, ensuring a clear understanding of the data fetching state.

## Getting Started

To run the app, you need to have Android Studio installed.

1.  Clone the repository to your local machine.
2.  Open the project in Android Studio.
3.  Build and run the project on an emulator or a physical device.
