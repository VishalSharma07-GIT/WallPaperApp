WALLIFY – Modern Android Wallpaper App

WALLER is a modern Android wallpaper application built using Kotlin and MVVM architecture.
The app allows users to browse, search, favorite, download, and share high-quality wallpapers with a smooth and clean user experience.

This project was built as my first complete Android app, focusing on real-world architecture, API integration, and polished UI/UX.

🚀 Features
🟢 Onboarding Experience

Elegant onboarding screen with a transparent bottom sheet

Appears only once on first app launch

Controlled using SharedPreferences

🔍 Wallpaper Search

Search wallpapers by keywords (e.g., nature, cars, abstract)

Results fetched dynamically using a REST API

🖼️ Home Screen

Grid-based wallpaper layout using RecyclerView

Smooth scrolling and modern card UI

Animated search bar interaction

❤️ Favorites System

Mark wallpapers as favorite

Favorites stored locally using Room Database

Live updates: favorite/unfavorite instantly reflects in UI

📄 Wallpaper Detail Screen

Full-screen wallpaper preview

Wallpaper description

Actions:

❤️ Favorite / Unfavorite

📥 Download

📤 Share

📥 Download & Share

Download wallpapers using Android DownloadManager

Share wallpaper links via system share intent

🛠️ Tech Stack

Language: Kotlin

UI: XML, RecyclerView, CardView

Architecture: MVVM

Networking: Retrofit, Gson

Image Loading: Coil

Local Storage: Room Database

Dependency Injection: Hilt

Async Handling: Kotlin Coroutines & Flow

Navigation: Jetpack Navigation Component

State Management: LiveData / StateFlow
