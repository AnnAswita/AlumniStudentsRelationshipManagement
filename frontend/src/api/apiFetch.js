export async function apiFetch(url, options = {}) {
    const res = await fetch(url, options);

    // Only logout on actual authentication failures
    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        window.location.href = "/login";
        return;
    }

    let data;
    try {
        data = await res.json();
    } catch {
        data = null;
    }

    // For all other non-OK responses, throw error to UI
    if (!res.ok) {
        const message =
            (data && (data.message || data.error)) ||
            "Something went wrong";

        throw new Error(message);
    }

    return data;
}
