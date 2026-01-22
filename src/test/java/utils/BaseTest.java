
package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;


@Listeners(utils.ExtentTestListener.class)
public class BaseTest {

    protected WebDriver driver;

    //  just added pause for demo to check the final screen of every test
    private static final boolean DEMO_MODE = true;
    private static final int DEMO_PAUSE_SECONDS = 4;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {

        //  GLOBAL DEMO PAUSE — see final screen of EVERY test
        if (DEMO_MODE) {
            try {
                Thread.sleep(DEMO_PAUSE_SECONDS * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
