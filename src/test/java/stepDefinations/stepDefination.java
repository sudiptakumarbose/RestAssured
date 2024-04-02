package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

public class stepDefination extends Utils {
	RequestSpecification res;

	ResponseSpecification respec;
	Response response1;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add Place Payload with {string} {string} {string} {string} {string} {int}")
	public void add_place_payload(String name, String language, String address, String Phone_number, String Website,
			Integer Accuracy) throws IOException {

		res = given().spec(requestSpecification())
				.body(data.addPlacePayload(name, language, address, Phone_number, Website, Accuracy));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST")) {

			response1 = res.when().post(resourceAPI.getResource());
		} else if (method.equalsIgnoreCase("GET")) {
			response1 = res.when().get(resourceAPI.getResource());
		}

	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
		assertEquals(response1.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void is_response_body_is_ok(String key, String value) {
		// Write code here that turns the phrase above into concrete actions

		assertEquals(getJsonpath(response1, key), value);

	}

	@Then("verify placeID created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id = getJsonpath(response1, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_post_http_request(resource, "GET");
		String actualName = getJsonpath(response1, "name");
		assertEquals(actualName, expectedName);

	}

	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}

}
