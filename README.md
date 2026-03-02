# NMevent

An Android app for creating, discovering, and managing events. Participants can chat in real time within each event using Firebase-backed messaging.

## Requirements

- Android Studio Hedgehog or later
- Android device or emulator running API 24+
- A Firebase project with Firestore and Authentication enabled

## Stack

- Java, Android SDK (API 24-34)
- Firebase Firestore (real-time database)
- Firebase Authentication
- Gradle build system
- Material Design components

## Features

- Email and password authentication via Firebase Auth
- Create events with title, date, time, location, and participant limits
- Browse all events with live updates from Firestore
- Real-time chat for event participants
- Material Design UI with RecyclerView lists and FloatingActionButtons

## Getting Started

1. Clone the repo and open it in Android Studio.
2. Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com).
3. Enable Email/Password authentication and Firestore in your Firebase project.
4. Download `google-services.json` from the Firebase console and place it in the `app/` directory.
5. Build and run on a device or emulator (API 24+).

```bash
./gradlew assembleDebug
```

## Project Structure

```
app/src/main/java/
    ├── auth/          (login, registration screens)
    ├── events/        (event list, creation, detail)
    ├── chat/          (real-time messaging)
    └── models/        (Event, Message, User POJOs)
```

## Firestore Collections

| Collection | Description                        |
|------------|------------------------------------|
| events     | Event documents with metadata      |
| messages   | Subcollection per event for chat   |
| users      | User profile data                  |

## License

MIT
