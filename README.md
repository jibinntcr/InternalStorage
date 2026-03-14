# Reference Implementation: Android Internal Storage

**Course:** Software Development for Portable Devices (CS 10)  


## 📖 Overview
This repository demonstrates the use of **Internal Storage** in Android, a privacy-first storage model used for saving private application data. Unlike static resources, this implementation focuses on files created and managed dynamically while the application is running.

## 🎓 Instructional Objectives
The primary goal of this demo is to illustrate:
* **Runtime Data Persistence:** How to create, write, and read files that persist across application restarts.
* **Append Mode Logic:** Utilizing `MODE_APPEND` to store multiple data entries (e.g., student records) within a single file without overwriting existing content.
* **Privacy & Security:** Understanding that internal storage is sandboxed and accessible only by the owning application.

## 🏛️ Project Architecture
The project highlights key differences between packaged resources and runtime storage:

### 1. Internal Storage Characteristics
* **Private Access:** Files are stored in the app's private directory (`/data/data/<package-name>/files/`) and are invisible to other apps.
* **Zero Permissions:** No manifest permissions (like `READ_EXTERNAL_STORAGE`) are required to access this area.
* **Lifecycle:** All data stored here is automatically removed when the user uninstalls the application.

### 2. Implementation Details
* **Saving Data:** Uses `openFileOutput(fileName, MODE_APPEND)` to ensure new notes are added to the end of the student record.
* **Reading Data:** Uses `openFileInput(fileName)` combined with a `bufferedReader` for efficient text retrieval.
* **Memory Safety:** Implementation utilizes Kotlin's `.use {}` extension to ensure file streams are closed immediately after execution.

## 🛠️ Usage for Students
Students should explore the `MainActivity.kt` to understand:
1.  How the application handles user input from the `OutlinedTextField`.
2.  The difference between `MODE_PRIVATE` (overwrites data) and `MODE_APPEND` (adds to data).
3.  How text is converted to byte arrays for file output and read back as strings.

---

**Topic:** Working with the File System  
**Previous Session:** Static Files (res/raw & assets)  
**Next Session:** CS 11 - Android Databases (SQLite)
