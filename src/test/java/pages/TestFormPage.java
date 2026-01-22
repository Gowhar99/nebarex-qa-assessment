package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestFormPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // -------- Step 1 Locators --------
    private By fullName = By.xpath("//input[@placeholder='e.g. John Doe']");
    private By email = By.xpath("//input[@placeholder='e.g. john@example.com']");
    private By age = By.xpath("//input[@placeholder='e.g. 25']");
    private By country = By.tagName("select");
    private By phone = By.xpath("//input[@placeholder='e.g. 9876543210']");
    private By nextButton = By.xpath("//button[contains(text(),'Next')]");

    // -------- Review Step Locators --------
    private By reviewTitle = By.xpath("//*[contains(text(),'Review')]");
    private By reviewContent = By.xpath("//*[contains(text(),'fullName')]");
    private By termsCheckbox = By.xpath("//input[@type='checkbox']");
    private By submitButton = By.xpath("//button[contains(text(),'Submit')]");

    // -------- General --------
    private By body = By.tagName("body");

    public TestFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // -------- Navigation --------
    public void open() {
        driver.get("https://dev.nebarex.com/file/tracking/test-form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(nextButton));
    }

    // -------- Step 1 Actions --------
    public void enterFullName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName)).clear();
        driver.findElement(fullName).sendKeys(name);
    }

    public void enterEmail(String emailValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        driver.findElement(email).sendKeys(emailValue);
    }

    public void enterAge(String ageValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(age)).clear();
        driver.findElement(age).sendKeys(ageValue);
    }

    public void selectCountry(String countryName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(country));
        Select select = new Select(driver.findElement(country));
        select.selectByVisibleText(countryName);
    }

    public void enterPhone(String phoneValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(phone)).clear();
        driver.findElement(phone).sendKeys(phoneValue);
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    // -------- Review & Submit --------
    public boolean isReviewStepDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(reviewTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isReviewDataDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(reviewContent));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getReviewText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(body));
        return driver.findElement(body).getText();
    }

    public void acceptTermsAndConditions() {
        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox));
        if (!driver.findElement(termsCheckbox).isSelected()) {
            driver.findElement(termsCheckbox).click();
        }
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isSubmissionSuccessful() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(body, "success"),
                    ExpectedConditions.textToBePresentInElementLocated(body, "submitted")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // -------- Validation Helpers --------
    public boolean isAnyValidationErrorDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(body));
        String pageText = driver.findElement(body).getText().toLowerCase();

        return pageText.contains("required")
                || pageText.contains("please")
                || pageText.contains("enter");
    }
}
