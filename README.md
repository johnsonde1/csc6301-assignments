# Flexible Notification System

A composition-based notification dispatcher built in Java.  
**Author:** Derrick Johnson | Merrimack College  

---

## Project Overview

This project demonstrates the **Composition over Inheritance** design principle.  
An `AlertSystem` dispatcher holds a reference to any `NotificationMedium` — Email, SMS, or WhatsApp — and can swap channels at runtime without modifying any existing code.

| Assignment | Feature Added |
|---|---|
| Assignment 4 | Core system — `EmailService`, `SMSService`, `AlertSystem` with session log |
| Assignment 5 | Extended system — `WhatsAppService` added with zero changes to the core |

---

## File Structure

```
/
├── NotificationMedium.java   # Interface — the contract all channels implement
├── EmailService.java         # Concrete channel: Email
├── SMSService.java           # Concrete channel: SMS
├── WhatsAppService.java      # Concrete channel: WhatsApp (Assignment 5)
└── AlertSystem.java          # Dispatcher + ArrayList log + main entry point
```

---

## Prerequisites

| Tool | Version | Check with |
|---|---|---|
| Java JDK | 11 or higher | `java -version` |

No external libraries or build tools are required.

---

## How to Deploy & Run

### 1. Clone the repository

```bash
git clone https://github.com/johnsonde1/csc6301-assignments.git
cd csc6301-assignments
```

### 2. Create the bin directory and compile all files

```bash
mkdir -p bin
javac -d bin *.java
```

### 3. Run the demo

```bash
java -cp bin AlertSystem.java
```

### Expected Output

```
=== Notification System Demo ===

[EMAIL] Sending message: Your account password was changed.
[EMAIL] Sending message: Your monthly invoice is ready.
[AlertSystem] Medium switched to: SMSService
[SMS] Sending message: Verification code: 482910
[SMS] Sending message: Your order has been dispatched.
[AlertSystem] Medium switched to: WhatsAppService
[WhatsApp] Sending message: Thanks for joining WhatsApp!
[AlertSystem] Medium switched to: EmailService
[EMAIL] Sending message: Welcome to our newsletter!

========== Session Log ==========
   1. [EmailService] Your account password was changed.
   2. [EmailService] Your monthly invoice is ready.
   3. [SMSService] Verification code: 482910
   4. [SMSService] Your order has been dispatched.
   5. [WhatsAppService] Thanks for joining WhatsApp!
   6. [EmailService] Welcome to our newsletter!
  Total messages sent: 6
=================================
```

---

## How to Add a New Notification Channel

No existing files need to be modified. Simply create a new class:

```java
public class SlackService implements NotificationMedium {
    @Override
    public void send(String message) {
        System.out.println("[Slack] Sending message: " + message);
    }
}
```

Then use it in `AlertSystem`:

```java
alert.setMedium(new SlackService());
alert.notifyUser("Build pipeline failed.");
```

Compile and run as normal — the session log captures it automatically.

---
