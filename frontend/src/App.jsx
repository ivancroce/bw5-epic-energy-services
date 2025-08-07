import "./App.css";
import { Routes, Route } from "react-router-dom";
import { Container } from "react-bootstrap";
import MyFooter from "./components/MyFooter";
import MyNavBar from "./components/MyNavbar";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
  return (
    <>
      <MyNavBar />
      <main className="flex-grow-1">
        <Container className="my-4">
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </Container>
      </main>
      <MyFooter />
    </>
  );
}

export default App;
