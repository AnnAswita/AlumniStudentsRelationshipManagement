import { useEffect, useState } from "react";
import { getAllAlumni } from "../alumniApi";
import { requestMentorship } from "../mentorshipApi";
import { useNavigate } from "react-router-dom";

export default function AlumniListPage() {
    const [alumni, setAlumni] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        loadAlumni();
    }, []);

    const loadAlumni = async () => {
        const data = await getAllAlumni();
        console.log("ALUMNI RESPONSE:", data);
        setAlumni(data);
    };

    const handleRequest = async (alumniId) => {
        const studentId = localStorage.getItem("studentId");
        if (!studentId) {
            alert("Student ID missing. Please enter it in the dashboard first.");
            return;
        }

        await requestMentorship(studentId, alumniId);
        alert("Mentorship request sent!");
        navigate("/student");
    };

    return (
        <div className="black-text-conatiner" style={{ padding: "20px" }}>
            <h2>Select an Alumni</h2>

            <ul>
                {alumni.map(a => (
                    <li key={a.id} style={{
                        padding: "10px",
                        border: "1px solid #ccc",
                        marginBottom: "10px",
                        borderRadius: "8px"
                    }}>
                        <strong>{a.name}</strong> ({a.email})
                        <br />
                        <button
                            onClick={() => handleRequest(a.id)}
                            style={{
                                marginTop: "8px",
                                padding: "8px 12px",
                                backgroundColor: "#007bff",
                                color: "white",
                                border: "none",
                                borderRadius: "5px",
                                cursor: "pointer"
                            }}
                        >
                            Request Mentorship
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
