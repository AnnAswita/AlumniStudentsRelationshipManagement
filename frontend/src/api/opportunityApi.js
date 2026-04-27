const BASE_URL = "http://localhost:8083/api/opportunities";

const getToken = () => localStorage.getItem("token");
// CREATE
export async function createOpportunity(data) {
    const token = getToken();

    const response = await fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });

    const result = await response.text();

    console.log("CREATE RESPONSE:", result);

    if (!response.ok) {
        throw new Error(result);
    }

    return JSON.parse(result);
}

// GET ALL
export async function getAllOpportunities() {
    const response = await fetch(BASE_URL);
    return response.json();
}

// GET BY ID
export async function getOpportunityById(id) {
    const response = await fetch(`${BASE_URL}/${id}`);
    return response.json();
}

// UPDATE
export async function updateOpportunity(id, data) {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    const payload = {
        ...data,
        userId: Number(userId)
    };

    console.log("UPDATE PAYLOAD:", payload);

    const response = await fetch(`${BASE_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(payload)
    });

    const text = await response.text();
    console.log("UPDATE RESPONSE:", text);

    if (!response.ok) {
        throw new Error(text);
    }

    return JSON.parse(text);
}

// DELETE
export async function deleteOpportunity(id) {
    await fetch(`${BASE_URL}/${id}`, {
        method: "DELETE"
    });
}