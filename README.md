# FairSplit 💸

**FairSplit** is a full-stack expense splitting app built with:

- 🔧 **Spring Boot + PostgreSQL** backend (REST API, JWT auth)
- ⚛️ **React + Vite + TailwindCSS** frontend (clean UI, modern stack)
- 🔐 Secure login, register, and token-based session
- 👥 Create/join groups via invite code
- 💰 Add expenses, track balances, and settle up smartly

---

## 🛠️ Project Structure

```
fairsplit/
├── backend/     # Java backend (Spring Boot)
├── frontend/    # React frontend (Vite + Tailwind)
```

---

## ✨ Features

- User registration & login (JWT secured)
- Create and join groups via invite code
- Add expenses with custom splits
- View group members, balances, and minimal settlements
- Fully documented API via Swagger

---

## 🔧 Tech Stack

| Backend       | Frontend           |
| ------------- | ------------------ |
| Java 21       | React + Vite       |
| Spring Boot 3 | TailwindCSS v4     |
| PostgreSQL    | Axios (API layer)  |
| JWT Auth      | React Router       |
| Flyway        | Context API (Auth) |

---

## 📦 Getting Started

1. **Clone the repo**

```bash
git clone https://github.com/yourusername/fairsplit.git
cd fairsplit
```

2. **Run Backend**

```bash
cd backend
./gradlew bootRun
```

3. **Run Frontend**

```bash
cd frontend
npm install
npm run dev
```

4. Visit:
   Frontend: `http://localhost:5173`  
   Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 💪 To Do / In Progress

- [ ] UI polish + group views
- [ ] Group creation/joining in frontend
- [ ] Settlements UI
- [ ] Deployment (Render + Vercel?)
- [ ] OpenAPI YAML export

---

## 💬 License

MIT or your choice. Customize here.
