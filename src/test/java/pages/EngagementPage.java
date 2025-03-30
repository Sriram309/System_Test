package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import drivermanager.DriverManager;

public class EngagementPage {
	private WebDriver driver;

	public EngagementPage(WebDriver driver) {
		PageFactory.initElements(this.driver = driver, this);
	}

	@FindBy(xpath = "//input[@aria-label='Date range input field']")
	private WebElement dateDropdown;
	@FindBy(xpath = "//button[@class = 'headerlabelbtn yearlabel']")
	private WebElement yearSection;
	@FindBy(xpath = "//button[@class = 'headerlabelbtn monthlabel']")
	private WebElement monthSection;
	@FindBy(xpath = "//td[starts-with(@class, 'yearchangebtncell')]/button[contains(@class, 'up')]")
	private WebElement yearUp;
	@FindBy(xpath = "//td[starts-with(@class, 'yearchangebtncell')]/button[contains(@class, 'down')]")
	private WebElement yearDown;

	private WebElement dateSelection(String day) {
		return driver.findElement(By.xpath("(//td[starts-with(@class, 'daycell currmonth')]/div)[" + day + "]"));
	}

	private WebElement monthSelection(String month) {
		return driver.findElement(By.xpath("//td[starts-with(@class, 'monthcell')]/div[text() = '" + month + "']"));
	}

	private WebElement yearSelection(String year) {
		return driver.findElement(By.xpath("//td[starts-with(@class, 'yearcell')]/div[text() = '" + year + "']"));
	}

	private WebElement SelectedDate(String date) {
		return driver.findElement(By.xpath(
				"//button[text() = 'Time Zone Review']/parent::app-clock-details-info/../following-sibling::div //p[contains(text(), '"
						+ date + "')]"));
	}

	public EngagementPage SelectTheDateRange(String startDate, String endDate) throws InterruptedException {
		String[] startDates = startDate.split("/");
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(dateDropdown));
		Thread.sleep(2000);
		dateDropdown.click();
		SelectDateOnCalander(startDates);
		String[] endDates = endDate.split("/");
		SelectDateOnCalander(endDates);
		Thread.sleep(5000);
		return this;
	}

	public EngagementPage verifyTheTopicsDateRange(String startDate, String endDate) {
		Assert.assertTrue(SelectedDate(startDate).isDisplayed());
		Assert.assertTrue(SelectedDate(endDate).isDisplayed());
		return this;
	}

	public void SelectDateOnCalander(String[] dates) throws InterruptedException {
		String month = dates[0], day = dates[1], year = dates[2];
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(yearSection));
		yearSection.click();
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(yearSelection(year)));
		if (yearSelection(year).isDisplayed()) {
			yearSelection(year).click();
		}
		monthSection.click();
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(monthSelection(month)));
		if (monthSelection(month).isDisplayed()) {
			monthSelection(month).click();
		}
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(dateSelection(day)));
		dateSelection(day).click();
		Thread.sleep(2000);
	}
}
