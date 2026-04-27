import {useEffect, useState} from "react";
import {
    getMentorshipsByAlumni,
    acceptMentorship,
    rejectMentorship,
    cancelMentorship,
    completeMentorship,
    canOpenMessaging,
    buildMessagingRouteState
} from "../mentorshipApi";

import {
    createOpportunity,
    getAllOpportunities,
    updateOpportunity,
    deleteOpportunity
} from "../opportunityApi";

import {
    getApplicationsByOpportunity,
    updateApplicationStatus
} from "../applicationApi";

import { scheduleMeeting, getMeetings } from "../meetingApi";
import {useNavigate} from "react-router-dom";

export default function AlumniDashboard() {
    const userId = localStorage.getItem("userId");
    const [mentorships, setMentorships] = useState([]);

    const [opportunities, setOpportunities] = useState([]);
    const [applications, setApplications] = useState([]);

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [selectedOpportunityId, setSelectedOpportunityId] = useState(null);
    

    const [editId, setEditId] = useState(null);

    const loadOpportunities = async () => {
        const data = await getAllOpportunities();

        const list = Array.isArray(data)
            ? data
            : data?.data
            ? data.data
            : data?.opportunities
            ? data.opportunities
            : [];

        setOpportunities(list);
    };

    const loadApplications = async (opportunityId) => {
        const data = await getApplicationsByOpportunity(opportunityId);
        setApplications(data);
    };

    const handleStatusUpdate = async (id, status) => {
        await updateApplicationStatus(id, status);
        loadApplications(selectedOpportunityId);
    };

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

    useEffect(() => {
    loadOpportunities();
    }, []);

    const navigate = useNavigate();

    const handleMessage = (mentorship) => {
        navigate(`/chat/${mentorship.id}`, {
            state: buildMessagingRouteState(
                mentorship,
                userId,
                "ALUMNI"
            )
        });
    };

    const handleCreate = async () => {
        const userId = localStorage.getItem("userId");

        await createOpportunity({
        title,
        description,
        userId: Number(userId)
        });

        setTitle("");
        setDescription("");

        loadOpportunities();
    };


    const handleUpdate = async () => {
        const userId = localStorage.getItem("userId");

        await updateOpportunity(editId, {
            title,
            description,
            userId: Number(userId)
        });

        setEditId(null);
        setTitle("");
        setDescription("");

        loadOpportunities();
    };

    const handleDelete = async (id) => {
        await deleteOpportunity(id);
        loadOpportunities();
    };

    const handleEditClick = (op) => {
        setEditId(op.opportunityId);
        setTitle(op.title);
        setDescription(op.description);
    };

    return (
        <div className="black-text-conatiner" style={{ padding: "20px" }}>

            <h2>Alumni Dashboard</h2>

            {/* ---------------- MENTORSHIPS ---------------- */}
            <h3>Mentorship Requests</h3>

            <ul>
                {mentorships.map((m) => (
                    <li key={m.id} style={{ marginTop: "15px" }}>

                        <strong>ID:</strong> {m.id} |
                        <strong>Status:</strong> {m.status} |
                        <strong>Student:</strong> {m.studentName}

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

            {/* ---------------- OPPORTUNITIES ---------------- */}
            <h3 style={{ marginTop: "40px" }}>Manage Opportunities</h3>

            {/* FORM */}
            <input
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <br />

            <textarea
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <br />

            {editId ? (
                <button onClick={handleUpdate}>
                    Update Opportunity
                </button>
            ) : (
                <button onClick={handleCreate}>
                    Create Opportunity
                </button>
            )}

            {/* LIST */}
            <ul>
                {opportunities.map((op) => (
                    <li key={op.opportunityId} style={{ marginTop: "15px" }}>

                        <strong>{op.title}</strong>
                        <p>{op.description}</p>

                        <button onClick={() => handleEditClick(op)}>
                            Edit
                        </button>

                        <button onClick={() => handleDelete(op.opportunityId)}>
                            Delete
                        </button>
                        <button onClick={() => loadApplications(op.opportunityId)}>
                            View Applications
                        </button>

                    </li>
                ))}
            </ul>
            {/* ================= APPLICATIONS ================= */}
            {applications.length > 0 && (
                <div style={{ marginTop: "30px" }}>
                    <h3>Applications</h3>

                    {applications.map((a) => (
                        <div key={a.applicationId} style={{
                            marginTop: "10px",
                            padding: "10px",
                            border: "1px solid #ccc",
                            borderRadius: "5px"
                        }}>

                            <p>
                                <strong>Student:</strong> {a.studentId} |
                                <strong>Status:</strong> {a.status}
                            </p>

                            <button onClick={() => handleStatusUpdate(a.applicationId, "ACCEPTED")}>
                                Accept
                            </button>

                            <button onClick={() => handleStatusUpdate(a.applicationId, "REJECTED")}>
                                Reject
                            </button>

                        </div>
                    ))}
                </div>
            )}

        </div>
    );
}
 

      