package example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class TestJSON {
  @Test
  public void testJSONResponse() {
		given().get("http://ip.jsontest.com").then().body("ip", equalTo("41.233.7.15")).log().all();
  }
}
