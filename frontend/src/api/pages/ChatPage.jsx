import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import {
    getWebSocketUrl,
    startConversation,
    getConversationMessages,
    sendMessage as sendMessageApi,
    markMessageAsRead
} from "../messageApi";

export default function ChatPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const stompClientRef = useRef(null);
    const initializedRef = useRef(false);

    const {
        mentorshipId,
        mentorshipStatus,
        senderId,
        receiverId,
        studentId,
        alumniId,
        studentName,
        alumniName
    } = location.state || {};

    const [conversationId, setConversationId] = useState(null);
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");
    const [connected, setConnected] = useState(false);
    const [loading, setLoading] = useState(true);

    const messagingAllowed = ["ACCEPTED", "ACTIVE"].includes(
        String(mentorshipStatus || "").toUpperCase()
    );

    useEffect(() => {
        if (initializedRef.current) return;
        initializedRef.current = true;

        if (!location.state) {
            navigate("/");
            return;
        }

        initializeChat();

        return () => {
            if (stompClientRef.current) {
                stompClientRef.current.deactivate();
            }
        };
    }, []);

    const initializeChat = async () => {
        try {
            if (!messagingAllowed) {
                setLoading(false);
                return;
            }

            const conversation = await startConversation(senderId, receiverId);
            const convId = conversation.conversationId || conversation.id;
            setConversationId(convId);

            const history = await getConversationMessages(convId);
            setMessages(history);

            connectWebSocket();
        } catch (error) {
            console.error("Failed to initialize chat:", error);
        } finally {
            setLoading(false);
        }
    };

    const getSenderDisplayName = (msgSenderId) => {
        if (msgSenderId === Number(senderId)) {
            return "You";
        }

        if (msgSenderId === Number(studentId)) {
            return studentName || "Student";
        }

        if (msgSenderId === Number(alumniId)) {
            return alumniName || "Alumni";
        }

        return "Other User";
    };

    const connectWebSocket = () => {
        if (stompClientRef.current && stompClientRef.current.connected) {
            return;
        }

        const socket = new SockJS(getWebSocketUrl());

        const client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            onConnect: () => {
                setConnected(true);

                client.subscribe("/topic/messages", (msg) => {
                    const body = JSON.parse(msg.body);

                    setMessages((prev) => {
                        const exists = prev.some(
                            (m) =>
                                (m.id && body.id && m.id === body.id) ||
                                (
                                    m.content === body.content &&
                                    m.senderId === body.senderId &&
                                    m.timestamp === body.timestamp
                                )
                        );
                        return exists ? prev : [...prev, body];
                    });
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

    const handleSend = async () => {
        if (!message.trim() || !conversationId) {
            return;
        }

        const payload = {
            conversationId,
            senderId: Number(senderId),
            receiverId: Number(receiverId),
            studentId: Number(studentId),
            alumniId: Number(alumniId),
            content: message
        };

        try {
            if (stompClientRef.current && connected) {
                stompClientRef.current.publish({
                    destination: "/app/send",
                    body: JSON.stringify(payload)
                });
            } else {
                const saved = await sendMessageApi(payload);
                setMessages((prev) => [...prev, saved]);
            }

            setMessage("");
        } catch (error) {
            console.error("Failed to send message:", error);
        }
    };

    const handleMarkAsRead = async (id) => {
        try {
            await markMessageAsRead(id);

            setMessages((prev) =>
                prev.map((msg) =>
                    msg.id === id ? { ...msg, read: true } : msg
                )
            );
        } catch (error) {
            console.error("Failed to mark message as read:", error);
        }
    };

    if (loading) {
        return <div style={{ padding: "20px" }}>Loading chat...</div>;
    }

    if (!messagingAllowed) {
        return (
            <div style={{ padding: "20px" }}>
                <h2>Messaging</h2>
                <p>
                    Mentorship status: <strong>{mentorshipStatus}</strong>
                </p>
                <p>Messaging is disabled until the mentorship becomes ACCEPTED or ACTIVE.</p>
            </div>
        );
    }

    return (
        <div style={{ padding: "20px", maxWidth: "900px", margin: "0 auto" }}>
            <h2>Chat</h2>

            <div style={{ marginBottom: "12px" }}>
                <div><strong>Mentorship ID:</strong> {mentorshipId}</div>
                <div><strong>Conversation ID:</strong> {conversationId}</div>
                <div><strong>Status:</strong> {connected ? "Connected" : "Disconnected"}</div>
            </div>

            <div
                style={{
                    border: "1px solid #ccc",
                    borderRadius: "8px",
                    padding: "15px",
                    height: "400px",
                    overflowY: "auto",
                    background: "#f9f9f9",
                    marginBottom: "15px",
                    display: "flex",
                    flexDirection: "column"
                }}
            >
                {messages.length === 0 ? (
                    <p>No messages yet.</p>
                ) : (
                    messages.map((msg, index) => {
                        const isMine = msg.senderId === Number(senderId);

                        return (
                            <div
                                key={msg.id ?? index}
                                style={{
                                    marginBottom: "10px",
                                    padding: "10px",
                                    borderRadius: "10px",
                                    background: isMine ? "#d1e7dd" : "#fff",
                                    border: "1px solid #ddd",
                                    alignSelf: isMine ? "flex-end" : "flex-start",
                                    maxWidth: "65%"
                                }}
                            >
                                <div>
                                    <strong>{getSenderDisplayName(msg.senderId)}</strong>
                                </div>

                                <div>{msg.content}</div>

                                <div style={{ fontSize: "12px", color: "#555" }}>
                                    {msg.timestamp ? new Date(msg.timestamp).toLocaleString() : ""}
                                </div>

                                {!isMine && (
                                    <div style={{ fontSize: "12px", color: msg.read ? "green" : "red" }}>
                                        {msg.read ? "Read" : "Unread"}
                                    </div>
                                )}

                                {!msg.read && msg.receiverId === Number(senderId) && (
                                    <button
                                        onClick={() => handleMarkAsRead(msg.id)}
                                        style={{ marginTop: "6px" }}
                                    >
                                        Mark as Read
                                    </button>
                                )}
                            </div>
                        );
                    })
                )}
            </div>

            <div style={{ display: "flex", gap: "10px" }}>
                <input
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Type a message..."
                    style={{ flex: 1, padding: "10px" }}
                />
                <button onClick={handleSend}>Send</button>
            </div>
        </div>
    );
}