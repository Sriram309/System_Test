package drivermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {

	public static WebDriverWait wait;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public static WebDriver InitDriver(String browser, String url) throws InterruptedException {
		switch (browser.toUpperCase()) {
		case "CHROME":
			tlDriver.set(new ChromeDriver());
			break;
		case "EDGE":
			tlDriver.set(new EdgeDriver());
			break;
		case "FIREFOX":
			tlDriver.set(new FirefoxDriver());
			break;
		case "SAFARI":
			tlDriver.set(new SafariDriver());
			break;
		default:
			throw new IllegalArgumentException(
					"Given browser of '" + browser + "' is not in the list: [Chrome, Firefox, Edge, Safari]");
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		if (url.isEmpty())
			throw new IllegalArgumentException("Given url is null");
		getDriver().get(url);
		Thread.sleep(10000);
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	public static void SetText(WebElement element, String value) throws IllegalAccessException {
		if (value.isEmpty())
			throw new IllegalAccessException("Given value should not be null");
		element.sendKeys(value);
	}

	public static String getProperty(String Key) {
		Properties p = new Properties();
		String value = "";
		try (FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "//Config.properties")) {
			p.load(fi);
			value = p.getProperty(Key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void captureScreenshot(String name) throws IOException {
		FileUtils.copyFile(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE),
				new File(System.getProperty("user.dir") + "//target//Screenshot//" + name + ".png"));
	}

	public static WebDriverWait Wait() {
		return new WebDriverWait(getDriver(), Duration.ofSeconds(60));
	}
	
	public static void scrollToAnElementByJs(WebElement element){
		((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
	}
}
