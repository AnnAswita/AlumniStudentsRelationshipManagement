import { useEffect, useState } from "react";
import {
  getEvent,
  registerForEvent,
  unregister,
  getParticipants
} from "../api/eventApi";

export default function EventDetails({ eventId }) {
  const [event, setEvent] = useState(null);
  const [participants, setParticipants] = useState([]);

  const userId = "11111111-1111-1111-1111-111111111111"; // replace with real user ID

  useEffect(() => {
    getEvent(eventId).then(setEvent);
    getParticipants(eventId).then(setParticipants);
  }, [eventId]);

  if (!event) return <p>Loading...</p>;

  return (
    <div>
      <h2>{event.title}</h2>
      <p>{event.description}</p>

      <button onClick={() => registerForEvent(eventId, userId)}>
        Register
      </button>

      <button onClick={() => unregister(eventId, userId)} style={{ marginLeft: 10 }}>
        Unregister
      </button>

      <h3 style={{ marginTop: 20 }}>Participants</h3>
      <ul>
        {participants.map(p => (
          <li key={p.id}>{p.studentId}</li>
        ))}
      </ul>
    </div>
  );
}
