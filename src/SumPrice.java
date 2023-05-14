import org.testng.Assert;
import org.testng.annotations.Test;
import Files.payload;
import io.restassured.path.json.JsonPath;

public class SumPrice {
	
	@Test
	public void sumValidations()
	{
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum =0 ;
		JsonPath js=new JsonPath(payload.CoursePrice());		
		int count = js.getInt("courses.size()");
		//System.out.println(count);
		
		
		for(int i=0;i<count;i++)
		{
			
		int prices = js.getInt("courses["+i+"].price");
		int copies = js.get("courses["+i+"].copies");
		int Totalsum = prices * copies;
		System.out.println(Totalsum);
		sum = sum + Totalsum;
		
	}
		System.out.println(sum);
		int actualamt = js.get(" dashboard.purchaseAmount");
		Assert.assertEquals(sum, actualamt);
	}
}
