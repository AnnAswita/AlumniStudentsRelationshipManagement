import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { scheduleMeeting } from "../meetingApi";

export default function CreateMeetingPage() {
    const { mentorshipId } = useParams();
    const navigate = useNavigate();

    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");
    const role = localStorage.getItem("role");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await scheduleMeeting({
                mentorshipId,
                description,
                date
            });

            alert("Meeting scheduled successfully");
            navigate(role === "ALUMNI" ? "/alumni" : "/student");
        } catch (err) {
            alert(err.message); // shows backend message
        }
    };


    return (
        <div className="black-text-container" style={{ padding: "20px" }}>
            <h2>Schedule a Meeting</h2>

            <form onSubmit={handleSubmit} style={{ maxWidth: "400px" }}>
                <label>Description</label>
                <input
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                    style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
                />

                <label>Date</label>
                <input
                    type="datetime-local"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    required
                    style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
                />

                <button
                    type="submit"
                    style={{
                        padding: "10px",
                        backgroundColor: "#28a745",
                        color: "white",
                        border: "none",
                        borderRadius: "5px",
                        cursor: "pointer"
                    }}
                >
                    Create Meeting
                </button>
            </form>
        </div>
    );
}
