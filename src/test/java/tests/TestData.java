package tests;

import java.time.LocalDate;
import org.testng.annotations.DataProvider;

public class TestData {

	@DataProvider(name = "ProductDetail")
	public static Object[][] ProductDetail() {
		return new Object[][] { { "TRULANCE", "Virtual In Office", "No", 1, LocalDate.now().getDayOfMonth(), LocalDate.now().getDayOfMonth(), "PT", "10:00 AM" } };
	}

}
