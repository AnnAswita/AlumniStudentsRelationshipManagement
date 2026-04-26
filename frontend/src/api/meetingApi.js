import {apiFetch} from "./apiFetch";

const BASE_URL = "http://localhost:8085/api/meeting";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export async function scheduleMeeting(mentorshipId) {
    const body = {
        mentorshipId,
        description: "Auto-generated meeting",
        date: new Date().toISOString().slice(0,16)
    };
    return apiFetch(`${BASE_URL}/schedule`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify(body)
    });
}

export async function getMeetings(mentorshipId) {
    return apiFetch(`${BASE_URL}?mentorshipId=${mentorshipId}`, {
        headers: { ...authHeader() }
    });
}
