# Appium Test Automation Framework

A lightweight Java-based automation framework for Android apps using **Appium**, **TestNG**, and **Page Object Model (POM)**. Designed for **sequential test execution**, with no parallel or thread-local setup. Tested on General Store demo app.


## Features

- Appium + TestNG integration
- Page Object Model design pattern
- Centralised driver management
- Rich gesture support: scroll, tap, swipe, long press, drag-drop
- Supports form validation, product flow, and cart verification
- APK bundled locally for ease of setup


## Project Structure
```
├── src
│   ├── main
│   │   └── java
│   │       ├── Pages/               # Page objects (BasePage, FormPage, ProductPage)
│   │       ├── factory/             # Driver and capability management
│   │       ├── resources/           # APKs and utility files              
│   └── test
│       └── java
│           └── Tests/              # TestNG test classes
├── pom.xml                       # Defendency Management
├── README.md                        # This file
```

## Prerequisites

- Java 11+
- Maven
- Appium (`npm install -g appium`)
- Android emulator or physical device
- Appium server running locally


## 🚀 Running Tests

### 1. Start Appium server:
```bash
appium --address 127.0.0.1 --port 4723
```
### 2. Run via Maven:
```bash
mvn test
```
### 3. Or via TestNG Suite:
```bash
Right-click on sanity-suite.xml → Run As → TestNG Suite
```

## Test Scenarios
Sample test cases include:
- Filling and submitting a form (TestFormPage)
- Adding products to cart and verifying price (TestProductPage)
- Validating terms & conditions modal
- Total checkout amount accuracy

All test methods are located in the Tests package.
## Driver Lifecycle
- Managed centrally via DriverFactory
- No parallel execution
- Driver is created in @BeforeMethod, destroyed in @AfterMethod

## Dependencies
Managed through pom.xml, includes:
- Appium Java Client
- Selenium
- TestNG