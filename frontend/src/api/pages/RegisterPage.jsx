import { useState } from "react";
import { registerUser } from "../userApi";

export default function RegisterPage() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("STUDENT");
    const [message, setMessage] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();
        setMessage("");

        try {
            await registerUser({ name, email, password, role });
            setMessage("Registration successful");
        } catch (err) {
            setMessage("Registration failed");
        }
    };

    return (
        <div style={{
            width: "350px",
            margin: "auto",
            marginTop: "60px",
            padding: "20px",
            border: "1px solid #ccc",
            borderRadius: "10px",
            boxShadow: "0 0 10px rgba(0,0,0,0.1)"
        }}>

            {/* ⭐ Project Title */}
            <h1 style={{
                textAlign: "center",
                fontWeight: "bold",
                marginBottom: "20px",
                fontSize: "22px"
            }}>
                Alumni–Student Project Management
            </h1>

            <h2 style={{ textAlign: "center" }}>Register</h2>

            {message && (
                <p style={{
                    textAlign: "center",
                    color: message.includes("successful") ? "green" : "red"
                }}>
                    {message}
                </p>
            )}

            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Full Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
                />

                <input
                    type="email"
                    placeholder="Email Address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
                />

                <select
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    style={{ width: "100%", padding: "10px", marginBottom: "15px" }}
                >
                    <option value="STUDENT">STUDENT</option>
                    <option value="ALUMNI">ALUMNI</option>
                </select>

                <button
                    type="submit"
                    style={{
                        width: "100%",
                        padding: "10px",
                        backgroundColor: "#007bff",
                        color: "white",
                        border: "none",
                        borderRadius: "5px",
                        cursor: "pointer"
                    }}
                >
                    Register
                </button>
            </form>

            <p style={{ textAlign: "center", marginTop: "15px" }}>
                Already have an account? <a href="/login">Login</a>
            </p>
        </div>
    );
}
