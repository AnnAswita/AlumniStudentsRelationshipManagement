import { useState, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

function App() {
  const [conversationId, setConversationId] = useState(1);
  const [senderId, setSenderId] = useState(101);
  const [receiverId, setReceiverId] = useState(201);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [connected, setConnected] = useState(false);

  const stompClientRef = useRef(null);

  const connect = () => {
    if (stompClientRef.current && stompClientRef.current.connected) {
      return;
    }

    const socket = new SockJS("http://localhost:8081/chat");

    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        setConnected(true);

        client.subscribe("/topic/messages", (msg) => {
          const body = JSON.parse(msg.body);
          setMessages((prev) => [...prev, body]);
        });

        console.log("Connected to WebSocket");
      },
      onDisconnect: () => {
        setConnected(false);
      },
      onStompError: (frame) => {
        console.error("STOMP error:", frame);
      },
      onWebSocketError: (error) => {
        console.error("WebSocket error:", error);
      }
    });

    client.activate();
    stompClientRef.current = client;
  };

  const sendMessage = () => {
    if (!stompClientRef.current || !connected || !message.trim()) {
      return;
    }

    const payload = {
      conversationId: Number(conversationId),
      senderId: Number(senderId),
      receiverId: Number(receiverId),
      content: message
    };

    stompClientRef.current.publish({
      destination: "/app/send",
      body: JSON.stringify(payload)
    });

    setMessage("");
  };

  const loadMessages = async () => {
    try {
      const res = await fetch(`http://localhost:8081/messages/${conversationId}`);
      const data = await res.json();
      setMessages(data);
    } catch (error) {
      console.error("Failed to load messages:", error);
    }
  };

  const markAsRead = async (id) => {
    try {
      await fetch(`http://localhost:8081/messages/read/${id}`, {
        method: "PUT"
      });

      setMessages((prev) =>
          prev.map((msg) =>
              msg.id === id ? { ...msg, read: true } : msg
          )
      );
    } catch (error) {
      console.error("Failed to mark message as read:", error);
    }
  };

  return (
      <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
        <h2>Alumni–Student Messaging UI</h2>

        <div style={{ marginBottom: "15px" }}>
          <label>Conversation ID: </label>
          <input
              value={conversationId}
              onChange={(e) => setConversationId(e.target.value)}
              style={{ marginRight: "10px" }}
          />

          <label>Sender ID: </label>
          <input
              value={senderId}
              onChange={(e) => setSenderId(e.target.value)}
              style={{ marginRight: "10px" }}
          />

          <label>Receiver ID: </label>
          <input
              value={receiverId}
              onChange={(e) => setReceiverId(e.target.value)}
          />
        </div>

        <div style={{ marginBottom: "15px" }}>
          <button onClick={connect} style={{ marginRight: "10px" }}>
            Connect
          </button>
          <button onClick={loadMessages}>
            Load Messages
          </button>
          <span style={{ marginLeft: "15px" }}>
          Status: {connected ? "Connected" : "Disconnected"}
        </span>
        </div>

        <div
            style={{
              border: "1px solid #ccc",
              borderRadius: "8px",
              padding: "15px",
              height: "350px",
              overflowY: "auto",
              marginBottom: "15px",
              background: "#f9f9f9"
            }}
        >
          {messages.length === 0 ? (
              <p>No messages yet.</p>
          ) : (
              messages.map((msg, index) => (
                  <div
                      key={msg.id ?? index}
                      style={{
                        marginBottom: "10px",
                        padding: "10px",
                        borderRadius: "8px",
                        background: msg.senderId === Number(senderId) ? "#d1e7dd" : "#fff",
                        border: "1px solid #ddd"
                      }}
                  >
                    <div>
                      <strong>{msg.senderId}</strong> → {msg.receiverId}
                    </div>
                    <div>{msg.content}</div>
                    <div style={{ fontSize: "12px", color: "#555" }}>
                      {msg.timestamp}
                    </div>
                    <div style={{ fontSize: "12px", color: msg.read ? "green" : "red" }}>
                      {msg.read ? "Read" : "Unread"}
                    </div>
                    {!msg.read && msg.receiverId === Number(senderId) && (
                        <button
                            onClick={() => markAsRead(msg.id)}
                            style={{ marginTop: "6px" }}
                        >
                          Mark as Read
                        </button>
                    )}
                  </div>
              ))
          )}
        </div>

        <div>
          <input
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Type a message..."
              style={{ width: "70%", marginRight: "10px", padding: "8px" }}
          />
          <button onClick={sendMessage}>Send</button>
        </div>
      </div>
  );
}

export default App;
