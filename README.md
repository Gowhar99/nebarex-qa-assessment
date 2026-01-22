# Nebarex QA Automation Assessment

This repository contains an automated QA solution for validating API and UI functionality using Selenium, TestNG, REST Assured, and Maven.

---

## Tech Stack
- Java 11
- Selenium WebDriver
- TestNG
- REST Assured
- Maven
- Extent Reports
- JaCoCo (code coverage)

---

## Test Coverage

### API Tests
- Validate tracking info API response
- Status code validation
- Response time validation
- Data structure and record count validation
- Negative test for missing API key

### UI Tests
- Validate UI loads tracking data correctly
- Verify API ↔ UI data consistency (30 records)
- Validate UI elements and status messages

### Form Validation Tests
- Empty form submission validation
- Invalid email validation
- Invalid phone number validation
- Missing age validation
- Missing country validation
- End-to-end form submission with review and acknowledgment

---

## Project Structure

src/test/java
├── api -> API test cases
├── pages -> Page Object Model classes
├── tests -> UI and validation test cases
└── utils -> Utilities and base classes


---

## How to Run Tests

### Run all tests
```bash
mvn clean verify
mvn test

Test Reports
Extent HTML Report

After execution, the Extent report is generated at:

test-output/ExtentReport.html


Open it in any browser to view test execution details.

Code Coverage

This project uses JaCoCo for code coverage reporting.

Since the project focuses on API and UI automation and does not contain production code under src/main/java, JaCoCo does not generate a coverage HTML report by default.

JaCoCo is fully configured and will automatically generate coverage reports when application code is present.

Notes

UI automation tests are not typically evaluated using code coverage metrics.

This project follows Page Object Model (POM) design principles.

Explicit waits are used to improve test stability.