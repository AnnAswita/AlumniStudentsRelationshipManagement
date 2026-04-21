const BASE_URL = "http://localhost:8085/api/meeting";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export async function scheduleMeeting(mentorshipId) {
    const res = await fetch(`${BASE_URL}/schedule`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify({ mentorshipId })
    });
    return res.json();
}

export async function getMeetings(mentorshipId) {
    const res = await fetch(`${BASE_URL}?mentorshipId=${mentorshipId}`, {
        headers: { ...authHeader() }
    });
    return res.json();
}
