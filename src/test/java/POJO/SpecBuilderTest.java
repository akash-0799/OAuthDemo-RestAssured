package POJO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {
	
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
		
		RequestSpecification req_spec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON)
		.build();
		
		ResponseSpecification res_spec = new ResponseSpecBuilder()
									     .expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res =  given().spec(req_spec).body(p);
		
        Response response = res.when().post("/maps/api/place/add/json")
		                    .then().spec(res_spec).extract().response();
        
		String responseString = response.asString();
		System.out.println(responseString);
		
		
	}

}

/*
 
1.
    RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("url").addQueryParam("key", "value")
                               .setContentType(ContentType.JSON).build();
                               
    given().spec(reqSpec).body("payload").when().post(x/y/z).then

2.
    RequestSpecification rr = given().log().all().header("Content-Type","application/json")
                          .queryParam("key", "qaclick123");

    rr.body("payload").when().post(x/y/z).then
    
*/
