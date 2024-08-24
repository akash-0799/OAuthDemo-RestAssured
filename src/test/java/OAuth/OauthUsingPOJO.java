package OAuth;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.path.json.JsonPath;
public class OauthUsingPOJO {

	public static void main(String[] args) {
		// Expected array of strings
		String [] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		String response = given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				          .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				          .formParams("grant_type", "client_credentials")
				          .formParams("scope", "trust")
				          .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		System.out.println("**************************************************");
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);
		System.out.println("**************************************************");
		GetCourse gc= given().queryParams("access_token", accessToken)
				   .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println("**************************************************");
		System.out.println(gc.getInstructor());
		System.out.println("***************************************************");
		System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());
		System.out.println("***************************************************");
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0; i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice()); 
				System.out.println("***************************************************");
			}
				
		}
		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		for(int i=0; i<webAutomationCourses.size();i++) {
			if(webAutomationCourses.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java")) {
				System.out.println(webAutomationCourses.get(i).getCourseTitle());
				System.out.println(webAutomationCourses.get(i).getPrice()); 
				System.out.println("***************************************************");
			}
			
				
		}
		
		/*
	    System.out.println("All the courses under webAutomation category are as follows:");
		List<WebAutomation> webAutomationCoursesTitles = gc.getCourses().getWebAutomation();
		for(int i=0; i<webAutomationCoursesTitles.size();i++) {
			System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
			}
		System.out.println("***************************************************");
		*/
		
		ArrayList<String> a = new ArrayList<String>(); // Object of ArrayList class
		List<WebAutomation> wct = gc.getCourses().getWebAutomation();
		for(int i=0; i<wct.size();i++) {
			a.add(wct.get(i).getCourseTitle());  
			// add is method in array list which pushes all the newly added strings in the array
			// by the end of the loop the array will have the actual course titles
			}
		List<String> expectedList = Arrays.asList(courseTitles); 
		/* this will convert string of array which was initially declared into the array list
           so that we can compare easily */
		
		Assert.assertTrue(a.equals(expectedList));
		
		}	
}
