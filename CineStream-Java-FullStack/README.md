# CineStream — Java Full-Stack OTT Portfolio Project

A Netflix-inspired OTT web application built with **Java 17 + Spring Boot + MySQL** on the backend and **React + Vite** on the frontend. It uses original CineStream branding and demo catalog content.

## Features
- Register and log in (passwords stored with BCrypt; JWT bearer tokens)
- Browse a seeded movie/show catalog
- Search by title, genre, and description
- View title details and play a demo video
- Add/remove titles from a personal watchlist
- Responsive dark OTT-style interface
- REST API with DTOs, validation, JPA repositories, and centralized error handling

> This is a portfolio-ready starter project, not a production streaming service. The included video URLs are public demo clips; replace them with media you own or are licensed to stream. Do not upload copyrighted movies.

## Tech stack
- Backend: Java 17, Spring Boot 3, Spring Web, Spring Data JPA, Spring Security, JWT, Bean Validation
- Database: MySQL 8
- Frontend: React, Vite, React Router, Axios

## Requirements
- JDK 17+
- Maven 3.9+
- Node.js 18+
- MySQL 8+

## 1. Create database
```sql
CREATE DATABASE cinestream;
```

## 2. Configure backend
Edit `backend/src/main/resources/application.properties` or set environment variables:
- `DB_URL` (default `jdbc:mysql://localhost:3306/cinestream`)
- `DB_USERNAME` (default `root`)
- `DB_PASSWORD` (default `root`)
- `JWT_SECRET` (use a long random secret in real deployments)

## 3. Run backend
```bash
cd backend
mvn spring-boot:run
```
Backend runs at `http://localhost:8080`. On first run, demo titles are inserted automatically.

## 4. Run frontend
Open a second terminal:
```bash
cd frontend
npm install
npm run dev
```
Open the Vite URL shown in the terminal (normally `http://localhost:5173`).

## Demo flow
1. Register a new account.
2. Log in.
3. Browse the catalog and open a title.
4. Add it to your watchlist.
5. Open **My List** to see saved titles.

## Main API routes
| Method | Route | Description |
|---|---|---|
| POST | `/api/auth/register` | Create account |
| POST | `/api/auth/login` | Log in and receive JWT |
| GET | `/api/movies` | Browse/search catalog |
| GET | `/api/movies/{id}` | Movie details |
| GET | `/api/watchlist` | Current user's watchlist |
| POST | `/api/watchlist/{movieId}` | Add title |
| DELETE | `/api/watchlist/{movieId}` | Remove title |

## Portfolio improvements to add next
- Admin dashboard for catalog management
- Subscription plans and payment gateway sandbox
- Cloud object storage + signed playback URLs
- Pagination, recommendations, and viewing history
- Docker Compose for app + MySQL
- Unit/integration tests and CI pipeline

## Security notes
The JWT secret and database credentials are local-development defaults. Change them before deployment. For production, also add rate limiting, refresh-token rotation, HTTPS, secure secret storage, and authorization tests.
