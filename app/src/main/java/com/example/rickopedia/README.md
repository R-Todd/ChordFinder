
# Rickopedia

Rickopedia is an Android application that lets users search for characters from the Rick and Morty universe, view detailed information about any character, and save favorites locally. It follows the MVVM architecture and uses Jetpack components along with popular third‑party libraries for a smooth, polished UX.

## 📦 Features

* **Search**: Look up characters by name using the search bar or the keyboard's search action.
* **Character List**: Displays search results with thumbnail, name, and status badge.
* **Details Screen**: Tap any character to see full details (status, species, gender, origin, last seen).
* **Favorites**: Mark/unmark characters as favorites and view your favorites list.
* **Offline Storage**: Favorites are stored locally with Room.
* **Loading Placeholder**: Shimmer effect while images load.
* **Material UI**: Clean, modern design with cards, chips, and smooth slide‑in animations.

## 🚀 Getting Started

### Requirements

* Android Studio Arctic Fox or higher
* Android SDK 26 (Android 8.0) or above
* Internet connection (for API calls)

### Setup & Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/R-Todd/Rickopedia
   cd Rickopedia
   ```

2. **Open in Android Studio**

   * Choose **File → Open...** and select the project root.
   * Allow Gradle to sync and download dependencies.

3. **Build & Run**

   * Connect an Android device or start an emulator.
   * Click **Run ▶** or use **Shift+F10**.

### Configuration

* No API key is required; the app uses the public Rick and Morty API (`https://rickandmortyapi.com`).
* All dependencies are declared in `build.gradle.kts`.

## 🛠 Architecture

* **MVVM**: `CharacterViewModel` handles data logic, `Repository` abstracts network/DB.
* **Retrofit & Gson**: Network calls to the Rick and Morty REST API.
* **Room**: Local database for favorites.
* **Navigation Component**: In-app navigation with toolbar and Up‑arrow handling.
* **Coil**: Asynchronous image loading with listeners to stop shimmer.

## 📑 Folder Structure

* `data/` – Network models, API client.
* `repository/` – Repository for data operations.
* `ui/` – Fragments, adapters, and UI logic.
* `viewmodel/` – ViewModel and factory.
* `res/` – Layouts, menus, navigation graph, values.

## 🤖 AI Assistance Disclaimer

This project received development and debugging assistance from OpenAI’s ChatGPT. Portions of the code were reviewed, refactored, and commented with AI support to improve readability and correctness. This README was also assisted by Generative AI for clarity, detail, and markdown formatting based off of human-provided information and starter README.

