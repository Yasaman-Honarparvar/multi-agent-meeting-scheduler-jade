# JADE Multi-Agent Meeting Scheduler
A complete multi-agent system implemented using **JADE** to simulate a meeting scheduling workflow with automated negotiation, notification, and calendar management.
This project implements **three distinct use cases**, each demonstrating different interaction paths between User, Negotiator, Calendar, and Notifier agents.

---

## 📌 Project Overview
This system simulates how autonomous agents collaborate to schedule or update meetings.
Agents negotiate time slots, check calendar availability, and notify the user of final results.

### System Agents

| Agent | Responsibility |
|-------|----------------|
| **UserAgent** | Initiates the scenario (UC1, UC2, UC3) |
| **NegotiatorAgent** | Coordinates all scheduling logic |
| **CalendarAgent** | Validates and stores availability/time slots |
| **NotifierAgent** | Sends final confirmation or rejection messages |
| **Sniffer/PrettySniffer** | Visual tracing of ACL message interactions |

All agents except **UserAgent** start automatically inside JADE's RMA.
The user launches different scenarios by starting UserAgent with arguments.

In JADE RMA:

Name: User
Class: app.agents.UserAgent

Arguments:

UC1 — for meeting request

UC2 — for rescheduling

UC3 — for cancellation

Then press Start.


---

## 📌 Implemented Use Cases

### ✔ Use Case 1 — Initial Meeting Request
**Goal:** User requests a meeting → system checks availability → confirms the meeting.

Flow:
1. UserAgent sends a meeting request  
2. Negotiator checks availability via CalendarAgent  
3. CalendarAgent confirms the time slot  
4. Negotiator approves  
5. NotifierAgent sends confirmation to the user  

📸 **Sniffer Output (UC1)**
![UC1](pics/UC1.png)

---

### ✔ Use Case 2 — Conflict Detected & Alternative Returned (Rescheduling an Existing Meeting)
**Goal:** Requested time is unavailable → system proposes an alternative.

Flow:
1. UserAgent requests a time slot  
2. CalendarAgent reports conflict  
3. Negotiator suggests an alternative  
4. CalendarAgent confirms  
5. NotifierAgent sends updated schedule  

📸 **Sniffer Output (UC2)**
![UC2](pics/UC2.png)

---

### ✔ Use Case 3 — Cancelling a Meeting
**Goal:** Multiple conflicts → system keeps negotiating until a free time is found.

Flow:
1. Initial Request → Conflict  
2. Alternative 1 → Conflict  
3. Alternative 2 → Accepted  
4. NotifierAgent informs user  

📸 **Sniffer Output (UC3)**
![UC3](pics/UC3.png)

---

## 📂 Project Structure

```
src/
└── main/java/app/
    ├── agents/
    │   ├── UserAgent.java
    │   ├── NegotiatorAgent.java
    │   ├── CalendarAgent.java
    │   ├── NotifierAgent.java
    │   └── PrettySnifferAgent.java
    ├── messages/
    │   ├── Models.java
    │   └──JsonUtil.java
    ├── util/
    │   └── LogWriter.java
    └── boot/
        └── GuiAutoBoot.java

logs/
├── UC1.log
├── UC2.log
└── UC3.log
```

Each use case automatically generates its own log file (e.g., `UC1.log`).

---

## 🔧 How to Compile

```sh
javac -cp "libs/*;src/main/java" -d build/classes/java/main   src/main/java/app/messages/*.java   src/main/java/app/util/*.java   src/main/java/app/agents/*.java   src/main/java/app/boot/*.java
```

---

## ▶ How to Run (Start JADE Platform)

Start all agents **except UserAgent**:

```sh
javac -cp "libs\jade-4.5.0.jar;libs\gson-2.10.1.jar;libs\json-20210307.jar;src\main\java" `
  -d build\classes\java\main `
  src\main\java\app\messages\*.java `
  src\main\java\app\util\*.java `
  src\main\java\app\agents\*.java `
  src\main\java\app\boot\AutoBoot.java

```

This launches:

- CalendarAgent
- NegotiatorAgent
- NotifierAgent
- PrettySnifferAgent
- RMA Tools

---

## ▶ How to Run Each Use Case

### Use Case 1
```sh
java "-Ddemo.uc=UC1" -cp ".;libs/*;build/classes/java/main" app.boot.AutoBoot
```

### Use Case 2
```sh
java "-Ddemo.uc=UC2" -cp ".;libs/*;build/classes/java/main" app.boot.AutoBoot
```

### Use Case 3
```sh
java "-Ddemo.uc=UC3" -cp ".;libs/*;build/classes/java/main" app.boot.AutoBoot
```

---

## 📝 Logging

Each run writes ACL messages into:

```
logs/UC1.log
logs/UC2.log
logs/UC3.log
```

These logs match the Sniffer message flow.

---

## 📌 Requirements

- Java 8+
- JADE 4.5.0
- gson 2.10.1
- org.json 20210307

---

## 📜 License
MIT License – free to use and modify.

---

## 📧 Contact
For questions or extensions, feel free to reach out.
