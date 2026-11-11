# SETUP — How to Clone, Checkout Your Branch, and Start Coding
Repo: https://github.com/VanceGalan/OOP-Capstone-Project.git
Owner: Vance | Collaborators: Shima, Vance, Lance, Jup (Write permission)

## STEP 0 — BEFORE YOU CLONE (2 mins)
### 0A. Accept the Invite
1. github.com -> bell icon -> VanceGalan invited you -> Accept
2. If no invite, message Vance: "Pa-add ulit, need Write, not Read."
3. Vance: Settings -> Collaborators -> your name -> Write
If you skip this, you can clone but CANNOT push (403).

### 0B. Log in to GitHub INSIDE IntelliJ
IntelliJ -> Settings (Ctrl+Alt+S) -> Version Control -> GitHub -> + -> Log In via GitHub -> Authorize

## STEP 1 — CLONE (do this ONCE)
1. IntelliJ Welcome -> Get from VCS
2. Paste: https://github.com/VanceGalan/OOP-Capstone-Project.git
3. Choose folder: IdeaProjects/OOP-Capstone-Project -> Clone -> Trust Project
4. You should see src/Main.java, src/util/Theme.java, README.md

## STEP 2 — CHECKOUT TO YOUR ASSIGNED BRANCH (do NOT create a new one)
Shima -> person-shima-auth-payment
Vance -> person-vance-customer
Lance -> person-lance-organizer
Jup -> person-jup-admin

In IntelliJ:
1. Bottom right -> click branch name (says main)
2. Click Fetch
3. Find YOUR branch -> Checkout
4. Bottom right must now say YOUR branch. If it says main, STOP — switch first.

If branch missing: Git -> Fetch, try again, tell Jup.

## STEP 3 — ADD FLATLAF JAR (once)
1. Download flatlaf-3.5.jar
2. File -> Project Structure -> Libraries -> + -> Java -> select JAR -> OK
3. Red errors on FlatLaf should disappear.

## STEP 4 — RUN IT FIRST
Open src/Main.java -> Run Main.main() -> Login window should open.
If it runs, setup is correct. If not, screenshot error to GC.

## STEP 5 — START CODING
Where you code:
Shima -> src/ui/auth/
Vance -> src/ui/customer/ + Theme.java + Main.java
Lance -> src/ui/organizer/
Jup -> src/ui/admin/
Rule: Don't edit someone else's folder without asking.

How to create a new screen:
1. Right-click your ui folder -> New -> Swing UI Designer -> GUI Form -> Name e.g. BrowseMovies
2. IntelliJ makes BrowseMovies.form + BrowseMovies.java
3. In .java, add: public JPanel getMainPanel() { return mainPanel; }
Never do new JButton() in code. Always drag in Designer.

How to use Theme:
Theme.stylePrimaryButton(loginBtn);
Never: btn.setBackground(new Color(79,70,229));

How to navigate:
JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
frame.setContentPane(new BrowseMovies().getMainPanel());
frame.revalidate(); frame.repaint();

## STEP 6 — DAILY WORKFLOW
Morning:
1. Bottom right -> switch to main -> Git -> Pull
2. Bottom right -> switch back to YOUR branch -> Git -> Merge Branch -> main -> Merge
Code:
- Code only in YOUR folder, Save often, Run Main.java to test.
Evening:
1. Ctrl+K -> tick ONLY your files -> message: "Add Browse Movies UI - Vance" -> Commit and Push
2. Check bottom right still says YOUR branch.
Pull Request (when screen is DONE):
GitHub -> Pull Requests -> New PR -> Base: main <- Compare: YOUR branch -> Create -> Tag teammate to review -> Merge.

## TROUBLESHOOTING
403 Push failed -> Didn't Accept invite, or Read-only, or wrong GitHub account in IntelliJ.
Coded on main by mistake -> DON'T push. Tell team. Create new branch from current, move commits, reset main.
Merge conflict -> Open file with <<<<<<<, keep correct version, delete markers, Commit.
FlatLaf red error -> Add JAR again.
getMainPanel null -> .form not bound to .java, check mainPanel field name matches.

## COPY-PASTE FOR GC
Branches ready. Don't create new ones.
Shima -> person-shima-auth-payment
Vance -> person-vance-customer
Lance -> person-lance-organizer
Jup -> person-jup-admin
In IntelliJ: Get from VCS -> Clone https://github.com/VanceGalan/OOP-Capstone-Project.git -> Bottom right -> Checkout YOUR branch -> Add FlatLaf JAR -> Run Main.java
Full steps: Read SETUP.md in repo. Never code on main.
