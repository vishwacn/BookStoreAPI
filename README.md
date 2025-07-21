## Tech Stack

| Tool/Library      | Purpose                                |
| Java (17)         | Programming language                   |
| RestAssured       | API automation                         |
| TestNG            | Test execution framework               |
| ExtentReports     | HTML reporting                         |
| Maven             | Build and dependency management        |
| GitHub Actions    | CI/CD pipeline                         |
| JaCoCo            | Code coverage                          |

Allure Report Setting
=====================
Press Win+R and enter the command: **sysdm.cpl** to open the System Properties tool.

On the Advanced tab, click **Environment variables**.

In the Edit environment variable dialog, click New to add a new line entry to the paths list.
In the new line, specify the full path to the bin subdirectory from an earlier step.
If the list contains a path to a previously installed Allure version, delete it.

Click OK to save the changes.
**allure --version**
2.32.0
**Generate Allure Report**
allure serve target/allure-results
allure generate target/allure-results --clean -o target/allure-report

## How to View GitHub Actions Reports
Go to repo URL (https://github.com/vishwacn/BookStoreAPI.git) → Actions
Click on the latest workflow run.
Scroll to Artifacts.
Download-Extent Report (ExtentReports.html)





