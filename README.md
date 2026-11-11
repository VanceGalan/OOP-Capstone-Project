# 🎬 Movie Ticket Booking System

### OOP Capstone Project

## 🗺️ UML Class Diagram
See full diagram → [docs/UML.md](docs/UML.md)

- - -

## 👥 Meet the Team

| Member | 🎯 Screens | 🌿 Branch |
| :--- | :--- | :--- |
| **Shima** | Auth + Payment + Ticket | `person-shima-auth-payment` |
| **Vance** | Customer | `person-vance-customer` |
| **Lance** | Organizer | `person-lance-organizer` |
| **Jup** | Admin | `person-jup-admin` |

> **Total: 20 screens** — built by 4 people, one branch each.

- - -

> 💡 **Read this once. These are the rules we all follow.**

| ✅ Rule | What it means |
| :--- | :--- |
| **\`main\` = finished only** | Nobody codes directly on `main` after setup |
| **One branch per person** | Work → Push → Pull Request → Review → Merge |
| **Designer only** | Every screen = `.form` + bound `.java`. Never `new JButton()` in code |
| **Every screen has \`getMainPanel()\`** | This is how Main swaps screens |
| **Theme only** | Never hard-code colors. Always `Theme.PRIMARY`, `Theme.stylePrimaryButton(btn)` |
| **Bound class = UI only** | Service = business logic, no Swing |

- - -

## 📁 Folder Structure

```
OOP-Capstone-Project/
├── 📂 lib/
│   └── flatlaf-3.8.jar
├── 📂 src/
│   ├── Main.java
│   ├── 📂 model/        User, Customer, EventOrganizer, Admin, Movie, Screening, Seat, Booking, Payment, Ticket
│   ├── 📂 service/      AuthService, MovieService, ScreeningService, BookingService, PaymentService, UserService, SeatService
│   ├── 📂 ui/
│   │   ├── 📂 auth/          Login, Register — Shima
│   │   ├── 📂 customer/      Dashboard, Browse, Detail, Seat, Summary, MyBookings, MyProfile — Vance
│   │   ├── 📂 organizer/     Dashboard, ManageMovies, ManageScreenings, CreateScreening, MyScreenings — Lance
│   │   └── 📂 admin/         Dashboard, ApproveScreening, Refund, ManageUsers — Jup
│   └── 📂 util/
│       └── Theme.java
├── README.md
├── SETUP.md
└── .gitignore
```

- - -

## ▶️ How to Run (Windows)

| Step | What to do |
| :--- | :--- |
| **1** | Open this folder in **IntelliJ IDEA** on Windows |
| **2** | JDK **17** or **21** — File → Project Structure → Project → SDK |
| **3** | Add FlatLaf — File → Project Structure → Libraries → + → Java → `lib/flatlaf-3.8.jar` |
| **4** | Run `src/Main.java` — Right-click → Run 'Main.main()' |
| **5** | ✨ Login screen opens first |

- - -

## 🔄 How to Navigate Between Screens

> Every screen has `getMainPanel()`. Swap the panel in the **SAME** JFrame. Never open a new JFrame.

```java
JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
frame.setContentPane(new BrowseMovies().getMainPanel());
frame.revalidate();
frame.repaint();
```

- - -

## 🚀 New Here?

👉 **Read** [**`SETUP.md`**](SETUP.md) — Full step-by-step for Windows: Clone → Checkout your branch → Add FlatLaf → Start coding.