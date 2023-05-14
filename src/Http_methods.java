import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.ReusableMethods;
import Files.payload;

public class Http_methods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//given - add all the inputs like parameter, header, body
		//when - submit the api ----> resource, http methods
		// Then - validate the response --->with assertions
		//log().all()----> logs the request and response
		//the json body has been written in seperate class in another package and called in here.
		//post method has the resource
		//equalTo is resolved by importing hamcrest package manually
		//given is resolved by importing static io package manually
		//json body,header, response etc anything can be validated.
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.addPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Connection", "Keep-Alive").extract().response().asString();
		
		System.out.println("ID =" + response);
		
		// we are adding a place--> updating the place---> get the updated address as response	
		JsonPath js= new JsonPath(response);		
		String  placeid= js.getString("place_id");		
		System.out.println(placeid);
		
		// update the place
		//place_id is used dynamically
		
		String updatedAddress = "AR Villa, USA";//-----> this is dynamically given in the body
		
		given().log().all().queryParam("Key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+updatedAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
	//get the updated address and validate it
		//Get methods does not have header and body.
		
		String getResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
		/*JsonPath js1=new JsonPath(getResponse);
		String newupdatedAddress = js1.getString("address"); //address is the path we are giving, here it does not have any parent path
		Assert.assertEquals(newupdatedAddress, updatedAddress);
		*/
	//now method 2 we are creating a separate method for json and calling it here
		//replacing line no 60 and 61 by below method.
		JsonPath js1 =  ReusableMethods.rawToJson(getResponse);
		String newupdatedAddress = js1.getString("address");
		Assert.assertEquals(newupdatedAddress, updatedAddress);
		
		
		
		System.out.println(newupdatedAddress);
		
	
	}

}
