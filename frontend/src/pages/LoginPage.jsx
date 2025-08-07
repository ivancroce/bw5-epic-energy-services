import { Form, Button, Container, Row, Col, Card } from "react-bootstrap";

const LoginPage = () => {
  return (
    <Container>
      <Row className="justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
        <Col md={6}>
          <Card className="p-4 shadow">
            <h3 className="text-center mb-4">Login</h3>
            <Form>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="Enter email" />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" />
              </Form.Group>

              <Button variant="primary" type="submit" className="w-100">
                Login
              </Button>
            </Form>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginPage;
