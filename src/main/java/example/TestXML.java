package example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;

import org.testng.annotations.Test;

public class TestXML {
  @Test
	public void testXMLResponse() {
		given().get("http://www.thomas-bayer.com/sqlrest/").then()
				.body(hasXPath("./CUSTOMERList[contains(text(),'" + "CUSTOMER" + "')]"))
				.log()
				.all();
  }
}
