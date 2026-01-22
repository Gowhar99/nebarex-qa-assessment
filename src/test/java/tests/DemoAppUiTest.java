package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DemoAppPage;
import utils.ApiClient;
import utils.BaseTest;
import utils.ExtentTestListener;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;
import java.util.Map;

public class DemoAppUiTest extends BaseTest {

    @Test
    public void verifyTrackingDataLoadedFromApiInUi() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        // 🔹 Call API
        Response apiResponse = ApiClient.getTrackingInfo();
        int apiStatusCode = apiResponse.getStatusCode();

        extentTest.info("API Status Code: " + apiStatusCode);
        Assert.assertEquals(apiStatusCode, 200);

        List<Map<String, Object>> apiList =
                apiResponse.jsonPath().getList("$");

        int apiRecordCount = apiList.size();
        extentTest.info("API Record Count: " + apiRecordCount);

        Assert.assertEquals(apiRecordCount, 30,
                "API should return exactly 30 records");

        // 🔹 Open UI
        DemoAppPage page = new DemoAppPage(driver);
        page.open();

        // 🔹 Validate initial UI
        Assert.assertTrue(
                page.isPageTitleDisplayed(),
                "Tracking Info List title should be visible"
        );
        extentTest.info("Tracking Info List title is visible");

        Assert.assertTrue(
                page.isLoadButtonDisplayed(),
                "Load Tracking Data button should be visible"
        );
        extentTest.info("Load Tracking Data button is visible");

        // 🔹 Load data in UI
        page.clickLoadTrackingData();
        extentTest.info("Clicked Load Tracking Data button");

        // ✅ WAIT until status shows "Loaded"
        String statusText = page.waitForStatusToBeLoaded();
        extentTest.info("UI Status Text: " + statusText);

        Assert.assertTrue(
                statusText.contains("Loaded"),
                "Status should indicate data is loaded"
        );

        // 🔹 Validate UI record count
        int uiRecordCount = page.getTrackingCardCount();
        extentTest.info("UI Record Count: " + uiRecordCount);

        Assert.assertEquals(
                uiRecordCount,
                apiRecordCount,
                "UI record count should match API record count"
        );

        Assert.assertEquals(
                uiRecordCount,
                30,
                "UI should display exactly 30 records"
        );

        extentTest.pass("UI data successfully matches API data (30 records)");
    }
}
