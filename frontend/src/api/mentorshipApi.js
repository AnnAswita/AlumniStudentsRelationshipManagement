import {apiFetch} from "./apiFetch";

const BASE_URL = "http://localhost:8085/api/mentorship";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export async function getMentorshipsByStudent(studentId) {
    return apiFetch(`${BASE_URL}/student/${studentId}`, {
        headers: { ...authHeader() }
    });
}

export async function getMentorshipsByAlumni(alumniId) {
    return apiFetch(`${BASE_URL}/alumni/${alumniId}`, {
        headers: { ...authHeader() }
    });
}

export async function acceptMentorship(id) {
    return apiFetch(`${BASE_URL}/${id}/accept`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function rejectMentorship(id) {
    return apiFetch(`${BASE_URL}/${id}/reject`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function cancelMentorship(id) {
    return apiFetch(`${BASE_URL}/${id}/cancel`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function completeMentorship(id) {
    return apiFetch(`${BASE_URL}/${id}/complete`, {
        method: "PUT",
        headers: { ...authHeader() }
    });
}

export async function requestMentorship(studentId, alumniId) {
    return apiFetch(`http://localhost:8085/api/mentorship/request`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        },
        body: JSON.stringify({ studentId, alumniId })
    });
}
