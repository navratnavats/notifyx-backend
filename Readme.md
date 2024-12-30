# NotifyX - Real-Time Notification System

NotifyX is a robust, real-time notification system designed to handle event-based notifications via 
multiple channels such as WebSocket and Web Push. It is built using Java Spring Boot and leverages 
modern technologies like Kafka, MongoDB, and Docker for efficient event processing and delivery.

(Connect with me on [LinkedIn](https://www.linkedin.com/in/navratna-vats/)).

## Features

- **Event-Driven Architecture**: Uses Kafka for asynchronous event streaming.
- **Multi-Channel Notifications**: Supports WebSocket and Web Push channels.
- **Notification Delivery Flexibility**: Notifications can be delivered via API or Kafka, depending on the use case.
- **User Preferences Management**: Customizable user preferences for notification channels and muting specific notifications.
- **WebSocket Integration**: Real-time updates using WebSocket.
- **Dockerized Deployment**: Fully containerized for easy setup and scaling.
- **Extensible Design**: Modular components for notification processing, delivery, and storage.
- **Upcoming Features**:
    - Authentication via JWT.
    - Enhanced caching mechanisms.

## Technology Stack

- **Backend**: Java Spring Boot
- **Messaging**: Apache Kafka
- **Database**: MongoDB
- **Real-Time Communication**: WebSocket
- **Containerization**: Docker, Docker Compose


## Setup Instructions

### Prerequisites

- Docker & Docker Compose
- Java 21 or later
- Maven

### Build and Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/navratnavats/notifyx-backend.git
   cd notifyx-backend
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

3. **Run with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

4. **Access the Application:**
    - **Backend API**: `http://localhost:8080`
    - **WebSocket Endpoint**: `ws://localhost:8080/ws/notification?userId=<USER_ID>`

### Configuration

- All configurations are managed via `application.properties` or environment variables in the Docker Compose file.
- Modify the Kafka and MongoDB settings as per your environment.

### Endpoints

#### Event Delivery
- **POST** `/api/v1/notification-delivery/send-kafka`
    - Sends an event to Kafka for processing.

- **POST** `/api/v1/notification-delivery/send-notification`
    - Directly processes and delivers a notification.

#### User Preferences
- **GET** `/api/v1/preference/get-user-preference/{userId}`
    - Retrieves user preferences.

- **PUT** `/api/v1/preference/change/{userId}`
    - Updates user preferences.

### Environment Variables

| Variable                | Description                           |
|-------------------------|---------------------------------------|
| `SPRING_PROFILES_ACTIVE` | Spring profile to activate            |
| `KAFKA_BOOTSTRAP_SERVERS`| Kafka bootstrap servers               |
| `MONGO_URI`             | MongoDB connection URI                |
| `SPRING_WEBSOCKET_ENDPOINT`| WebSocket endpoint path             |

## Architecture Overview

### Components

1. **Event Processing**
    - `KafkaConsumer` listens to events from Kafka and processes them using `EventProcessorService`.
2. **Notification Delivery**
    - `NotificationDeliveryService` handles delivery via WebSocket or other channels.
3. **User Preferences**
    - `PreferenceService` manages user preferences, including default preferences.
4. **WebSocket Integration**
    - `NotificationWebSocketHandler` manages WebSocket sessions and real-time notifications.

### Data Flow API
1. An event is sent via API .
2. `EventProcessorSerive` processes the event.
3. A notification is built and delivered using `NotificationDeliveryService`.
4. The notification record is saved in MongoDB.


### Data Flow Kafa
1. An event is sent to Kafka.
2. `KafkaConsumer` processes the event.
3. A notification is built and delivered using `NotificationDeliveryService`.
4. The notification record is saved in MongoDB.


## Contributing

Feel free to fork the repository and submit pull requests. Contributions are welcome!

## Contact
For any inquiries or support, please reach out via email at [navratna.vats.work@gmail.com](mailto:navratna.vats.work@gmail.com) or connect on [LinkedIn](https://www.linkedin.com/in/navratna-vats/).