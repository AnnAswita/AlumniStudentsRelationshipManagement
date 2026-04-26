import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMeetings } from "../meetingApi";

export default function ViewMeetingsPage() {
    const { mentorshipId } = useParams();
    const [meetings, setMeetings] = useState([]);

    useEffect(() => {
        load();
    }, []);

    const load = async () => {
        try {
            const data = await getMeetings(mentorshipId);
            setMeetings(data);
        } catch (err) {
            alert(err.message);
        }
    };

    return (
        <div className="black-text-container" style={{ padding: "20px" }}>
            <h2>Meetings</h2>

            {meetings.length === 0 && <p>No meetings yet.</p>}

            <ul>
                {meetings.map((m) => (
                    <li key={m.id} style={{ marginBottom: "15px" }}>
                        <strong>Description:</strong> {m.description} <br />
                        <strong>Date:</strong> {m.date} <br />
                    </li>
                ))}
            </ul>
        </div>
    );
}
