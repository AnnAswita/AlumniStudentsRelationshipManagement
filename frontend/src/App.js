import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";


import LoginPage from "./api/pages/LoginPage";
import RegisterPage from "./api/pages/RegisterPage";
import StudentDashboard from "./api/pages/StudentDashboard";
import AlumniDashboard from "./api/pages/AlumniDashboard";
import Navbar from "./components/Navbar";
import AlumniListPage from "./api/pages/AlumniListPage";
import ChatPage from "./api/pages/ChatPage";
import EventsPage from "./api/pages/EventsPage";
import CreateMeetingPage from "./api/pages/CreateMeetingPage";
import ViewMeetingsPage from "./api/pages/ViewMeetingsPage";
import AboutPage from "./api/pages/AboutPage";


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
                <Route path="/meeting/create/:mentorshipId" element={<CreateMeetingPage />} />
                <Route path="/meeting/view/:mentorshipId" element={<ViewMeetingsPage />} />
                <Route path="/events" element={<EventsPage />} />
                <Route path="/chat/:mentorshipId" element={<ChatPage />} />
                <Route path="/about" element={<AboutPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
