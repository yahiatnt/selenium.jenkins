package example;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;
	By searchField = By.name("s");
	By products = By.xpath("//div[contains(@class,'product_grid_item')]");
	By productTitle = By.className("prodtitle");
	By oldPrice = By.className("oldprice");
	By currentPrice = By.className("currentprice");

  @Test
	public void testSearch() {
		driver.get("http://store.demoqa.com");
		WebElement searchFieldObj = driver.findElement(searchField);
		searchFieldObj.clear();
		searchFieldObj.sendKeys("Apple TV");
		searchFieldObj.sendKeys(Keys.ENTER);

		List<WebElement> productsObj = driver.findElements(products);
		for (WebElement productObj : productsObj) {
			if (productObj.findElement(productTitle).getText().equalsIgnoreCase("Apple TV")) {
				Assert.assertEquals("product's old price", "$89.00", productObj.findElement(oldPrice).getText());
				Assert.assertEquals("product's current price", "$80.00",
						productObj.findElement(currentPrice).getText());
			}
			else {
				Assert.fail("There was product with unexpected title in the search results!");
			}
		}
		if (productsObj.size() == 0) {
			Assert.fail("No products were found matching the specified search criteria!");
		}
  }
  @BeforeTest
  public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
  }

  @AfterTest
  public void afterTest() {
		driver.quit();
  }

}
