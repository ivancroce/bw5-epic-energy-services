import { useEffect, useState } from "react";
import { Row, Col, Card, Spinner, Alert } from "react-bootstrap";
import axios from "axios";

const DashboardPage = () => {
  const [customers, setCustomers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          throw new Error("No access token found. Please login.");
        }

        const response = await axios.get("http://localhost:3001/customers", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        setCustomers(response.data.content);
      } catch (err) {
        console.error("Failed to fetch customers:", err);
        setError("Failed to load customer data. You may be unauthorized.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchCustomers();
  }, []);

  if (isLoading) {
    return <Spinner animation="border" variant="primary" />;
  }

  if (error) {
    return <Alert variant="danger">{error}</Alert>;
  }

  return (
    <div>
      <div className="my-5">
        <h1 className="mb-4 text-white">Customer Dashboard</h1>
      </div>
      <Row>
        {customers.map((customer) => (
          <Col key={customer.id} md={4} className="mb-3">
            <Card>
              <Card.Body>
                <Card.Title>{customer.companyName}</Card.Title>
                <Card.Text>
                  <strong>Email:</strong> {customer.email}
                  <br />
                  <strong>VAT:</strong> {customer.vatNumb}
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default DashboardPage;
