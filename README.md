# **CoinTrade Terminal**

### RUN THIS DAILY MONTHLY AND YEARLY chmod +x gradlew

### _A web-based cryptocurrency trading simulator built with React, Spring Boot, and MongoDB._

---

## **📌 Overview**

**CoinTrade Terminal** is a web-based, simulated cryptocurrency trading platform designed for education and demonstration.  
Users can trade major coins in a virtual environment using real-time market data, manage orders, and track portfolios — with all data persisted in **MongoDB**.  
The frontend is built in **React**, while the backend runs on **Java (Spring Boot)**.

---

## **🎯 Project Goals**

- Deliver a fast, intuitive, and educational trading simulation.
- Allow users to place mock buy/sell orders and observe price changes in real time.
- Provide a responsive, interactive UI with clear visual cues.
- Persist user accounts, orders, and portfolios securely in MongoDB.

---

## **✨ Key Features**

- **User Registration & Authentication**  
  Secure sign-up/login using Spring Security.

- **Real-Time Coin Price Feed**  
  Uses public APIs (e.g., CoinGecko) to fetch live market data.

- **Trading Functionality**  
  Mock buy/sell orders with validation and automatic account/portfolio updates.

- **Portfolio Dashboard**  
  Live holdings, profit/loss, and asset allocation metrics.

- **Order & Transaction History**  
  View pending and executed trades.

- **Interactive React UI**  
  Drag-and-drop dashboard layout, responsive grid, and color-coded status indicators.

- **Persistent State**  
  All user/order/portfolio data stored in MongoDB.

- **Secure REST API**  
  Protected endpoints; no sensitive info exposed to clients.

- **User Notifications**  
  Visual alerts for trades, errors, warnings, and price changes.

---

## **🧰 Technical Requirements & Solutions**

| Requirement | Solution / Technology | Satisfied? |
|------------|------------------------|------------|
| OO Language | Java (Spring Boot backend) | ✅ |
| RESTful Service | CoinGecko API, backend REST endpoints | ✅ |
| Web-Based UI | React (JS/TS) | ✅ |
| Persisted State | MongoDB (users, accounts, orders, portfolios) | ✅ |
| Dependency Injection | Spring Boot services, repositories | ✅ |
| Abstractions/Polymorphism | Domain models for trades/orders/accounts | ✅ |
| Responsive Design | React grid / drag-and-drop libs | ✅ |
| Security/Data Protection | JWT, sessions, encrypted API keys | ✅ |

---

## **🧩 Design Patterns Used**

- **Factory Pattern** – For generating order types (market, limit) and coin objects.
- **Strategy Pattern** – For portfolio valuation or order-fill strategies (FIFO, LIFO, avg cost).
- **Observer Pattern** – UI reacts to market updates and completed trades.
- **Repository Pattern** – Abstracts persistence in MongoDB.
- **Command Pattern** – Encapsulates buy/sell actions for future audit or undo/redo support.

---

## **🎨 UI/UX Highlights**

- Modern dashboard using **react-grid-layout** / **react-dnd**.
- Color-coded visual feedback:  
  - **Green** → Success  
  - **Red** → Errors/losses  
  - **Yellow** → Warnings/pending  
- Pop-up notifications and badges.
- Clean, professional nautical-themed branding (future optional).
- **No sound-based notifications** — visual-only.

---

## **🚫 Out-of-Scope (v1)**

- Demo mode (unless used in teaching environments).
- Sound/voice alerts.
- Deep reporting/advanced analytics.
- Non-CoinGecko price sources.
- Real trading capability (simulation only).
- Financial derivatives or advanced instruments.
- Support for obscure/non-major coins.

---

## **✅ Success Criteria**

- Reliable mock order placement and cancellation.
- Real-time dashboard updates (portfolio, orders, market data).
- Fully persistent, consistent state across sessions.
- Clear UI feedback and strong performance.
- At least **5–10 concurrent users** supported with stable backend scaling.
