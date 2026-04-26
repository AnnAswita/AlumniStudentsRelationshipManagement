import {apiFetch} from "./apiFetch";

const BASE_URL = "http://localhost:8081/users/role/ALUMNI";

function authHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

export async function getAllAlumni() {
   return apiFetch(BASE_URL, {
        headers: { ...authHeader() }
    });
}
