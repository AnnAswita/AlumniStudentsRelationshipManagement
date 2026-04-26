const MESSAGE_BASE_URL = "http://localhost:8085/api/messages";
const CONVERSATION_BASE_URL = "http://localhost:8085/api/conversations";
const WS_URL = "http://localhost:8087/chat";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export function getWebSocketUrl() {
    return WS_URL;
}

export async function startConversation(senderId, receiverId) {
    const res = await fetch(`${CONVERSATION_BASE_URL}/start`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify({ senderId, receiverId })
    });

    if (!res.ok) {
        throw new Error("Failed to start conversation");
    }

    return res.json();
}

export async function getConversationMessages(conversationId) {
    const res = await fetch(`${MESSAGE_BASE_URL}/${conversationId}`, {
        headers: { ...authHeader() }
    });

    if (!res.ok) {
        throw new Error("Failed to fetch messages");
    }

    return res.json();
}

export async function sendMessage(payload) {
    const res = await fetch(`${MESSAGE_BASE_URL}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify(payload)
    });

    if (!res.ok) {
        throw new Error("Failed to send message");
    }

    return res.json();
}

export async function markMessageAsRead(id) {
    const res = await fetch(`${MESSAGE_BASE_URL}/read/${id}`, {
        method: "PUT",
        headers: { ...authHeader() }
    });

    if (!res.ok) {
        throw new Error("Failed to mark message as read");
    }

    return res.json();
}

export async function getUnreadMessages(receiverId) {
    const res = await fetch(`${MESSAGE_BASE_URL}/unread/${receiverId}`, {
        headers: { ...authHeader() }
    });

    if (!res.ok) {
        throw new Error("Failed to fetch unread messages");
    }

    return res.json();
}