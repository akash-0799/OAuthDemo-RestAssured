package POJO;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
	
	public static void main(String[] args) {
		
		AddPlace_POJO_Serialization p = new AddPlace_POJO_Serialization();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanhuage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		Response response = given().log().all().queryParam("key", "qaclick123").body(p).when().post("/maps/api/place/add/json").then().
		assertThat().assertThat().statusCode(200).extract().response();
		String responseString = response.asString();
		System.out.println(responseString);
		
		
	}

}
