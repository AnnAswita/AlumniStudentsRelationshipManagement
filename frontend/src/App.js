import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";


import LoginPage from "./api/pages/LoginPage";
import RegisterPage from "./api/pages/RegisterPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<RegisterPage />} />
                <Route path="/login" element={<LoginPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
