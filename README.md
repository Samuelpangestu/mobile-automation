# Mobile Automation - SauceDemo App

Mobile automation testing using Appium + Cucumber + Java with centralized configuration and Page Object Model architecture.

---

## Quick Start

```bash
cd mobile-automation

# Run all tests (Allure report auto-opens)
./run_test.sh android

# Run specific tags
./run_test.sh android @smoke
./run_test.sh android @login
./run_test.sh android @product
```

**Note:** Script automatically opens Allure report in browser after test completion. Press `Ctrl+C` to stop the report server.

---

## Tech Stack

- **Framework:** Appium 8.6.0 + Cucumber 7.16.1
- **Language:** Java 17
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model + BasePage
- **Reporting:** Allure 2.26.0
- **BDD:** Cucumber Gherkin

---

## Features

- Centralized configuration (all settings in `config.properties`)
- Centralized locators (all locators in `locators-android.properties`)
- DRY architecture with BasePage (50% less code duplication)
- Automatic screenshot on test failure
- BDD with Cucumber Gherkin syntax
- Professional Allure test reports
- 12 test scenarios covering login, dashboard, and product flows

---

## Prerequisites

```bash
# Required
Java 17+
Maven 3.6+
Node.js
Android SDK

# Install Appium
npm install -g appium
appium driver install uiautomator2
```

---

## Installation

```bash
# 1. Navigate to project
cd mobile-automation

# 2. Install dependencies (Maven will download automatically on first run)
mvn clean compile

# 3. Ensure Appium is running
appium

# 4. Start Android emulator or connect device
```

---

## Project Structure

```
mobile-automation/
├── src/main/
│   ├── java/.../pages/
│   │   ├── BasePage.java          # Common methods (DRY)
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   └── ProductPage.java
│   ├── java/.../utils/
│   │   ├── ConfigReader.java      # Read config.properties
│   │   └── LocatorReader.java     # Read locators-android.properties
│   └── resources/
│       ├── config.properties      # All settings (timeouts, credentials, device)
│       └── locators-android.properties  # All element locators
├── src/test/
│   ├── java/.../tests/
│   │   └── StepDefinitions.java   # Hooks + Step definitions
│   └── resources/features/
│       ├── login.feature
│       ├── dashboard.feature
│       └── product.feature
├── apps/mda-2.2.0-25.apk
└── run_test.sh
```

---

## Running Tests

### Using Script (Recommended)

```bash
# Run all tests
./run_test.sh android

# Run by tags
./run_test.sh android @smoke        # Smoke tests
./run_test.sh android @login        # Login tests
./run_test.sh android @dashboard    # Dashboard tests
./run_test.sh android @product      # Product tests
```

### Manual Execution

```bash
# Compile project
mvn clean compile test-compile

# Run all tests
mvn clean test -Dplatform=android

# View Allure report
mvn allure:serve
```

---

## Configuration

### Settings (`config.properties`)

```properties
# Timeouts
timeout.implicit = 10
timeout.animation = 1

# Device Configuration
android.device.name = Android Emulator
android.app.path = apps/mda-2.2.0-25.apk

# Test Credentials
test.user.valid.username = bob@example.com
test.user.valid.password = 10203040
```

**To modify:** Edit `config.properties` - no code changes needed!

### Locators (`locators-android.properties`)

```properties
login.username = id:com.saucelabs.mydemoapp.android:id/nameET
login.password = id:com.saucelabs.mydemoapp.android:id/passwordET
login.button = id:com.saucelabs.mydemoapp.android:id/loginBtn
dashboard.title = id:com.saucelabs.mydemoapp.android:id/productTV
```

**To modify:** Edit `locators-android.properties` - no code changes needed!

---

## Test Coverage

**Total: 12 Test Scenarios**

| Category | Test Count | Status |
|----------|-----------|--------|
| Login | 4 | Verified |
| Dashboard | 4 | Verified |
| Product | 4 | Verified |

**Login Tests:**
- Successful login
- Invalid credentials
- Empty credentials
- Logout functionality

**Dashboard Tests:**
- Dashboard displayed after login
- Product catalog visible
- Menu navigation
- Cart accessible

**Product Tests:**
- Add product to cart
- Sort products by price
- View product details
- Remove product from cart

---

## Appium Inspector Setup

```bash
# 1. Install Appium Inspector
brew install --cask appium-inspector

# 2. Start Appium server
appium

# 3. Launch app on emulator
adb shell am start -n com.saucelabs.mydemoapp.android/.view.activities.SplashActivity

# 4. Connect Inspector with these capabilities:
{
  "platformName": "Android",
  "appium:deviceName": "Android Emulator",
  "appium:automationName": "UiAutomator2",
  "appium:appPackage": "com.saucelabs.mydemoapp.android",
  "appium:appActivity": ".view.activities.SplashActivity",
  "appium:noReset": true
}

# 5. Inspect elements and update locators-android.properties
```

---

## Adding New Tests

**1. Add scenario to feature file:**
```gherkin
# features/product.feature
Scenario: TC_13 - New test scenario
  When I perform action
  Then I should see expected result
```

**2. Add locator to properties file:**
```properties
# locators-android.properties
product.new.element = id:com.saucelabs.mydemoapp.android:id/newElement
```

**3. Add method to page object:**
```java
// ProductPage.java
public void performAction() {
    clickAndWait("product.new.element");  // Reuse BasePage method
}
```

**4. Add step definition:**
```java
// StepDefinitions.java
@When("I perform action")
public void iPerformAction() {
    productPage.performAction();
}
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Element not found | Update locator in `locators-android.properties` |
| Timeout exception | Increase timeout in `config.properties` |
| Appium connection error | Ensure Appium server is running with `appium` |
| APK not found | Verify `android.app.path` in `config.properties` |
| Device not connected | Check device/emulator with `adb devices` |

---

## CI/CD Integration

This project can be integrated with:
- Jenkins pipelines
- GitHub Actions workflows
- GitLab CI/CD

See main repository documentation for CI/CD setup examples.

---

## Documentation

For complete architecture and design decisions, see root documentation folder.

---

## Created by

Samuel
