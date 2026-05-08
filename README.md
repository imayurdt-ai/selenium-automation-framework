# Enterprise Selenium Automation Framework

> **Production-grade** Selenium 4 + TestNG framework targeting [SauceDemo](https://www.saucedemo.com)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.20-green)](https://selenium.dev)
[![TestNG](https://img.shields.io/badge/TestNG-7.9-blue)](https://testng.org)
[![Maven](https://img.shields.io/badge/Maven-3.9-red)](https://maven.apache.org)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Browser Automation | Selenium 4.20 |
| Test Framework | TestNG 7.9 |
| Build Tool | Maven 3.9 |
| Driver Management | WebDriverManager 5.8 |
| Reporting | ExtentReports 5.1 |
| Logging | Log4j2 2.23 |
| Excel Data | Apache POI 5.2 |
| Assertions | AssertJ 3.25 |

---

## Quick Start

```bash
# Clone
git clone https://github.com/imayurdt-ai/selenium-automation-framework.git
cd selenium-automation-framework

# Run smoke suite (Chrome, QA env)
mvn test -Dsuite=smoke -Denv=qa

# Run regression in parallel
mvn test -Dsuite=regression -Dbrowser=firefox -Dheadless=true

# Run on staging
mvn test -Pstaging -Dbrowser=edge
```

---

## Folder Structure

```
src/
├── main/java/com/enterprise/framework/
│   ├── base/         ← BasePage, BaseTest
│   ├── config/       ← ConfigManager (Singleton)
│   ├── constants/    ← FrameworkConstants
│   ├── driver/       ← DriverManager (ThreadLocal)
│   ├── enums/        ← BrowserType, WaitStrategy, EnvironmentType
│   ├── factory/      ← DriverFactory (Factory Pattern)
│   ├── listeners/    ← TestListener, RetryAnalyzer
│   └── utils/        ← ReportManager, ScreenshotUtil, ExcelUtil, WaitUtil, JsonUtil
├── test/java/com/enterprise/
│   ├── pages/        ← LoginPage, InventoryPage, CartPage, CheckoutPage
│   └── tests/        ← LoginTest, InventoryTest, CartTest, CheckoutTest
└── test/resources/
    ├── suites/       ← smoke.xml, regression.xml
    └── testdata/     ← users.json, inventory.json, checkout.json
```

---

## Design Patterns

- **Factory Pattern** — `DriverFactory` creates browser instances
- **Singleton Pattern** — `ConfigManager` loads config once
- **ThreadLocal Pattern** — `DriverManager` isolates drivers per thread
- **Page Object Model** — each page is a separate class
- **Strategy Pattern** — `WaitStrategy` enum drives all waits

---

## Test Cases

| # | Module | Test Case |
|---|---|---|
| TC-01 | Login | Valid user login |
| TC-02 | Login | Locked user error |
| TC-03 | Login | Empty credentials |
| TC-04 | Login | Wrong credentials |
| TC-05 | Login | Logout flow |
| TC-06 | Inventory | 6 products displayed |
| TC-07 | Inventory | Sort A→Z |
| TC-08 | Inventory | Sort Price Low→High |
| TC-09 | Inventory | Add to cart |
| TC-10 | Cart | Verify cart items |
| TC-11 | Cart | Remove item |
| TC-12 | Checkout | Valid form submission |
| TC-13 | Checkout | Missing first name error |
| TC-14 | Checkout | Complete purchase |
| TC-15 | E2E | Full happy path |

---

## Reports
Extent HTML reports auto-generated in `reports/` after each run.
Screenshots auto-captured to `screenshots/` on test failure.
