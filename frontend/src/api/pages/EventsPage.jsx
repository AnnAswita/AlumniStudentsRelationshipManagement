import { useState } from "react";
import EventList from "../../components/EventList";
import EventCreate from "../../components/EventCreate";
import EventDetails from "../../components/EventDetails";

export default function EventsPage() {
  const [selectedEvent, setSelectedEvent] = useState(null);

  return (
    <div className="black-text-conatiner" style={{ padding: 20 }}>
      <h1>Event Management</h1>

      <EventCreate />

      {!selectedEvent && <EventList onSelect={setSelectedEvent} />}
      {selectedEvent && <EventDetails eventId={selectedEvent} />}
    </div>
  );
}
