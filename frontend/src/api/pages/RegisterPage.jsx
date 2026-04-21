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

            <h1 className="register-title">Alumni–Student Project Management</h1>
            <h2 className="register-heading">Register</h2>

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

                <select
                    className="register-input register-select"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                >
                    <option value="STUDENT">STUDENT</option>
                    <option value="ALUMNI">ALUMNI</option>
                </select>


                <button className="register-button" type="submit">Register</button>
            </form>

            <p className="register-link">
                Already have an account? <a href="/login">Login</a>
            </p>
        </div>
    );
}
