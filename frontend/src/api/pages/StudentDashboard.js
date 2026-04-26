import { useState, useEffect } from "react";
import {
    getMentorshipsByStudent,
    cancelMentorship,
    completeMentorship,
    canOpenMessaging,
    buildMessagingRouteState
} from "../mentorshipApi";
import { useNavigate } from "react-router-dom";

export default function StudentDashboard() {
    const [mentorships, setMentorships] = useState([]);
    const userId = localStorage.getItem("userId");
    const navigate = useNavigate();

    const loadMentorships = async () => {
        if (!userId) return;
        const data = await getMentorshipsByStudent(userId);
        setMentorships(data);
    };

    useEffect(() => {
        loadMentorships();
    }, []);

    const handleCancel = async (id) => {
        await cancelMentorship(id);
        loadMentorships();
    };

    const handleComplete = async (id) => {
        await completeMentorship(id);
        loadMentorships();
    };

    const handleMessage = (mentorship) => {
        navigate(`/chat/${mentorship.id}`, {
            state: buildMessagingRouteState(
                mentorship,
                userId,
                "STUDENT"
            )
        });
    };

    return (
        <div className="black-text-conatiner" style={{ padding: "20px" }}>
            <h2>Student Dashboard</h2>

            <button
                onClick={() => navigate("/student/request-mentorship")}
                style={{
                    padding: "10px",
                    backgroundColor: "#28a745",
                    color: "white",
                    border: "none",
                    borderRadius: "5px",
                    cursor: "pointer"
                }}
            >
                Request Mentorship
            </button>

            <ul>
                {mentorships.map((m) => (
                    <li key={m.id} style={{ marginTop: "15px" }}>
                        <strong>ID:</strong> {m.id} |
                        <strong>Status:</strong> {m.status} |
                        <strong>Alumni:</strong> {m.alumniName}
                        <br />

                        {(m.status === "REQUESTED" ||
                            m.status === "ACCEPTED" ||
                            m.status === "ACTIVE") && (
                            <button onClick={() => handleCancel(m.id)} style={{ marginRight: "5px" }}>
                                Cancel
                            </button>
                        )}

                        {m.status === "ACTIVE" && (
                            <button onClick={() => handleComplete(m.id)}>
                                Complete
                            </button>
                        )}

                        <button onClick={() => navigate(`/meeting/view/${m.id}`)}>
                            View Meetings
                        </button>

                        {(m.status === "ACCEPTED" || m.status === "ACTIVE") && (
                            <button
                                onClick={() => navigate(`/meeting/create/${m.id}`)}
                                style={{ marginLeft: "5px" }}
                            >
                                Schedule Meeting
                            </button>
                        )}

                        <button
                            onClick={() => handleMessage(m)}
                            disabled={!canOpenMessaging(m.status)}
                            style={{ marginLeft: "5px" }}
                        >
                            {canOpenMessaging(m.status) ? "Message" : "Messaging Disabled"}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}