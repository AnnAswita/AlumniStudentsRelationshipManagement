export default function AboutPage() {
    return (
        <div className="black-text-conatiner" style={{ padding: "40px", maxWidth: "900px", margin: "0 auto" }}>
            <h1>About the System</h1>

            <p>
                The Alumni–Student Relationship Management System is designed to connect
                students with alumni for mentorship, guidance, meetings, and communication.
            </p>

            <h3>Key Features</h3>
            <ul>
                <li>Student and alumni registration and login</li>
                <li>Mentorship request, acceptance, cancellation, and completion</li>
                <li>Meeting scheduling between students and alumni</li>
                <li>Real-time messaging after mentorship acceptance</li>
                <li>Listing and Adding - Events And Opportunities</li>
            </ul>

            <h3>Architecture</h3>
            <p>
                The platform follows a microservices architecture. The main services include
                User Service, Mentorship Service, Messaging Service, API Aggregator, and
                Eureka Service Registry.
            </p>

            <h3>Messaging</h3>
            <p>
                Messaging is enabled only when a mentorship is accepted or active. Messages
                are grouped into conversations and delivered using WebSocket communication.
            </p>

            <h3>Project Purpose</h3>
            <p>
                The goal of the system is to support long-term engagement between students
                and alumni by providing structured mentorship and communication tools.
            </p>
        </div>
    );
}