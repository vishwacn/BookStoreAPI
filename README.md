### ✅ Tech Stack

| Tool/Library       | Purpose                         |
| ------------------ | ------------------------------- |
| **Java (17)**      | Programming language            |
| **RestAssured**    | API test automation             |
| **TestNG**         | Test execution framework        |
| **ExtentReports**  | HTML reporting framework        |
| **Maven**          | Build and dependency management |
| **GitHub Actions** | CI/CD automation pipeline       |
| **JaCoCo**         | Code coverage analysis          |

---

### ⚙️ Allure Report Setup

1. Press `Win + R`, type `sysdm.cpl`, and hit **Enter** to open **System Properties**.
2. Go to the **Advanced** tab and click on **Environment Variables**.
3. Under **System Variables**, find and edit the `Path` variable.
4. In the **Edit Environment Variable** dialog:

   * Click **New**.
   * Add the full path to the `bin` directory of your Allure installation.
   * Remove any older Allure paths if present.
5. Click **OK** to save and close all dialogs.

#### ✅ Verify Installation

allure --version   # Expected output: 2.32.0

#### 📊 Generate Allure Report

allure serve target/allure-results
# OR
allure generate target/allure-results --clean -o target/allure-report

---

### 📥 How to View GitHub Actions Reports

1. Go to the repository:
   👉 [https://github.com/vishwacn/BookStoreAPI](https://github.com/vishwacn/BookStoreAPI)
2. Click on the **Actions** tab.
3. Select the most recent **workflow run**.
4. Scroll down to the **Artifacts** section.
5. Download the report named `ExtentReports.html`.

---
