import { useState } from "react";
import { loginUser } from "../userApi";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const result = await loginUser({ email, password });
            localStorage.setItem("token", result.token);
            localStorage.setItem("userId",result.id);
            localStorage.setItem("role",result.role);

            //Get role form the login response
            const role = localStorage.getItem("role");

            // Redirect based on role
            if (role === "STUDENT") {
                navigate("/student");
            } else if (role === "ALUMNI") {
                navigate("/alumni");
            } else {
                navigate("/");
            }

        } catch (err) {
            setError("Invalid email or password");
        }
    };

    return (
        <div style={{
            width: "380px",
            margin: "auto",
            marginTop: "80px",
            padding: "20px",
            background: "#ffffff",
            borderRadius: "12px",
            boxShadow: "0 8px 25px rgba(0,0,0,0.15)"
        }}>

            {/* Project Title */}
            <h1 style={{
                textAlign: "center",
                fontWeight: "bold",
                marginBottom: "20px",
                fontSize: "22px"
            }}>
                Welcome Back
            </h1>

            {/* <h2 style={{ textAlign: "center" }}>Login</h2>*/}

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
                    style={{ width: "90%", padding: "10px", marginBottom: "10px" }}
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    style={{ width: "90%", padding: "10px", marginBottom: "15px" }}
                />

                <button
                    type="submit"
                    style={{
                        width: "95%",
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
