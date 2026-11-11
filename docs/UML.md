## 🗺️ UML Class Diagram

```mermaid
classDiagram
    class Authenticatable {
        <<interface>>
        +boolean login() boolean
        +void logout() void
    }
    class Payable {
        <<interface>>
        +boolean processPay() boolean
        +boolean refundPay() boolean
    }
    class Manageable {
        <<interface>>
        +void create() void
        +void update() void
        +void delete() void
    }
    class User {
        <<abstract>>
        -String userId
        -String firstName
        -String lastName
        -String email
        -String passwordHash
        -LocalDate birthdate
        -String status
        +String getFullName() String
        +int getAge() int
        +boolean login() boolean
        +void showRoleMenu() void
    }
    class Payment {
        -String paymentId
        -String bookingId
        -double amount
        -String method
        -String status
        +boolean processPay() boolean
        +boolean refundPay() boolean
    }
    class Movie {
        -String movieId
        -String title
        -String genre
        -int durationMins
        -String mtrcbRating
        -String description
        -String poster
        +String getTitle() String
        +void setTitle() void
    }

    Authenticatable <|.. User : implements
    Payable <|.. Payment : implements
    Manageable <|.. Movie : implements
    User <|-- Customer
    User <|-- EventOrganizer
    User <|-- Admin
