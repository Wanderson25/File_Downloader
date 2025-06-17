# ☕ Java File Download Demo: Content-Length vs. Transfer-Encoding

![Java](https://img.shields.io/badge/Java-17+-blue?style=for-the-badge&logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-green?style=for-the-badge&logo=spring)

## 🎯 The Challenge

This project is a practical, hands-on demonstration of two fundamental ways an HTTP server can send files to a client. It was created to answer the question:

> "Why are large game files often split into multiple parts for download, while smaller files are downloaded in one go?"

This repository contains a simple Java-based server and client that showcase the two key HTTP mechanisms at the heart of this question: `Content-Length` and `Transfer-Encoding: chunked`.

---

## 🔬 Core Concepts Explained

### 1. The Standard Download (`Content-Length`)

This is the most common method. The server calculates the file's exact size *before* the transfer begins and sends it in an HTTP header.

-   **Header:** `Content-Length: 10485760`
-   **✅ Pros:** The client knows the total file size from the start, making it perfect for creating accurate progress bars (e.g., "Downloading 5.7 MB of 10.0 MB").
-   **❌ Cons:** The server must know the entire file size upfront. This is not suitable for content that is being dynamically generated and streamed on-the-fly.

### 2. The Streaming Download (`Transfer-Encoding: chunked`)

When the server doesn't know the final size of the content, it can send it in a series of pieces, or "chunks".

-   **Header:** `Transfer-Encoding: chunked`
-   **✅ Pros:** Ideal for streaming very large files or dynamically generated content (e.g., zipping files from a database and sending them directly). The server can start sending data immediately without pre-calculation.
-   **❌ Cons:** The client doesn't know the total file size, making it difficult to show a percentage-based progress bar.

---

## ✨ Project Features

*   A simple **Spring Boot server** that runs on port `8081`.
*   Two distinct API endpoints to test both download methods.
*   A lightweight **Java 11+ HTTP Client**.
*   The client saves downloaded files with **distinct names** for easy comparison.

| Endpoint             | HTTP Header Used              | Saved Filename                        |
| -------------------- | ----------------------------- | ------------------------------------- |
| `/download-standard` | `Content-Length`              | `downloaded_file_FROM_STANDARD.zip`   |
| `/download-stream`   | `Transfer-Encoding: chunked`  | `downloaded_file_FROM_STREAM.zip`     |

---

## 🛠️ Getting Started

### Prerequisites

To build and run this project, you will need:
-   **Java Development Kit (JDK)**: Version 17 or newer.
-   **Apache Maven**: To build the project and manage dependencies.
-   An IDE like **VS Code** (with the Java Extension Pack) or **IntelliJ IDEA**.

### 🚀 Running the Project

**Step 1: Clone the Repository**

git clone https://github.com/your-username/your-repository-name.git
cd your-repository-name

**Step 2: Start the Server**

1. Open the project in your IDE.

2. Locate and run the ServerApplication.java file.

3. Wait for the console to show that the server has started on port 8081.

4. Keep the server running in the background.

**Step 3: Run the Client to Test**

1. Now, open the FileDownloaderClient.java file.

2. Choose which download method you want to test by setting the chosenUrl variable: String chosenUrl = streamUrl;

3. Run the FileDownloaderClient.java file.

4. Check the console output for the server headers and look for the corresponding .zip file in the project's root directory.

## 🚨 Troubleshooting

If the server fails to start with a Port 8081 was already in use error, it means an old instance of the server is still running. Stop the old Java process using your IDE's stop button or your system's Task Manager / kill command.

This project was created to fulfill a fun and educational challenge. Enjoy!
