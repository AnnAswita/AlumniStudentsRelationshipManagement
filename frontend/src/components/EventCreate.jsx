import { useState } from "react";
import { createEvent } from "../api/eventApi";

export default function EventCreate() {
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userId = "11111111-1111-1111-1111-111111111111"; // replace with real user ID

    await createEvent({ title, description: desc }, userId);
    alert("Event created!");
    setTitle("");
    setDesc("");
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 20 }}>
      <h2>Create Event</h2>

      <input
        placeholder="Event Title"
        value={title}
        onChange={e => setTitle(e.target.value)}
        style={{ display: "block", marginBottom: 10 }}
      />

      <textarea
        placeholder="Description"
        value={desc}
        onChange={e => setDesc(e.target.value)}
        style={{ display: "block", marginBottom: 10 }}
      />

      <button type="submit">Create</button>
    </form>
  );
}
