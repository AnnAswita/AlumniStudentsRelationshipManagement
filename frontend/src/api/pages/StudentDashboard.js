import { useState, useEffect } from "react";
import {
    getMentorshipsByStudent,
    cancelMentorship,
    completeMentorship,
    canOpenMessaging,
    buildMessagingRouteState
} from "../mentorshipApi";

import {
    getAllOpportunities, getOpportunityById
} from "../opportunityApi";

import {
    applyForOpportunity,
    getApplicationsByStudent
} from "../applicationApi";

import { useNavigate } from "react-router-dom";

export default function StudentDashboard() {
    const [mentorships, setMentorships] = useState([]);
    const [opportunities, setOpportunities] = useState([]);
    const [selectedOpportunity, setSelectedOpportunity] = useState(null);
    const [applications, setApplications] = useState([]);

    const userId = localStorage.getItem("userId");
    const navigate = useNavigate();


    const loadMentorships = async () => {
        if (!userId) return;
        const data = await getMentorshipsByStudent(userId);
        setMentorships(data);
    };


    const loadOpportunities = async () => {
        const data = await getAllOpportunities();
        setOpportunities(data);
    };

    const handleViewOpportunity = async (id) => {
    const data = await getOpportunityById(id);
    setSelectedOpportunity(data);
    };

    const handleApply = async (opportunityId) => {
        const studentId = localStorage.getItem("userId");

        await applyForOpportunity({
            studentId,
            opportunityId
        });

        alert("Applied successfully!");
    };

    const loadApplications = async () => {
        const studentId = localStorage.getItem("userId");
        const data = await getApplicationsByStudent(studentId);
        setApplications(data);
    };

    useEffect(() => {
        loadMentorships();
        loadOpportunities(); 
        loadApplications();
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

            <h3>My Mentorships</h3>

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

            <h3 style={{ marginTop: "40px" }}>Opportunities</h3>

            <ul>
                {opportunities.map((op) => (
                    <li key={op.opportunityId} style={{ marginTop: "15px" }}>
                        <strong>{op.title}</strong>
                        <p>{op.description}</p>

                        <button
                            onClick={() => handleViewOpportunity(op.opportunityId)}
                            style={{
                                padding: "6px 10px",
                                backgroundColor: "#007bff",
                                color: "white",
                                border: "none",
                                borderRadius: "5px",
                                cursor: "pointer"
                            }}
                        >
                            View Details
                        </button>

                        <button
                            onClick={() => handleApply(op.opportunityId)}
                            style={{
                                padding: "6px 10px",
                                backgroundColor: "#28a745",
                                color: "white",
                                border: "none",
                                borderRadius: "5px",
                                cursor: "pointer",
                                marginLeft: "8px"
                            }}
                        >
                            Apply
                        </button>
                    </li>
                ))}
            </ul>
            <h3 style={{ marginTop: "40px" }}>My Applications</h3>

            {applications.length === 0 ? (
                <p>No applications yet</p>
            ) : (
                <ul>
                    {applications.map((app) => (
                        <li key={app.applicationId} style={{ marginTop: "10px" }}>
                            <strong>Opportunity ID:</strong> {app.opportunityId} |
                            <strong>Status:</strong> {app.status}
                        </li>
                    ))}
                </ul>
            )}

            {/* ---------------- OPPORTUNITY DETAILS ---------------- */}
            {selectedOpportunity && (
                <div
                    style={{
                        marginTop: "30px",
                        padding: "15px",
                        border: "1px solid #ccc",
                        borderRadius: "8px",
                        backgroundColor: "#f9f9f9"
                    }}
                >
                    <h3>Opportunity Details</h3>

                    <p><strong>Title:</strong> {selectedOpportunity.title}</p>
                    <p><strong>Description:</strong> {selectedOpportunity.description}</p>

                    {selectedOpportunity.requirements && (
                        <p><strong>Requirements:</strong> {selectedOpportunity.requirements}</p>
                    )}

                    <button
                        onClick={() => setSelectedOpportunity(null)}
                        style={{
                            marginTop: "10px",
                            padding: "6px 10px",
                            backgroundColor: "red",
                            color: "white",
                            border: "none",
                            borderRadius: "5px"
                        }}
                    >
                        Close
                    </button>
                </div>
            )}

        </div>
    );
}
