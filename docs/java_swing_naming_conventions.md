# 📘 Java Swing & Backend Naming Conventions

This document establishes the standard naming conventions for our **Movie Booking System** capstone project. Consistent naming ensures that code across all team members (Authentication, Customer, Organizer, and Admin modules) remains readable, predictable, and easy to merge.

## 1. General Java Naming Rules

| Element | Convention | Example | 
| ----- | ----- | ----- | 
| **Packages** | All lowercase, dot-separated | `com.moviebooking.model`, `com.moviebooking.ui.organizer` | 
| **Classes & Interfaces** | `PascalCase` (Nouns / Adjectives) | `Movie`, `Screening`, `Seat`, `Manageable`, `ManageMoviesPanel` | 
| **Methods** | `camelCase` (Verbs / Action phrases) | `getFormattedDateTime()`, `reserveSeat()`, `loadMovieCards()` | 
| **Variables & Fields** | `camelCase` (Descriptive nouns) | `movieId`, `durationMins`, `showDateTime`, `isReserved` | 
| **Constants** | `UPPER_SNAKE_CASE` | `PRIMARY_COLOR`, `DEFAULT_SEAT_PRICE`, `MAX_SEATS_PER_ROW` | 

## 2. Java Swing UI Component Prefixes

To easily distinguish UI elements from standard data variables in your event listeners and layout builders, use **descriptive `camelCase` names ending with the component type suffix** (or standard prefixing). **Suffix style** is recommended for modern Java readability:

### Interactive Controls

| Component Type | Suffix | Example | Usage Context | 
| ----- | ----- | ----- | ----- | 
| `JButton` | `Btn` | `addMovieBtn`, `saveScreeningBtn`, `deleteBtn` | Action triggers | 
| `JToggleButton` | `Toggle` | `seatA1Toggle`, `genreActionToggle` | Seat selection & filter chips | 
| `JTextField` | `Field` | `titleField`, `priceField`, `searchField` | Single-line text inputs | 
| `JPasswordField` | `PassField` | `passwordField`, `confirmPassField` | Password inputs | 
| `JTextArea` | `Area` | `descriptionArea`, `rejectReasonArea` | Multi-line text inputs | 
| `JComboBox` | `Combo` | `mtrcbRatingCombo`, `cinemaHallCombo`, `timeCombo` | Dropdown selections | 
| `JSpinner` / Date Picker | `Picker` | `birthdatePicker`, `showDatePicker` | Date & numeric pickers | 

### Display & Containers

| Component Type | Suffix | Example | Usage Context | 
| ----- | ----- | ----- | ----- | 
| `JLabel` | `Lbl` | `titleLbl`, `statusBadgeLbl`, `errorMsgLbl` | Text, icons, & pill badges | 
| `JPanel` / `RoundedPanel` | `Panel` | `headerPanel`, `movieCardPanel`, `seatGridPanel` | Layout containers & cards | 
| `JScrollPane` | `Scroll` | `moviesScroll`, `screeningsScroll` | Scrollable viewports | 
| `JDialog` | `Dialog` | `addEditMovieDialog`, `editUserDialog` | Modal popups | 
| `JFrame` | `Frame` | `mainFrame`, `loginFrame` | Top-level windows | 

## 3. Model & Domain Attributes (Per UML Diagram)

Keep variable names identical to the UML class diagram attributes when passing data between UI forms and backend services:

* **Movie:** `movieId`, `title`, `genre`, `durationMins`, `mtrcbRating`, `description`, `poster`
* **Screening:** `screeningId`, `movieId`, `showDateTime`, `cinemaHall`, `price`, `status`
* **Seat:** `seatId`, `screeningId`, `rowLetter`, `seatNumber`, `isReserved`
* **Booking & Payment:** `bookingId`, `customerId`, `bookingDate`, `paymentId`, `amount`, `method`, `ticketId`, `qrCode`

## 4. Event Handler & Helper Method Naming

Structure UI helper and action methods consistently across all screens:

* **UI Initialization:** `initComponents()`, `setupLayout()`, `bindEvents()`
* **Dynamic Card Builders:** `createMovieCard(Movie movie)`, `createScreeningCard(Screening screening)`, `createSeatButton(Seat seat)`
* **Data Refreshing:** `refreshMovieGrid()`, `loadScreeningsList()`
* **Event Handlers:** `handleSaveMovie()`, `handleDeleteScreening(String screeningId)`, `onSeatClicked(Seat seat)`