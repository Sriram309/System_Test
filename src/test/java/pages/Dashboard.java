package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import drivermanager.DriverManager;

public class Dashboard {
	private WebDriver driver;

	public Dashboard(WebDriver driver) {
		PageFactory.initElements(this.driver = driver, this);
	}

	@FindBy(xpath = "//a[text()='Go later']")
	private WebElement goLatter;

	@FindBy(xpath = "//a[text() = 'Create']")
	private WebElement createOption;
	@FindBy(xpath = "//a[text() = 'Engagements']")
	private WebElement engagementOption;

	public Dashboard navigateToCreateFeatureEngagementPage() {

		DriverManager.Wait().until(ExpectedConditions.visibilityOf(goLatter));
		goLatter.click();
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(createOption));
		if (createOption.isDisplayed()) {
			createOption.click();
		}
		return this;
	}

	public Dashboard navigateToEngagementPage() {
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(goLatter));
		goLatter.click();
		DriverManager.Wait().until(ExpectedConditions.visibilityOf(engagementOption));
		if (engagementOption.isDisplayed()) {
			engagementOption.click();
		}
		return this;
	}
}
