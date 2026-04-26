import { useState } from "react";
import { registerUser } from "../userApi";
import "../styles/register.css";

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
        <div className="register-container">

            <h1 className="register-title">Create Account</h1>
            <p style={{
                textAlign: "center",
                color: "#666",
                fontSize: "14px",
                marginBottom: "25px"
            }}>
                <h2 className="register-heading">Join the alumni–student network</h2>
            </p>


            {message && (
                <p className="register-message" style={{ color: message.includes("successful") ? "green" : "red" }}>
                    {message}
                </p>
            )}

            <form onSubmit={handleRegister}>
                <input
                    className="register-input"
                    type="text"
                    placeholder="Full Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />

                <input
                    className="register-input"
                    type="email"
                    placeholder="Email Address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <input
                    className="register-input"
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <label style={{ marginLeft: "0%", fontSize: "13px", color: "#555" }}>
                    Select Role
                </label>
                <select
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    style={{
                        width: "105%",
                        padding: "10px",
                        margin: "0 auto 12px auto",
                        display: "block",
                        borderRadius: "6px",
                        border: "1px solid #ccc"
                    }}
                >
                    <option value="STUDENT">Student</option>
                    <option value="ALUMNI">Alumni</option>
                </select>


                <button className="register-button" type="submit"
                        style={{
                            width: "105%",
                            padding: "10px",
                            backgroundColor: "#007bff",
                            color: "white",
                            border: "none",
                            borderRadius: "5px",
                            cursor: "pointer"
                        }}
                >Register</button>
            </form>

            <p className="register-link">
                Already have an account? <a href="/login">Login</a>
            </p>
        </div>
    );
}
