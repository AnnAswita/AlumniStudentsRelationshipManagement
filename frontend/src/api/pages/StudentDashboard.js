import { useState } from "react";
import {
    getMentorshipsByStudent,
    cancelMentorship,
    completeMentorship
} from "../mentorshipApi";
import { scheduleMeeting, getMeetings } from "../meetingApi";
import { useNavigate } from "react-router-dom";

export default function StudentDashboard() {
    const [studentId, setStudentId] = useState("");
    const [mentorships, setMentorships] = useState([]);

    const loadMentorships = async () => {
        const data = await getMentorshipsByStudent(studentId);
        setMentorships(data);
    };

    const handleCancel = async (id) => {
        await cancelMentorship(id);
        loadMentorships();
    };

    const handleComplete = async (id) => {
        await completeMentorship(id);
        loadMentorships();
    };

    const handleSchedule = async (id) => {
        await scheduleMeeting(id);
        alert("Meeting scheduled");
    };

    const handleViewMeetings = async (id) => {
        const data = await getMeetings(id);
        alert(JSON.stringify(data, null, 2));
    };
    const navigate = useNavigate();

    return (
        <div style={{ padding: "20px" }}>
            <h2>Student Dashboard</h2>

            <input
                placeholder="Enter Student ID"
                value={studentId}
                onChange={(e) => {
                    setStudentId(e.target.value);
                    localStorage.setItem("studentId", e.target.value);
                }}
                style={{ padding: "10px", marginRight: "10px" }}
            />

            <button onClick={loadMentorships} style={{ marginRight: "10px" }}>
                Load Mentorships
            </button>

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
                        <strong>Alumni:</strong> {m.alumniId}
                        <br />

                        <button onClick={() => handleViewMeetings(m.id)} style={{ marginRight: "5px" }}>
                            View Meetings
                        </button>

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

                        <button onClick={() => handleSchedule(m.id)} style={{ marginLeft: "5px" }}>
                            Schedule Meeting
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
