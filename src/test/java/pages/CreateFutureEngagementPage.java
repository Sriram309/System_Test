package pages;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import drivermanager.DriverManager;

public class CreateFutureEngagementPage {
	private WebDriver driver;

	public CreateFutureEngagementPage(WebDriver driver) {
		PageFactory.initElements(this.driver = driver, this);

	}

	@FindBy(xpath = "//label[@class='each-topic']")
	private List<WebElement> createOption;
	@FindBy(xpath = "//input[@name = 'topicid']/parent::label//ul//li/span")
	private List<WebElement> listOfSelectTopics;
	@FindBy(xpath = "//div[@id='calendar']//td[@class = 'today day']")
	private WebElement currentDate;
	@FindBy(xpath = "//div[@id='products']//i/parent::a[text()='Next ']")
	private WebElement nextButton;
	@FindBy(xpath = "//h4/b[text() = 'Confirm ']")
	private WebElement confirmPopUp;
	@FindBy(xpath = "//a[text() = 'Ok ']")
	private WebElement okButton;
	@FindBy(xpath = "//select[@name='timezone']")
	private WebElement selectTimeZone;
	@FindBy(xpath = "//a[text()='Continue']")
	private WebElement continueButton;
	@FindBy(xpath = "//div[@class='slotnewlist']/p")
	private WebElement scheduledTime;

	private WebElement pastDay(int pastDateinNo) {
		return driver
				.findElement(By.xpath("//div[@id='calendar']//td[@class = 'today day']/preceding-sibling::td[text() = '"
						+ pastDateinNo + "']"));
	}

	private WebElement selectTiming(String time) {
		return driver.findElement(By.xpath("//span[text() = '" + time + "']/../parent::li"));
	}

	private WebElement featureDay(int featureDay) {
		return driver
				.findElement(By.xpath("//div[@id='calendar']//td[@class = 'today day']/following-sibling::td[text() = '"
						+ featureDay + "']"));
	}

	private WebElement selectProduct(String productname) {
		return driver.findElement(
				By.xpath("//label[@class='each-topic']//h4/span[text() = '" + productname.toUpperCase() + "']"));
	}

	private WebElement selectEngagementType(String typeOfEngagement) {
		return driver.findElement(
				By.xpath("//ul[@class='company-info type-icon']//li[text() = ' " + typeOfEngagement + "']"));
	}

	private WebElement selectMultipleTopic(String multipleTopic) {
		return driver.findElement(
				By.xpath("(//span[normalize-space()='" + multipleTopic + "']/preceding-sibling::input/..)[2]"));
	}

	public CreateFutureEngagementPage verifyAllSelectedProdectsAreListed() throws IOException {
		DriverManager.Wait()
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//label[@class='each-topic']")));
		Assert.assertTrue(createOption.size() == 3, "All the 3 products are not displayed");
		DriverManager.captureScreenshot(
				"ListedProduct" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddyyyyHHmmss")));
		return this;
	}

	public CreateFutureEngagementPage SelectAProductAndCompleteTheDetails(String productName, String engagementType,
			String multipleChoice, int topic, int pastDay, int futureDay, String timeZone, String time)
			throws InterruptedException, Throwable {
		Thread.sleep(2000);
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(selectProduct(productName)));
		selectProduct(productName).click();
		Thread.sleep(2000);
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(selectEngagementType(engagementType)));
		selectEngagementType(engagementType).click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(selectMultipleTopic(multipleChoice)));
		selectMultipleTopic(multipleChoice).click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(listOfSelectTopics.get(topic - 1)));
		listOfSelectTopics.get(topic - 1).click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(currentDate));
		Assert.assertTrue(pastDay(pastDay).getDomAttribute("class").equalsIgnoreCase("disabled day"));
		DriverManager.scrollToAnElementByJs(featureDay(futureDay));
		featureDay(futureDay).click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(selectTimeZone));
		(new Select(selectTimeZone)).selectByVisibleText(timeZone);
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(selectTiming(time)));

		selectTiming(time).click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(continueButton));
		continueButton.click();

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(scheduledTime));
		Assert.assertTrue(scheduledTime.isDisplayed());

		return this;
	}

}
