package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestFormPage;
import utils.BaseTest;

public class TestFormValidationTest extends BaseTest {

    @Test
    public void verifyFormSubmissionWithValidData() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@yopmail.com");
        page.enterAge("30");
        page.selectCountry("India");
        page.enterPhone("9000000000");

        page.clickNext();

        Assert.assertTrue(
                page.isReviewStepDisplayed(),
                "Review step should be displayed after entering valid data"
        );
    }

    @Test
    public void verifyErrorOnEmptyFormSubmission() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.clickNext();

        Assert.assertTrue(
                page.isAnyValidationErrorDisplayed(),
                "Validation errors should be displayed for empty form submission"
        );
    }

    @Test
    public void verifyErrorOnInvalidEmail() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@"); // invalid email
        page.enterAge("30");
        page.selectCountry("India");
        page.enterPhone("9000000000");

        page.clickNext();

        Assert.assertTrue(
                page.isAnyValidationErrorDisplayed(),
                "Validation error should be shown for invalid email"
        );
    }

    @Test
    public void verifyErrorOnInvalidMobileNumber() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@yopmail.com");
        page.enterAge("30");
        page.selectCountry("India");
        page.enterPhone("12345"); // invalid phone

        page.clickNext();

        Assert.assertTrue(
                page.isAnyValidationErrorDisplayed(),
                "Validation error should be shown for invalid mobile number"
        );
    }

    @Test
    public void verifyErrorWhenCountryNotSelected() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@yopmail.com");
        page.enterAge("30");
        page.enterPhone("9000000000");

        page.clickNext();

        Assert.assertTrue(
                page.isAnyValidationErrorDisplayed(),
                "Validation error should be shown when country is not selected"
        );
    }

    @Test
    public void verifyErrorWhenAgeNotEntered() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@yopmail.com");
        page.selectCountry("India");
        page.enterPhone("9000000000");

        page.clickNext();

        Assert.assertTrue(
                page.isAnyValidationErrorDisplayed(),
                "Validation error should be shown when age is not entered"
        );
    }

    @Test
    public void verifyReviewAcknowledgeAndSubmit() {

        TestFormPage page = new TestFormPage(driver);
        page.open();

        page.enterFullName("John Doe");
        page.enterEmail("john@yopmail.com");
        page.enterAge("30");
        page.selectCountry("India");
        page.enterPhone("9000000000");

        page.clickNext();

        Assert.assertTrue(
                page.isReviewStepDisplayed(),
                "Review step should be displayed"
        );

        Assert.assertTrue(
                page.isReviewDataDisplayed(),
                "Review data should be displayed"
        );

        String reviewText = page.getReviewText();
        Assert.assertTrue(reviewText.contains("john@yopmail.com"));
        Assert.assertTrue(reviewText.contains("9000000000"));

        page.acceptTermsAndConditions();
        page.clickSubmit();

        Assert.assertTrue(
                page.isSubmissionSuccessful(),
                "Form should be submitted successfully"
        );
    }
}
