package pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import drivermanager.DriverManager;

public class LoginPage {
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(this.driver = driver, this);
	}

	@FindBy(xpath = "//input[@placeholder='Email']")
	private WebElement email;
	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement password;
	@FindBy(xpath = "//button[text()='Log in']")
	private WebElement loginButton;
	@FindBy(xpath = "//span[@class = 'loading-img']")
	private WebElement lodingImg;
	@FindBy(xpath = "//div[starts-with(@class, 'to-title')]")
	private WebElement errorTitle;
	@FindBy(xpath = "//span[starts-with(@class, 'to-message')]")
	private WebElement errormessage;
	@FindBy(xpath = "//a[text() = 'Go later']")
	private WebElement goLaterButton;

	public LoginPage fillAndSubmitLoginDetail(String userName, String passwor) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(email));
		email.clear();
		email.sendKeys(userName);
		password.clear();
		password.sendKeys(passwor);
		if (loginButton.isDisplayed()) {
			loginButton.click();
		}
		return this;
	}

	public LoginPage VerifySuccesfullUserLoginDetails() throws Exception {

		DriverManager.Wait()
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Go now']"))));

		Assert.assertTrue(true, "Login Successfull");
		DriverManager.captureScreenshot(
				"SuccessFulllogin" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddyyyyHHmmss")));
		return this;
	}

	public boolean waitForLoadingImageToComplete() throws IOException {

		try {
			DriverManager.captureScreenshot("HomePage");
			DriverManager.Wait().until(ExpectedConditions.presenceOfElementLocated(By.id("toastr-container")));
			DriverManager.Wait().until(ExpectedConditions.invisibilityOf(lodingImg));
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public LoginPage VerifyUserLoginErrorWarning() throws IOException {
		if (waitForLoadingImageToComplete()) {
			Assert.assertEquals(errorTitle.getText(), "Login Failed");
		}
		return this;
	}

}
