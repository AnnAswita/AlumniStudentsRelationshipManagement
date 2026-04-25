import {useEffect, useState} from "react";
import {
    getMentorshipsByAlumni,
    acceptMentorship,
    rejectMentorship,
    cancelMentorship,
    completeMentorship
} from "../mentorshipApi";
import { scheduleMeeting, getMeetings } from "../meetingApi";

export default function AlumniDashboard() {
    const userId = localStorage.getItem("userId");
    const [mentorships, setMentorships] = useState([]);

    const loadMentorships = async () => {
       if (!userId) return;
        const data = await getMentorshipsByAlumni(userId);
        setMentorships(data);
    };
    const handleAccept = async (id) => {
        await acceptMentorship(id);
        loadMentorships();
    };

    const handleReject = async (id) => {
        await rejectMentorship(id);
        loadMentorships();
    };
    const handleCancel = async (id) => {
        await cancelMentorship(id);
        loadMentorships();
    };
    const handleComplete = async (id) => {
        await completeMentorship(id);
        loadMentorships();
    };

    useEffect(() => {
        loadMentorships();
    }, []);
    return (
        <div style={{ padding: "20px" }}>
            <h2>Alumni Dashboard</h2>
            <ul>
                {mentorships.map((m) => (
                    <li key={m.id} style={{ marginTop: "15px" }}>
                        <strong>ID:</strong> {m.id} |
                        <strong>Status:</strong> {m.status} |
                        <strong>Student:</strong> {m.studentId}
                        <br />

                        {m.status === "REQUESTED" && (
                            <>
                                <button onClick={() => handleAccept(m.id)} style={{ marginRight: "5px" }}>
                                    Accept
                                </button>
                                <button onClick={() => handleReject(m.id)} style={{ marginRight: "5px" }}>
                                    Reject
                                </button>
                            </>
                        )}

                        {(m.status === "ACTIVE" || m.status === "ACCEPTED") && (
                            <button onClick={() => handleCancel(m.id)} style={{ marginRight: "5px" }}>
                                Cancel
                            </button>
                        )}

                        {m.status === "ACTIVE" && (
                            <button onClick={() => handleComplete(m.id)} style={{ marginRight: "5px" }}>
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
