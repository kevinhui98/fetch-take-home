# Fetch Take-Home Assignment – Android (Jetpack Compose)

This is a native Android application built with Kotlin and Jetpack Compose for the Fetch Rewards take-home coding challenge.

## 📱 Features

- ✅ Fetches data from API Link
- ✅ Filters out items with blank or null `name` fields
- ✅ Groups items by `listId`
- ✅ Sorts each group alphabetically by `name` and by `listId` overall
- ✅ Displays the result in a clean 2-column grouped layout using `LazyColumn`
- ✅ Supports pull-to-refresh using Material3’s native `pullRefresh` APIs
- ✅ Retry button shown on fetch failure
- ✅ Built with modern Android architecture: **ViewModel + StateFlow + Compose**

## 🧱 Architecture

This app follows a lightweight MVVM structure:

UI (MainScreen, ItemList) 
├── ViewModel (ItemViewModel) 
└── StateFlow for reactive UI state 
└── Repository (ItemRepository) 
└── Ktor (with fallback to OkHttp)


## 🧰 Tech Stack

- Jetpack Compose (Material3)
- Kotlin
- ViewModel + StateFlow
- Ktor Client (CIO)
- OkHttp fallback (for network resilience)
- Native Material3 Pull-to-Refresh
- LazyColumn + custom chunked Row grid

## 📦 Getting Started

1. Clone the repo:

   ```bash
   git clone https://github.com/kevinhui98/fetch-take-home.git
   ```
2. Open in Android Studio Giraffe or newer

3. Run on an emulator or device (API 24+)

4. Pull down to refresh the list

## 📝 Notes
The app gracefully falls back to OkHttp if the Ktor request fails (e.g., DNS resolution issues).

Designed to work smoothly even on a fresh emulator boot.

## ✨ App Walkthrough
Grouped List with Pull-to-Refresh

<img src="fetch-take-home.gif" width="500"/>

https://imgur.com/a/yGsMV7Q

## 👋 Author
Built with ❤️ by Kevin H
GitHub: @kevinhui98
