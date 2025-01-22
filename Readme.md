# Shangri-la Petition platform project Setup Guide

## Open your browser and navigate to `http://localhost/app`.

## Prerequisites
Before you begin, ensure you have the following installed on your machine:

### NOTE: To run, the project shell scripts are provides therefore please use linux machine to run the application with simplicity.

1. **Docker**
2. **Docker Compose**
3. **Node.js** (v14 or above)
4. **npm** (v6 or above)
5. **maven**

---

## Steps to Run the Project

Navigate to the project directory:

```bash
$ cd ~/CW2_pm455/
```

Note: Use main project tree based scripts only.

### 2. Bootstrap the Environment
Run the bootstrap script to prepare the environment for backend and frontend:

```bash
$ ./scripts/bootstrap.sh
```
This will set up necessary configurations and initialize any required services.

### 3. Start the Project
To start the backend, frontend, and any supporting services, use the start script:

```bash
$ ./scripts/start.sh
```

### 4. Access the Application
- **Frontend**: Open your browser and navigate to `http://localhost/app`.
- **Backend API**: Access the backend API at `http://localhost/api`.

### 5. Stop the Project
To stop all running services, run the stop script:

```bash
$ ./scripts/stop.sh
```

### 6. Clean Up
To clean up any generated files or containers, use the cleanup script:

```bash
$ ./scripts/cleanup.sh
```

---

## Additional Notes
- Ensure all ports defined in `docker-compose.yml` are available on your system.
- Petition Committee already bootstrapped with given default credentials:

```text
    emailId = "admin@petition.parliament.sr"
    password = "2025%shangrila"
```
- "As authentication is maintained in cookie, please clear the cookie if any error occurs during registration or signing in."
---
