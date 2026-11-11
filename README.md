# 🎬 Movie Ticket Booking System — OOP Capstone Project

Java Swing • GUI-first • Team of 4

## Team
| Member | Role | Screens | Branch |
|---|---|---|---|
| Shima | Auth + Payment + Ticket | 4 | `person-shima-auth-payment` |
| Vance | Customer | 7 | `person-vance-customer` |
| Lance | Organizer | 5 | `person-lance-organizer` |
| Jup | Admin | 4 | `person-jup-admin` |
| **Total** | | **20** | |

## Rules
- `main` = finished / working only. **Nobody codes directly on main.**
- Work on your own branch → Push → Pull Request → Teammate reviews → Merge

## Folder Structure
```
src/
  model/      User, Customer, EventOrganizer, Admin, Movie, Screening, Seat, Booking, Payment, Ticket
  service/    AuthService, MovieService, ScreeningService, BookingService, PaymentService, UserService, SeatService
  ui/
    auth/       Login, Register
    customer/   Dashboard, Browse, Detail, Seat, Summary, MyBookings, MyProfile
    organizer/  Dashboard, ManageMovies, ManageScreenings, CreateScreening, MyScreenings
    admin/      Dashboard, ApproveScreening, Refund, ManageUsers
  util/       Theme.java
data/         .txt / .csv files (gitignored if needed)
docs/         UML, UX Board, Task Division, Senior Dev Docs
```

## How to Run
1. Open this folder in **IntelliJ IDEA**
2. Make sure JDK 17+ is selected
3. Run `src/Main.java`
4. Use IntelliJ GUI Designer `.form` files for each screen — never `new JButton()` manually

## Daily Workflow
```bash
git checkout main
git pull
git checkout person-<yourname>-<role>
git merge main
# ... code ...
git add .
git commit -m "Add Login UI - Shima"
git push
# Then: GitHub → Pull Request → Review → Merge
```

## UML

```mermaid
---
title: Movie Ticket Booking System — UML v6 FINAL (NO VIP) — CLEAN
---
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
        +boolean login()
        +Movie[] browseMovies()
        +String getFullName()
        +int getAge()
        +void showRoleMenu()*
    }
    class Customer {
        +void showRoleMenu()
        +void selectSeats()
        +void purchaseTicket()
        +void cancelRefund()
    }
    class EventOrganizer {
        +void showRoleMenu()
        +void createMovie()
        +void updateMovie()
        +void deleteMovie()
        +void createScreening()
        +void updateScreening()
        +void deleteScreening()
    }
    class Admin {
        +void showRoleMenu()
        +boolean approveScreening()
        +boolean rejectScreening()
        +boolean processRefund()
        +void manageUsers()
        +void resetPassword(User user)
    }
    User <|-- Customer
    User <|-- EventOrganizer
    User <|-- Admin
    class Movie {
        -String movieId
        -String title
        -String genre
        -int durationMins
        -String mtrcbRating
        -String description
        -String poster
        +Movie[] getMovies()
    }
    class Screening {
        -String screeningId
        -String movieId
        -LocalDateTime showDateTime
        -String cinemaHall
        -double price
        -String status
        +Seat[] getAvailableSeats()
        +String getFormattedDateTime()
        +Screening[] getScreeningsForMovie()
    }
    Movie "1" -- "1..*" Screening : has
    class Seat {
        -String seatId
        -String screeningId
        -char rowLetter
        -int seatNumber
        -boolean isReserved
        +boolean reserveSeat()
        +void releaseSeat()
    }
    note for Seat "NO seatType\nNO VIP\nPrice = Screening.price"
    Screening "1" -- "1..*" Seat : contains
    class Booking {
        -String bookingId
        -String customerId
        -String screeningId
        -Date bookingDate
        -String status
        +double calculateTotal()
        +void confirmBooking()
        +void cancelBooking()
    }
    note for Booking "calculateTotal() = seats.size() x screening.price\nNO VIP check"
    Customer "1" -- "0..*" Booking : creates
    Seat "1..*" -- Booking : reserves
    class Payment {
        -String paymentId
        -String bookingId
        -double amount
        -String method
        -String status
        +boolean processPay()
        +boolean refundPay()
    }
    class Ticket {
        -String ticketId
        -String bookingId
        -String qrCode
        -String cinemaHall
        -Date issuedAt
        +String generateQRCode()
        +boolean validateTicket()
        +void saveTicketToFile()
    }
    Booking "1" -- "1" Payment : paid by
    Booking "1" -- "1..*" Ticket : issues
    class Transactable {
        <<interface>>
        +boolean processPayment()
        +boolean refundPayment()
    }
    class Verifiable {
        <<interface>>
        +String generateQRCode()
        +boolean validateTicket()
    }
    Payment ..|> Transactable
    Ticket ..|> Verifiable
```

## Key Decisions
- **GUI-first:** Build all screens with dummy data first, wire logic later
- **Bound class = Waiter:** UI logic only, calls Service
- **Service = Kitchen:** Business logic, file I/O, calculations, no Swing
- **Theme.java:** One place for colors/fonts. Everyone imports it. Primary `#4F46E5`, Background `#F6F7FB`
- **Showtime Picker:** In Movie Detail, pick ONE showtime card. Only Approved + Future shown. Sold Out = gray disabled. Booking stores `screeningId`, not just `movieId`.
- **Cancel/Refund:** My Bookings → Cancel → `Pending Refund` → Admin Refund → Approve → `Refunded`
- **Admin:** Can edit all user details, Reset Password (cannot see hash), cannot deactivate self. Promotes Customer → Organizer via Manage Users.
