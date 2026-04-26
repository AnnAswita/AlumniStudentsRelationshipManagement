import {apiFetch} from "./apiFetch";

const BASE_URL = "http://localhost:8081";

export async function loginUser(credentials) {
    const response = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credentials)
    });

    if (!response.ok) {
        throw new Error("Invalid login credentials");
    }

    return response.json();
}

export async function registerUser(data) {
    return apiFetch(`${BASE_URL}/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
}

