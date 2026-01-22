// package pages;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;

// public class DemoAppPage {
//     private WebDriver driver;
//     private By body = By.tagName("body");

//     public DemoAppPage(WebDriver driver) {
//         this.driver = driver;
//     }

//     public void open() {
//         driver.get("https://dev.nebarex.com/file/tracking/demo-app");
//     }

//     public String getPageText() {
//         return driver.findElement(body).getText();
//     }
// }
// package pages;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;

// public class DemoAppPage {

//     private WebDriver driver;

//     // Locators
//     private By loadButton = By.xpath("//button[contains(text(),'Load Tracking Data')]");
//     private By statusLabel = By.xpath("//*[contains(text(),'Status')]");
//     private By body = By.tagName("body");

//     public DemoAppPage(WebDriver driver) {
//         this.driver = driver;
//     }

//     public void open() {
//         driver.get("https://dev.nebarex.com/file/tracking/demo-app");
//     }

//     public void clickLoadTrackingData() {
//         driver.findElement(loadButton).click();
//     }

//     public String getPageText() {
//         return driver.findElement(body).getText();
//     }

//     public String getStatusText() {
//         return driver.findElement(statusLabel).getText();
//     }
// }
// package pages;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;

// public class DemoAppPage {

//     private WebDriver driver;

//     // Locators
//     private By pageTitle = By.xpath("//h1[contains(text(),'Tracking Info List')]");
//     private By loadButton = By.xpath("//button[contains(text(),'Load Tracking Data')]");
//     private By statusLabel = By.xpath("//*[contains(text(),'Status')]");
//     private By body = By.tagName("body");

//     public DemoAppPage(WebDriver driver) {
//         this.driver = driver;
//     }

//     // -------- Navigation --------
//     public void open() {
//         driver.get("https://dev.nebarex.com/file/tracking/demo-app");
//     }

//     // -------- Actions --------
//     public void clickLoadTrackingData() {
//         driver.findElement(loadButton).click();
//     }

//     // -------- Validations --------
//     public boolean isPageTitleDisplayed() {
//         return driver.findElement(pageTitle).isDisplayed();
//     }

//     public boolean isLoadButtonDisplayed() {
//         return driver.findElement(loadButton).isDisplayed();
//     }

//     public boolean isStatusDisplayed() {
//         return driver.findElement(statusLabel).isDisplayed();
//     }

//     public String getStatusText() {
//         return driver.findElement(statusLabel).getText();
//     }

//     public String getPageText() {
//         return driver.findElement(body).getText();
//     }
// }
// package pages;

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoAppPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pageTitle = By.xpath("//*[contains(text(),'Tracking Info List')]");
    private By loadButton = By.xpath("//button[contains(text(),'Load Tracking Data')]");
    private By statusLabel = By.xpath("//*[contains(text(),'Status')]");
    private By trackingCards = By.cssSelector(".card");

    public DemoAppPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get("https://dev.nebarex.com/file/tracking/demo-app");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    public boolean isPageTitleDisplayed() {
        return driver.findElements(pageTitle).size() > 0;
    }

    public boolean isLoadButtonDisplayed() {
        return driver.findElements(loadButton).size() > 0;
    }

    public void clickLoadTrackingData() {
        wait.until(ExpectedConditions.elementToBeClickable(loadButton)).click();
    }

    // ✅ Wait until status becomes Loaded
    public String waitForStatusToBeLoaded() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                statusLabel, "Loaded"
        ));
        return driver.findElement(statusLabel).getText();
    }

    public int getTrackingCardCount() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                trackingCards, 0
        ));
        return driver.findElements(trackingCards).size();
    }
}

