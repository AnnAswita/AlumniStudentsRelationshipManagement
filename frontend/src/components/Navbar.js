import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
    const navigate = useNavigate();
    const token = localStorage.getItem("token");

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };
    const goHome = () => {
        const role = localStorage.getItem("role");
        navigate(role === "STUDENT" ? "/student" : "/alumni");
    };

    return (
        <div style={{
            backgroundColor: "#007bff",
            padding: "12px",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            color: "white",
            marginBottom: "20px"
        }}>
            <div style={{ fontWeight: "bold", fontSize: "18px" }}>
                Alumni–Student Portal
            </div>

            <div>

                {!token && (
                    <>
                        <Link to="/" style={{ color: "white", marginRight: "15px" }}>Register</Link>
                        <Link to="/login" style={{ color: "white" }}>Login</Link>
                    </>
                )}

                {token && (
                                    <>
                                        <button
                                            onClick={goHome}
                                            style={{
                                                backgroundColor: "transparent",
                                                color: "white",
                                                border: "none",
                                                marginRight: "15px",
                                                cursor: "pointer",
                                                fontSize: "16px"
                                            }}
                                        >
                                            Home
                                        </button>
                                        {/* Events Button */}
                                        <Link
                                            to="/events"
                                            style={{
                                                color: "white",
                                                marginRight: "15px",
                                                textDecoration: "none"
                                            }}
                                        >
                                            Events
                                        </Link>
                                        <Link to="/about"
                                              style={{
                                                  color: "white",
                                                  marginRight: "15px",
                                                  textDecoration: "none"
                                              }}
                                        >About</Link>

                                        <button
                                            onClick={handleLogout}
                                            style={{
                                                backgroundColor: "white",
                                                color: "#007bff",
                                                border: "none",
                                                padding: "8px 12px",
                                                borderRadius: "5px",
                                                cursor: "pointer"
                                            }}
                                        >
                                            Logout
                                        </button>
                                    </>
                                )}
            </div>
        </div>
    );
}
