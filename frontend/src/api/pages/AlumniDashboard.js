import {useEffect, useState} from "react";
import {
    getMentorshipsByAlumni,
    acceptMentorship,
    rejectMentorship,
    cancelMentorship,
    completeMentorship
} from "../mentorshipApi";
import { scheduleMeeting, getMeetings } from "../meetingApi";
import {useNavigate} from "react-router-dom";

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
    const navigate = useNavigate();
    return (
        <div className="black-text-conatiner" style={{ padding: "20px" }}>
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
                        {/* Schedule Meeting allowed only in ACCEPTED or ACTIVE */}
                        {(m.status === "ACCEPTED" || m.status === "ACTIVE") && (
                            <button
                                onClick={() => navigate(`/meeting/create/${m.id}`)}
                                style={{ marginLeft: "5px" }}
                            >
                                Schedule Meeting
                            </button>
                        )}

                        <button onClick={() => navigate(`/meeting/view/${m.id}`)}>
                            View Meetings
                        </button>

                    </li>
                ))}
            </ul>
        </div>
    );
}
