package testNG;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assert {
	
	@Test
	public void TC_01() {
		String employeeName = "Wendy";
		Object address = null;
		
		Assert.assertTrue(employeeName.equals("Wendy"));
		Assert.assertFalse(employeeName.equals("Jennie"));
		Assert.assertEquals(employeeName, "Wendy");
		
		Assert.assertNull(address);
		address = "Korea";
		Assert.assertNotNull(address);
	}
}
