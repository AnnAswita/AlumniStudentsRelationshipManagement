import { useEffect, useState } from "react";
import { getEvents } from "../api/eventApi";

export default function EventList({ onSelect }) {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    getEvents().then(setEvents);
  }, []);

  return (
    <div>
      <h2>Upcoming Events</h2>
      {events.map(ev => (
        <div
          key={ev.id}
          onClick={() => onSelect(ev.id)}
          style={{ cursor: "pointer", padding: 10, borderBottom: "1px solid #ccc", backgroundColor: "white", minWidth: "300px" }}
        >
          <h3>{ev.title}</h3>
          <p>{ev.description}</p>
        </div>
      ))}
    </div>
  );
}
