# Recipe Guide App

## Overview

The Recipe Guide App is an Android application designed to assist users in discovering and preparing meals based on available ingredients. It allows users to browse a collection of recipes, view detailed recipe information, and follow step-by-step instructions for cooking. The app fetches data from Firebase and provides real-time updates for recipe lists and search results.

## Features

- **Browse Recipes**: Users can view a list of recipes fetched from Firebase Realtime Database.
- **Detailed Recipe View**: Click on a recipe to view detailed information, including ingredients, instructions, cook time, and calories.
- **Search Functionality**: The app includes a dynamic search bar that filters recipes based on user input.
- **Firebase Integration**: The app uses Firebase for storing and retrieving recipe data, ensuring real-time synchronization and updates.
- **User Interface**: Built with Android's RecyclerView and Data Binding for a smooth, responsive experience.

## Technologies Used

- **Kotlin**: The app is built using Kotlin, offering modern and concise coding practices.
- **Firebase Realtime Database**: The app fetches and stores recipe data in Firebase, enabling real-time updates.
- **RecyclerView & Data Binding**: For efficient display and interaction with the list of recipes.
- **LiveData & ViewModel**: For managing UI-related data in a lifecycle-conscious way.

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/noormuniruet/RecipeGuide-App-with-Firebase-Integration.git
   ```

2. Open the project in **Android Studio**.

3. Set up Firebase in your project:
   - Go to the Firebase console: [Firebase Console](https://console.firebase.google.com/).
   - Create a new Firebase project and link it to your Android app.
   - Follow the steps to integrate Firebase Realtime Database.

4. Sync the project and run the app on your emulator or physical device.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Create a new pull request.

## License

This project is open-source and available under the MIT License.

---

For more details, please feel free to reach out or contribute to the project through the GitHub repository.

GitHub Repository: [Recipe Guide App with Firebase Integration](https://github.com/noormuniruet/RecipeGuide-App-with-Firebase-Integration.git)
