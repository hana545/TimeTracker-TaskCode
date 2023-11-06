# TimeTracker-TaskCode

This app is a solution for a coding task that features a datetime picker and a submit button.

## Features
- **Datetime Picker**: The app provides a datetime picker implemented with a button that opens a Date picker dialog when clicked. After choosing a valid date, the time picker opens up.

- **Date and Time Validations**: The selected date and time should not be in the future, in other words, they shouldn't be set to a time after the current datetime.

- **Initial Data**: On the first launch, or any other launches when the database is empty, the app will call a mock API implemented with the API module. The API response includes the current date and 6:30 as the time. After successfully submitting and inserting the datetime into the database, on the next launch, the app will fetch the most recent datetime. The datetime is displayed and stored in the "yyyy-MM-dd HH:mm" format.

- **Database**: The database is implemented using Room, which has only one entity named "Employee." The "Employee" entity has an auto-generated ID and a "check_in_date" column that stores datetime strings.

## Technology Stack
- Language: Kotlin
- Database: Room Database
- MVVM architecture
- Mock API Module
- Made with Android Studio Flamingo 2022.2.1 and Java 17 for compilation

## Installation
To run the TimeTracker app on your Android device, follow these steps:

1. Clone the project repository from GitHub: git clone https://github.com/hana545/TimeTracker-TaskCode.git
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.
4. Make sure you have the right version of Android Studio installed, along with the necessary Android SDKs.
