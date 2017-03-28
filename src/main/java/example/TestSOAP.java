package example;

import static io.restassured.RestAssured.given;
//import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasXPath;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class TestSOAP {
  @Test
	public void testSOAPResponse() {
		// http://services.aonaware.com/DictService/DictService.asmx?op=Define
		// http://services.aonaware.com/DictService/DictService.asmx?wsdl

		String baseURI = "http://www.webservicex.net";
		int port = 80;
		RestAssured.baseURI = baseURI;
		RestAssured.port = port;

		String myEnvelope = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "<soap12:Body>" + "<ConvertTemp xmlns=\"http://www.webserviceX.NET/\">"
				+ "<Temperature>35</Temperature>" + "<FromUnit>degreeCelsius</FromUnit>"
				+ "<ToUnit>degreeFahrenheit</ToUnit>" + "</ConvertTemp>" + "</soap12:Body>" + "</soap12:Envelope>";

		Map<String, String> authhdrs = new HashMap<String, String>();
		authhdrs.put("SOAPAction", "ConvertTemp");
		// authhdrs.put("Content-Length",
		// Integer.toString(myEnvelope.length()));

		Response resp = given()/*
								 * .config(RestAssured.config().xmlConfig(
								 * xmlConfig().with().namespaceAware(true)))
								 */
				.request().headers(authhdrs).contentType("application/soap+xml; charset=UTF-8;")
				.body(myEnvelope).when().post("/ConvertTemperature.asmx").andReturn();
		// System.out.println(resp.xmlPath().get("Body.ConvertTempResponse.ConvertTempResponse"));
		String xml = resp.asString();
		resp.then().log().all().body("Envelope.Body.ConvertTempResponse.ConvertTempResult", equalTo("95"));
		// resp.then().body(hasXPath("//ConvertTempResponse/ConvertTempResult[text()='95']"));
		XmlPath xmlPath = new XmlPath(xml);
		System.out
				.println("Convert Temp Result = " + xmlPath.get("Envelope.Body.ConvertTempResponse.ConvertTempResult"));
  }
}
