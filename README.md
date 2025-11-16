# WhisperNet Backend
A Spring Boot backend app powering **WhisperNet**, an anonymous real-time encrypted chat network with 1:1 secure messaging, online user presence, typing indicators, and ephemeral sessions.

---

## 🚀 Features

### 🔒 **1. End-to-End Encrypted 1:1 Messaging**
Messages are encrypted **client-side** and sent as ciphertext.  
The server **never sees plaintext**.  
The backend only routes encrypted payloads to the correct receiver.

### 👤 **2. Anonymous Real-Time Online Presence**
WhisperNet keeps track of:
- users connecting
- users disconnecting
- active sessions

Live updates are broadcast over `/topic/public`.

### 🟢 **3. Live User Directory**
The backend maintains a runtime in-memory directory of all online users.  
When someone joins or leaves, everyone is notified instantly.

### 💬 **4. Direct Encrypted Message Routing**
Messages are delivered via:
/queue/messages
using:
- SimpMessagingTemplate
- User-specific STOMP queues

### ✍️ **5. Typing Indicators**
Typing notifications are sent securely via:
/queue/typing
This enables “User is typing…” alerts in the UI.

### 🧼 **6. Ephemeral Sessions (Auto-Message Deletion)**
When BOTH users disconnect:
- Chat session is destroyed
- Messages held in memory during conversation are wiped
- No logs, no traces, zero retention

Perfect for anonymous / privacy-focused chats.

### 🔌 **7. Tech-Stack for Backend**
Backend uses:
- Spring WebSocket
- STOMP message broker
- SockJS fallback transport

This ensures compatibility across browsers and deployment environments.