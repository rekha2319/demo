import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class dynamicJson {
	
	@Test(dataProvider="Booksdata")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=
 given().header("Content-Type","application/json").body(payload.Addbook(isbn,aisle)).
				when().post("/Library/Addbook.php").
				then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js=
	ReusableMethods.rawToJson(response);
		String id=
		js.get("ID");
		
		System.out.println(id);
	}
	
	
	@DataProvider(name="Booksdata")
	public Object[][] getData()
	{
		return new Object[][] {{"wdfd","1122"},{"dddss","2333"},{"eeee","3322"}};
	}
}
