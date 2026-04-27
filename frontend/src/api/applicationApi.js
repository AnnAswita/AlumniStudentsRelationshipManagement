const BASE_URL = "http://localhost:8083/api/applications";

const getToken = () => localStorage.getItem("token");
// APPLY
export async function applyForOpportunity(data) {
    const token = getToken();

    const response = await fetch(`${BASE_URL}/apply`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });

    return response.json();
}

// UPDATE STATUS

export async function updateApplicationStatus(id, status) {
    const token = getToken();

    const response = await fetch(
        `${BASE_URL}/${id}/status?status=${status}`,
        {
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${token}` // ✅ REQUIRED
            }
        }
    );

    if (!response.ok) {
        throw new Error("Failed to update status");
    }

    return response.json();
}

// GET BY STUDENT
export async function getApplicationsByStudent(studentId) {
    const token = getToken();

    const response = await fetch(
        `${BASE_URL}/student/${studentId}`,
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    return response.json();
}

// GET BY OPPORTUNITY
export async function getApplicationsByOpportunity(opportunityId) {
    const token = getToken();

    const response = await fetch(
        `${BASE_URL}/opportunity/${opportunityId}`,
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    return response.json();
}