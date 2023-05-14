import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

public class ReqResSpecBuilder {

	public static void main(String[] args) {
		
		
		RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON)
		.setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("Key","Qaclick123")
		.build();
		
		given().spec(req).body().post("/maps/api/place/add/json")

	}

}
