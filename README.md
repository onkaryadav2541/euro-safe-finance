# 🛡️ EuroSafe - Student Safety Platform

EuroSafe is a backend API built to ensure the safety of students in Europe. It allows users to send instant **SOS alerts** with geolocation, view active dangers on a **Community Radar**, and manage emergency incidents securely.

## 🚀 Features
* **User Registration & Security:** Secure signup/login with BCrypt password encryption and Spring Security.
* **SOS Alert System:** Instant incident reporting with GPS coordinates (Latitude/Longitude).
* **Community Radar:** View active dangers nearby (Public Safety Map).
* **Incident Management:** Users can resolve alerts once they are safe.
* **Data Validation:** Prevents bad data from entering the system.
* **Automated Testing:** Unit tests to ensure reliability.

## 🛠️ Tech Stack
* **Language:** Java 21 (Spring Boot 3.4)
* **Database:** PostgreSQL (running in Docker)
* **Security:** Spring Security & Basic Auth
* **Build Tool:** Maven

## 🔌 API Endpoints

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user | ❌ No |
| `POST` | `/api/incidents` | Create an SOS Alert | ✅ Yes |
| `GET` | `/api/incidents` | View my incident history | ✅ Yes |
| `GET` | `/api/incidents/active` | **Radar:** View all open dangers | ✅ Yes |
| `PATCH` | `/api/incidents/{id}/resolve` | Mark an incident as "Safe" | ✅ Yes |

## ⚙️ How to Run

1.  **Start the Database:**
    ```bash
    docker compose up -d
    ```
2.  **Run the App:**
    ```bash
    ./mvnw spring-boot:run
    ```
3.  **Run Tests:**
    ```bash
    ./mvnw test
    ```

## 📝 Project Status
**Version 1.0 Completed.**
*Developed as part of a 15-Day Spring Boot Engineering Challenge.*

## 🔮 Future Roadmap
- [ ] Admin Dashboard
- [ ] Email Notifications
- [ ] Radius Search

## Created by 
**Onkar Yadav**