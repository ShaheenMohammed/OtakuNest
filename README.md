# Otaku Nest 🏯

**Otaku Nest** is a premium, self-hosted personal media server designed specifically for anime and manga enthusiasts. It provides a seamless experience for managing, streaming, and reading your personal collection with automatic metadata fetching and progress tracking.

## ✨ Features (In Development)

- 📺 **Anime Streaming**: High-quality video playback with subtitle support.
- 📖 **Manga Reader**: Optimized reading experience for manga and webtoons.
- 📂 **Automatic Organization**: Automatically scan and organize your media files.
- 🔍 **Fuzzy Search**: Find titles quickly using PostgreSQL's trigram search.
- 📊 **Progress Tracking**: Keep track of every chapter read and episode watched.
- 🔒 **Secure Access**: JWT-based authentication to keep your nest private.

## 🛠️ Tech Stack

### Backend
- **Core**: Java 21 (LTS)
- **Framework**: Spring Boot 3.4.1
- **Security**: Spring Security with JWT
- **Persistence**: Spring Data JPA
- **Database**: PostgreSQL 16

### Frontend
- **Core**: React 18
- **Build Tool**: Vite
- **Styling**: Vanilla CSS (Modern Aesthetics)

### Infrastructure
- **Containerization**: Docker & Docker Compose
- **Database Extensions**: `pg_trgm` (fuzzy search), `pgcrypto`

## 🚀 Getting Started

### Prerequisites
- [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Node.js](https://nodejs.org/) (v18+)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### 1. Database Setup
Start the PostgreSQL database container:
```bash
docker-compose up -d
```
The database will automatically initialize with the required extensions (`pg_trgm`, `pgcrypto`).

### 2. Backend Setup
Navigate to the backend directory and run the application:
```bash
cd backend
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080`.

### 3. Frontend Setup
Navigate to the frontend directory, install dependencies, and start the development server:
```bash
cd frontend
npm install
npm run dev
```
The application will be available at `http://localhost:5173`.

## 📂 Project Structure

```text
Otaku Nest/
├── backend/          # Spring Boot application
├── frontend/         # React + Vite application
├── infra/            # Database scripts & configurations
└── docker-compose.yml # Infrastructure orchestration
```

---
Built with ❤️ for the Otaku community.
