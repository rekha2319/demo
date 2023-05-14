import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraApi {

	@Test
public void Jira()
{
		
	//creaing an instance to login into jira
	//Session filter method captures the whole session response and can be used for other http methods. 
		//here the session filter captures the unique id which is created while logging in with our credentials.
		//then that is used before the when().
	//relaxedhttpsvalidation is used for ignoring the http certificate errors.
	RestAssured.baseURI="http://localhost:8080";
	
	SessionFilter session = new SessionFilter();
	
	String response = given().relaxedHTTPSValidation(). header("Content-Type","application/json").body("{ \r\n"
			+ "    \"username\": \"rekha2319\",\r\n"
			+ "    \"password\": \"4nov2006ar.A\" \r\n"
			+ " }").log().all().filter(session).when().post("/rest/auth/1/session").then().extract().response().asString();
	
	
	
	//Adding comment to the existing issue.
	//Path parameter is getting the issue id and then passed inside the post method in curly braces. Anything inside
	// curly braces represents path parameter.
	
	String newComment = "Hi, how are you? I'm fine";
	String commentRes = given().pathParam("id","10201").log().all().header("Content-Type","application/json").body("{\r\n"
			+ "    \"body\": \""+newComment+"\",\r\n"
			+ "    \"visibility\": {\r\n"
			+ "        \"type\": \"role\",\r\n"
			+ "        \"value\": \"Administrators\"\r\n"
			+ "    }\r\n"
			+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().assertThat().statusCode(201)
	.extract().response().asString();
	System.out.println(commentRes);
	
	JsonPath js=new JsonPath(commentRes);
	String commentID= js.getString("id"); //id is extracted from the comment
	
	//Adding attachment to the above existing issue
	//Multi part method is used to send attachment by creating an new "file" class
	//Another header with multipart/form-data has to be added, so that java understands that multipart method is used
	//path paramenter is used to identify the issue and it is passed into the post method.
	
	
	/*given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id","10201").
	header("Content-Type","multipart/form-data")
	.multiPart("file", new File("jira.txt"))
	.when()
	.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);*/
	
	//getting the issue details 
	//path parameter allows to navigate to the specific issue
	//query parameter narrows down the search by filtering the availbale fields.
	//we can use both path and query parameter in a single request
	
	String issueDetails = given().filter(session).pathParam("id","10201").
			queryParam("fields","comment").log().all().when().get("/rest/api/2/issue/{id}")
	.then().log().all().extract().asString();
	
	System.out.println(issueDetails);
	
	//now we are going to extract a specific comment and validate whether it is correct or not.
	//1st we have to parse the above issue details, get its size and iterate it with all comments.
	//for this, we need the id of the comment to identify from all the comments. 
	//Now from the id extract the bodt of the comment and compare it with the actual comment.
	
	JsonPath js1=new JsonPath(issueDetails);
	int commentCount = js1.getInt("fields.comment.comments.size()");
	System.out.println(commentCount);
	
	for(int i=0;i<commentCount;i++)
	{
		String commentIDissue = js1.get("fields.comment.comments["+i+"].id").toString();
			
			if(commentIDissue.equalsIgnoreCase(commentID))
			{
				String msg = js1.get("fields.comment.comments["+i+"].body").toString();
			System.out.println(msg);
			Assert.assertEquals(msg, newComment);
			}
	}}
}
