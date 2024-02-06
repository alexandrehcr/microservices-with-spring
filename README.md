# Microservices with Spring

This project is organized in a microservices architecture with 3 major components: Employee Service, Department Service, and Organization Service. Each component is registered in a service registry and discover and benefit from the load balancing capabilities it can offer. All incoming client request are made to the API gateway, that in turn forwards the request to one of the available services.

The Employee Service communicates with the Department Service and Organization Service to get information about the department and organization a department works for. Case one or both is unable to respond, the circuit breaks and there's a specific fallback mechanism for each.

## Features and Technologies
- REST APIs communication (Spring Boot)
- API Gateway (Spring Cloud Gateway)
- Service Registry (Spring Cloud Eureka Discovery)
- Load Balancing (Spring Cloud Load balancer)
- Circuit Breaker (Resilience4J)
- Observation (Spring Actuator)
- Distributed Tracing (Zipkin)
- Documentation (Swagger)
- MySQL
- H2
- ModelMapper
- Lombok
