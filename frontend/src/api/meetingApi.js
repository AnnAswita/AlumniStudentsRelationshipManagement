import {apiFetch} from "./apiFetch";

const BASE_URL = "http://localhost:8085/api/meeting";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export function scheduleMeeting(meetingData) {
    return apiFetch("http://localhost:8085/api/meeting/schedule", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(meetingData)
    });
}

export async function getMeetings(mentorshipId) {
    return apiFetch(`${BASE_URL}?mentorshipId=${mentorshipId}`, {
        headers: { ...authHeader() }
    });
}
