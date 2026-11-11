# 🚀 SETUP GUIDE

### How to Clone, Checkout Your Branch, and Start Coding

**Repo:** https://github.com/VanceGalan/OOP-Capstone-Project.git

> ⏱️ **Read this once. 10 minutes. After that, you will never get lost.**

---

## 📋 Quick Map

| Step | Time | What |
| :--- | :--- | :--- |
| **STEP 0** | 2 min | Accept invite + Log in to GitHub in IntelliJ |
| **STEP 1** | 3 min | Clone the repo |
| **STEP 2** | 2 min | Checkout YOUR branch |
| **STEP 3** | 2 min | Add FlatLaf JAR |
| **STEP 4** | 1 min | Run it first |
| **STEP 5** | — | Start coding your screens |
| **STEP 6** | Daily | Morning → Code → Evening Push |

---

## 🟢 STEP 0 — Before You Clone (Windows)

### 0A. Accept the Invite

1. Go to **github.com** → Click the 🔔 **bell** (top right)
2. Look for: `VanceGalan invited you to OOP-Capstone-Project`
3. Click **Accept**

> 💬 If you don't see it, message Vance in GC.

Vance must set: **Repo → Settings → Collaborators → Your name → Write**

> ⚠️ If you skip this, you can clone but **CANNOT push** (403 error).

### 0B. Log in to GitHub INSIDE IntelliJ

1. IntelliJ → **File → Settings** (`Ctrl + Alt + S`) → **Version Control → GitHub**
2. Click **+** → **Log In via GitHub...**
3. Browser opens → Log in → Authorize JetBrains
4. You should see your GitHub username in IntelliJ ✅

### 0C. Check Git in IntelliJ

1. **File → Settings → Version Control → Git**
2. Look for **Path to Git executable** + **Test** button next to it
3. Click **Test**

| What you see | What it means |
| :--- | :--- |
| `Git version is 2.43.0` ✅ | Git is installed, you're good |
| `Git is not installed` ❌ | Install Git for Windows from git-scm.com, restart IntelliJ, click Test again |

---

## 🟢 STEP 1 — Clone the Repo (Windows)

> Do this **ONCE**. Only once.

1. **File → Close Project** → Welcome screen → **Get from VCS**
2. Paste:
   ```
   https://github.com/VanceGalan/OOP-Capstone-Project.git
   ```
3. Directory — pick a Windows folder, for example:
   ```
   C:\Users\YourName\IdeaProjects\OOP-Capstone-Project
   ```
   > ⚠️ Must be **NEW / EMPTY**. Do NOT clone into a folder that already has files.
4. Click **Clone**
5. **Trust Project** → Wait for indexing

**You should see:**

```
src/
  Main.java
  model/  service/  ui/  util/Theme.java
lib/
  flatlaf-3.8.jar
README.md
SETUP.md
```

> If you see an EMPTY project → You cloned the wrong folder. Do **Git → Pull**.

---

## 🟢 STEP 2 — Checkout YOUR Branch

> 🚫 **DO NOT code on `main`. DO NOT create a new branch.** Branches are already made for you.

| 🧑 Who are you? | 🌿 Your branch — checkout THIS one |
| :--- | :--- |
| **Shima** | `person-shima-auth-payment` |
| **Vance** | `person-vance-customer` |
| **Lance** | `person-lance-organizer` |
| **Jup** | `person-jup-admin` |

### In IntelliJ on Windows:

1. Bottom right → Click branch name (says `main`)
2. Click **Fetch** → Find YOUR branch → Click **Checkout**
3. Bottom right should now say **YOUR** branch ✅

**If missing:** Git → Fetch → Try again → Tell Jup.

> 🔁 **From now on, ALWAYS check bottom right before you code.** If it says `main`, STOP — switch first.

---

## 🟢 STEP 3 — Add FlatLaf JAR (Windows, One Time)

> FlatLaf makes Swing look modern. Without it, `import com.formdev.flatlaf...` will be red.

The JAR is **ALREADY** in the project: `lib/flatlaf-3.8.jar` — **do NOT download again.**

1. **File → Project Structure** (`Ctrl + Alt + Shift + S`) → **Libraries** → **+** → **Java**
2. Go to:
   ```
   C:\Users\YourName\IdeaProjects\OOP-Capstone-Project\lib\
   ```
   Select `flatlaf-3.8.jar` → OK → Apply → OK
3. Red should disappear. If not: File → Invalidate Caches → Restart

### ✅ Also check these two:

| Check | How | Should say |
| :--- | :--- | :--- |
| **JDK** | File → Project Structure → Project → SDK | **17** or **21** |
| **Sources Root** | Right-click `src` → Mark Directory as | **"Unmark as Sources Root"** |

If SDK says `<No SDK>` → Download JDK 17 / 21.
If `src` says `Mark as Sources Root` → Click it.

---

## 🟢 STEP 4 — Run It First (Windows)

> Before you code anything, prove the setup works.

1. Open `src/Main.java`
2. Right-click → **Run 'Main.main()'** (or green ▶️)
3. ✨ **Login window** should open

| Result | Meaning |
| :--- | :--- |
| Login opens ✅ | Setup is correct |
| Red error ❌ | Screenshot the red error at the bottom → Send to GC |

---

## 🟢 STEP 5 — Start Coding Your Screens

### 📍 Where do you code?

| You | Your folder — ONLY code here |
| :--- | :--- |
| **Shima** | `src/ui/auth/` |
| **Vance** | `src/ui/customer/` |
| **Lance** | `src/ui/organizer/` |
| **Jup** | `src/ui/admin/` |

> 🚫 Don't edit someone else's folder without asking in GC.

### 🎨 How to create a new screen (GUI Designer, Windows)

Example: Vance creates **Browse Movies**.

1. Right-click `src/ui/customer/` → **New → Swing UI Designer → GUI Form**
2. Name: `BrowseMovies` → OK
3. IntelliJ makes 2 files:
   - `BrowseMovies.form` — the design
   - `BrowseMovies.java` — the bound class (your code)
4. Drag JPanel, JButton, JLabel, etc. from **Palette** on the right
5. In `.java`, add this (EVERY screen must have it):
   ```java
   public JPanel getMainPanel() {
       return mainPanel;
   }
   ```

### 📐 Layout Rules — Follow These Every Time

| Component | Horizontal Size Policy | Vertical Size Policy |
| :--- | :--- | :--- |
| **Root panel** | — | **GridLayoutManager (IntelliJ)** — always this one |
| **Label** | Fixed | Fixed |
| **TextField / PasswordField / Button** | Can Grow | Fixed |

> 🚫 **Never Fill = Both** for Labels / Buttons — that makes them huge.
>
> 🎯 **Spacing:** Use **Vertical Gap / Horizontal Gap** on the panel, or **Insets** on the component. Don't drag dashed grid lines — they're hard to grab. Use **VSpacer / HSpacer** to center.
>
> 🚫 **Never** `new JButton()` in code. Always drag in Designer.

### 🎨 How to use Theme.java

```java
Theme.stylePrimaryButton(loginBtn); // indigo button
Theme.styleDangerButton(deleteBtn); // red button
titleLabel.setFont(Theme.TITLE);
mainPanel.setBackground(Theme.BACKGROUND);
```

> 🚫 Never: `btn.setBackground(new Color(79, 70, 229));` — always use Theme.

### 🔄 How to navigate

> Don't open a new JFrame. Swap the panel in the **SAME** JFrame.

```java
JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
frame.setContentPane(new BrowseMovies().getMainPanel());
frame.revalidate();
frame.repaint();
```

### ❓ What is `.gitkeep`?

> `.gitkeep` is a tiny empty file inside empty folders like `src/model/`.
>
> Git only tracks **files**, not folders. Without `.gitkeep`, Git would delete your empty folders on push.
>
> Once you put a real `.java` file in that folder, you can delete the `.gitkeep` there. It does nothing in Java.

### ❓ What is Refactor in IntelliJ?

> When you Rename / Move / Delete a file, IntelliJ asks **"Refactor?"**
>
> That means: *"Do you want me to automatically update ALL files that use this file, so nothing breaks?"*
>
> - **Yes** → IntelliJ updates them for you
> - **No** → You fix red errors yourself

---

## 🟢 STEP 6 — Daily Workflow (Windows)

### ☀️ A. Morning — Update your branch from main

1. Bottom right → Switch to **`main`**
2. **Git → Pull**
3. Bottom right → Switch back to **YOUR branch**
4. **Git → Merge Branch** → Select `main` → **Merge**

### 💻 B. Code

- Code only in YOUR folder
- Save often (`Ctrl + S`)
- Run `Main.java` to test

### 🌙 C. Evening — Commit + Push

1. Press **`Ctrl + K`** (Commit window)
2. Tick **ONLY** your changed files
3. Message:
   ```
   Add Browse Movies UI - Vance
   Add Login validation - Shima
   Fix Approve Screening bug - Jup
   ```
   Format: `What you did - YourName`
4. Click **Commit and Push** → **Push**

> ✅ Check: Bottom right still says **YOUR** branch, not `main`.

### 🎉 D. Pull Request (When a screen is DONE)

1. GitHub → **Pull Requests** → **New Pull Request**
2. Base: `main` ← Compare: **YOUR branch**
3. Title: `Browse Movies UI - Vance`
4. **Create Pull Request** → Tag teammate: *"Pa-review PR ko"*
5. Teammate → **Merge Pull Request** → **Confirm Merge**
6. Everyone does **Morning Step** again to get the new code.

---

## 🛠️ Troubleshooting (Windows)

| Problem | Fix |
| :--- | :--- |
| **403 / Push failed** | Didn't Accept invite / Read-only / Wrong GitHub account → File → Settings → Version Control → GitHub |
| **Branch missing** | Git → Fetch → Try again |
| **Coded on main by mistake** | DON'T push. Tell team. Create branch from current → Move commits → Reset main |
| **Merge conflict** | Keep correct version, delete `<<<<<<<` markers, Commit |
| **FlatLaf red error** | Add JAR from `lib/`. Check `.gitignore` does NOT ignore `lib/*.jar` |
| **getMainPanel null** | `.form` not bound. Check `mainPanel` field name matches `.java` |
| **File is not runnable** | `src` must be Sources Root + SDK 17/21 |
| **Directory already exists** | Add `/OOP-Capstone-Project` at end of Directory box when cloning |
| **Spacing huge / Can't drag dashed line** | Use Gap / Insets / Spacer. Labels Fixed/Fixed, TextField Can Grow/Fixed |
| **Already up to date but no teammate code** | Wrong branch. Switch to YOUR branch + Merge main |

---