import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";


import LoginPage from "./api/pages/LoginPage";
import RegisterPage from "./api/pages/RegisterPage";
import StudentDashboard from "./api/pages/StudentDashboard";
import AlumniDashboard from "./api/pages/AlumniDashboard";
import Navbar from "./components/Navbar";
import AlumniListPage from "./api/pages/AlumniListPage";

function App() {
    return (
        <BrowserRouter>
            <Navbar />
            <Routes>
                <Route path="/" element={<RegisterPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/student" element={<StudentDashboard />} />
                <Route path="/alumni" element={<AlumniDashboard />} />
                <Route path="/student/request-mentorship" element={<AlumniListPage />} />

            </Routes>
        </BrowserRouter>
    );
}

export default App;
