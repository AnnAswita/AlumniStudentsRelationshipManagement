const BASE_URL = "http://localhost:8080/events";

export async function getEvents() {
  const res = await fetch(BASE_URL);
  return res.json();
}

export async function getEvent(id) {
  const res = await fetch(`${BASE_URL}/${id}`);
  return res.json();
}

export async function createEvent(event, userId) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-User-Id": userId
    },
    body: JSON.stringify(event)
  });
  return res.json();
}

export async function registerForEvent(eventId, userId) {
  return fetch(`${BASE_URL}/${eventId}/register`, {
    method: "POST",
    headers: { "X-User-Id": userId }
  });
}

export async function unregister(eventId, userId) {
  return fetch(`${BASE_URL}/${eventId}/register`, {
    method: "DELETE",
    headers: { "X-User-Id": userId }
  });
}

export async function getParticipants(eventId) {
  const res = await fetch(`${BASE_URL}/${eventId}/participants`);
  return res.json();
}
