package POJO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.specification.RequestSpecification;

public class ECommerceAPI_Test {

	public static void main(String[] args) {
		
		// Login
		
		RequestSpecification req = new RequestSpecBuilder()
				                   .setBaseUri("https://rahulshettyacademy.com")
		                           .setContentType(ContentType.JSON).build();
		LoginRequest lreq = new LoginRequest();
		lreq.setUserEmail("akash.shri2407@gmail.com");
		lreq.setUserPassword("Ietdavv#2499");
		RequestSpecification req_login = given().relaxedHTTPSValidation().log().all().spec(req).body(lreq);
		
		LoginResponse lres = req_login.when().post("/api/ecom/auth/login").then().log().all().extract()
				             .response().as(LoginResponse.class);
		
		System.out.println("Token: "+"\n"+lres.getToken());
		System.out.println("User Id: "+"\n"+lres.getUserId());
		System.out.println("********************************************************");
		
		// Add Product
		
		RequestSpecification ap_req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", lres.getToken()).build();
		
		RequestSpecification req_ap = given().relaxedHTTPSValidation().log().all().spec(ap_req).param("productName","Laptop")
		.param("productAddedBy",lres.getUserId()).param("productCategory","IT")
		.param("productSubCategory", "Hardware").param("productPrice", "40000")
		.param("productDescription", "HP Elite").param("productFor", "All")
		.multiPart("productImage",new File("C:\\Users\\2103125\\Downloads\\Rest Assrured Course\\LaptopImage.jpg"));
		
		String ap_res = req_ap.when().post("/api/ecom/product/add-product").then().log().all().extract()
		.response().asString();
		
		JsonPath js = new JsonPath(ap_res);
		String productId = js.get("productId");
		System.out.println("Product Id:"+"\n"+productId);
		System.out.println("********************************************************");
		
		// Create Order
		
		RequestSpecification co_req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", lres.getToken()).setContentType(ContentType.JSON).build();
		
		OrderDetail od = new OrderDetail();
		od.setCountry("India");
		od.setProductOrderedId(productId);
		
		List<OrderDetail> odl = new ArrayList<OrderDetail>();
		odl.add(od);
		
		Orders o = new Orders();
		o.setOrders(odl);
		
		RequestSpecification req_co = given().relaxedHTTPSValidation().log().all().spec(co_req).body(o);
		
		String co_res = req_co.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		System.out.println("Create Order Response: "+"\n"+co_res);
		
		// Delete Product
		
		RequestSpecification dp_req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", lres.getToken()).setContentType(ContentType.JSON).build();
		
		RequestSpecification req_dp =given().relaxedHTTPSValidation().log().all().spec(dp_req).pathParam("productId", productId);
		
		String dp_res =req_dp.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		// { } is used for path parameter identification
		
		JsonPath js1 = new JsonPath(dp_res);
		Assert.assertEquals("Product Deleted Successfully",js1.get("message") );
		
		
		
	}

}
