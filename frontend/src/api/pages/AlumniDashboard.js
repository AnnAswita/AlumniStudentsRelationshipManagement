import { useState } from "react";
import {
    getMentorshipsByAlumni,
    acceptMentorship,
    rejectMentorship,
    cancelMentorship,
    completeMentorship
} from "../mentorshipApi";
import { scheduleMeeting, getMeetings } from "../meetingApi";

export default function AlumniDashboard() {
    const [alumniId, setAlumniId] = useState("");
    const [mentorships, setMentorships] = useState([]);

    const loadMentorships = async () => {
        const data = await getMentorshipsByAlumni(alumniId);
        setMentorships(data);
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Alumni Dashboard</h2>

            <input
                placeholder="Enter Alumni ID"
                value={alumniId}
                onChange={(e) => setAlumniId(e.target.value)}
                style={{ padding: "10px", marginRight: "10px" }}
            />
            <button onClick={loadMentorships}>Load Mentorships</button>

            <ul>
                {mentorships.map((m) => (
                    <li key={m.id} style={{ marginTop: "15px" }}>
                        <strong>ID:</strong> {m.id} |
                        <strong>Status:</strong> {m.status} |
                        <strong>Student:</strong> {m.studentId}
                        <br />

                        {m.status === "REQUESTED" && (
                            <>
                                <button onClick={() => acceptMentorship(m.id)} style={{ marginRight: "5px" }}>
                                    Accept
                                </button>
                                <button onClick={() => rejectMentorship(m.id)} style={{ marginRight: "5px" }}>
                                    Reject
                                </button>
                            </>
                        )}

                        {(m.status === "ACTIVE" || m.status === "ACCEPTED") && (
                            <button onClick={() => cancelMentorship(m.id)} style={{ marginRight: "5px" }}>
                                Cancel
                            </button>
                        )}

                        {m.status === "ACTIVE" && (
                            <button onClick={() => completeMentorship(m.id)} style={{ marginRight: "5px" }}>
                                Complete
                            </button>
                        )}

                        <button onClick={async () => {
                            await scheduleMeeting(m.id);
                            alert("Meeting scheduled");
                        }}>
                            Schedule Meeting
                        </button>

                        <button onClick={async () => {
                            const data = await getMeetings(m.id);
                            alert(JSON.stringify(data, null, 2));
                        }} style={{ marginLeft: "5px" }}>
                            View Meetings
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
