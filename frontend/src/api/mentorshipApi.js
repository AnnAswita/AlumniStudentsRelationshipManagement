const BASE_URL = "http://localhost:8085/api/mentorship";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export async function getMentorshipsByStudent(studentId) {
    const res = await fetch(`${BASE_URL}/student/${studentId}`, {
        headers: { ...authHeader() }
    });
    return res.json();
}

export async function getMentorshipsByAlumni(alumniId) {
    const res = await fetch(`${BASE_URL}/alumni/${alumniId}`, {
        headers: { ...authHeader() }
    });
    return res.json();
}

export async function acceptMentorship(id) {
    return fetch(`${BASE_URL}/${id}/accept`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function rejectMentorship(id) {
    return fetch(`${BASE_URL}/${id}/reject`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function cancelMentorship(id) {
    return fetch(`${BASE_URL}/${id}/cancel`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function completeMentorship(id) {
    return fetch(`${BASE_URL}/${id}/complete`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function requestMentorship(studentId, alumniId) {
    const res = await fetch(`http://localhost:8085/api/mentorship/request`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify({ studentId, alumniId })
    });
    return res.json();
}
