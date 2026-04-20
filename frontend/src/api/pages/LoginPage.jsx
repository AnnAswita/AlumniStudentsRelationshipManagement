import { useState } from "react";
import { loginUser } from "../userApi";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const result = await loginUser({ email, password });
            localStorage.setItem("token", result.token);
            alert("Login successful");
        } catch (err) {
            setError("Invalid email or password");
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

            <h2 style={{ textAlign: "center" }}>Login</h2>

            {error && (
                <p style={{ color: "red", textAlign: "center" }}>
                    {error}
                </p>
            )}

            <form onSubmit={handleLogin}>
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
                    style={{ width: "100%", padding: "10px", marginBottom: "15px" }}
                />

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
                    Login
                </button>
            </form>

            <p style={{ textAlign: "center", marginTop: "15px" }}>
                Don't have an account? <a href="/">Register</a>
            </p>
        </div>
    );
}
