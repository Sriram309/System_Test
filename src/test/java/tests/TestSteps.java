package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import drivermanager.DriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.CreateFutureEngagementPage;
import pages.Dashboard;
import pages.EngagementPage;
import pages.LoginPage;

public class TestSteps extends DriverManager {
	public WebDriver driver;
	public LoginPage loginPage;

	@Parameters(value = "Browser")
	@BeforeMethod
	public void beforeMethod(String browser) throws InterruptedException {
		driver = InitDriver(browser, getProperty("Url"));
		loginPage = new LoginPage(driver);
	}

	@Test()
	@Feature("Login Feature")
	@Story("Valid Login Test")
	@Description("The test verifies valid and invalid email & password")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithDifferentUser() throws Exception {

		loginPage.fillAndSubmitLoginDetail("invalidemail@gmail.com", getProperty("Password"));

		loginPage.fillAndSubmitLoginDetail(getProperty("Username"), "123456");

		loginPage.fillAndSubmitLoginDetail(getProperty("Username"), getProperty("Password"))
				.VerifySuccesfullUserLoginDetails();

	}

	@Test(dataProvider = "ProductDetail", dataProviderClass = TestData.class)
	@Feature("Test 2")
	@Story("Valid Test2")
	@Description("The test verifies valid and invalid email & password")
	public void Test2(String productName, String engagementType, String multipleChoice, int topic, int pastDay,
			int futureDay, String timeZone, String time) throws Throwable {
		loginPage.fillAndSubmitLoginDetail(getProperty("Username"), getProperty("Password"))
				.VerifySuccesfullUserLoginDetails();
		(new Dashboard(driver)).navigateToCreateFeatureEngagementPage();
		(new CreateFutureEngagementPage(driver)).verifyAllSelectedProdectsAreListed()
				.SelectAProductAndCompleteTheDetails(productName, engagementType, multipleChoice, topic, pastDay - 1,
						futureDay + 1, timeZone, time);
	}

	@Test()
	@Feature("Test 3")
	@Story("Valid Test3")
	public void Test3() throws Exception {
		loginPage.fillAndSubmitLoginDetail(getProperty("Username"), getProperty("Password"))
				.VerifySuccesfullUserLoginDetails();
		(new Dashboard(driver)).navigateToEngagementPage();
		(new EngagementPage(driver)).SelectTheDateRange("Mar/3/2025", "Mar/18/2025")
				.verifyTheTopicsDateRange("Mar 3, 2025", "Mar 14, 2025");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
}
