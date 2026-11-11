## 🗺️ UML Class Diagram

```mermaid
classDiagram
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
    class Customer {
        +void showRoleMenu() void
        +void selectSeats() void
        +void purchaseTicket() void
        +void cancelRefund() void
    }
    class EventOrganizer {
        +void showRoleMenu() void
        +void createMovie() void
        +void updateMovie() void
        +void deleteMovie() void
        +void createScreening() void
        +void updateScreening() void
        +void deleteScreening() void
    }
    class Admin {
        +void showRoleMenu() void
        +boolean approveScreening() boolean
        +boolean rejectScreening() boolean
        +boolean processRefund() boolean
        +void manageUsers() void
        +void resetPassword() void
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
    class Screening {
        -String screeningId
        -String movieId
        -LocalDateTime showDateTime
        -String cinemaHall
        -double price
        -String status
        +String getFormattedDateTime() String
    }
    class Seat {
        -String seatId
        -String screeningId
        -char rowLetter
        -int seatNumber
        -boolean isReserved
        +boolean reserveSeat() boolean
        +void releaseSeat() void
    }
    class Booking {
        -String bookingId
        -String customerId
        -String screeningId
        -Date bookingDate
        -String status
        +double calculateTotal() double
        +void confirmBooking() void
        +void cancelBooking() void
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
    class Ticket {
        -String ticketId
        -String bookingId
        -String qrCode
        -String cinemaHall
        -Date issuedAt
        +String generateQRCode() String
        +boolean validateTicket() boolean
        +void saveTicketToFile() void
    }
    User <|-- Customer
    User <|-- EventOrganizer
    User <|-- Admin
    Movie "1" -- "1..*" Screening : has
    Screening "1" -- "1..*" Seat : has
    Customer "1" -- "0..*" Booking : makes
    Booking "1" -- "1" Payment : paid by
    Booking "1" -- "1" Ticket : issues
    Screening "1" -- "0..*" Booking : booked for