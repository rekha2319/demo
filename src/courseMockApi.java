import Files.payload;
import io.restassured.path.json.JsonPath;

public class courseMockApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		JsonPath js=new JsonPath(payload.CoursePrice());
		
		//Print No of courses returned by API		
		int totalCoursecount= js.getInt("courses.size()");
		System.out.println(totalCoursecount);
		
		//Print Purchase Amount		
		int purchaseAmount= js.get("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//Print Title of the first course
		String title= js.get("courses[0].title");
		System.out.println(title);
		
		//Print All course titles and their respective Prices
		
		for(int i=0;i<totalCoursecount;i++)
		{
			String title1 = js.get("courses["+i+"].title");
			int price = js.get("courses["+i+"].price");
			System.out.println(title1);
			System.out.println(price);			

		}
		//Print no of copies sold by cypress Course
		
		System.out.println("Print no of copies sold by Cypress Course");
		for(int i=0;i<totalCoursecount;i++)
		{
			String title1 = js.get("courses["+i+"].title");
			if(title1.equalsIgnoreCase("Cypress"))
			{
				int p =js.get("courses["+i+"].copies");
				System.out.println(p);
				break;
			}
		}
		
	}

}
