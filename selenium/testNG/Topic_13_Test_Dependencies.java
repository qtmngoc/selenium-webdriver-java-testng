package testNG;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

// Đọc Topic 14 để hiểu Listener
// Listener này chỉ áp dụng cho mỗi Topic 13
// Nếu muốn áp dụng cho toàn bộ Topic thì thêm vào file runTestCase.xml
//	<listeners>
//		<listener class-name= "testNG.Topic_14_Listener" />
//	</listeners>
@Listeners(testNG.Topic_14_Listener.class)
public class Topic_13_Test_Dependencies {
	
	@Test
	public void TC_01_Create_User() {
		Assert.assertTrue(true);
		System.out.println("TC 01 Passed");
	}
	
	// Nếu TC_01 fail ->  TC_02: skip
	@Test(dependsOnMethods = "TC_01_Create_User")
	public void TC_02_View_User() {
		Assert.assertTrue(false);
		System.out.println("TC 02 Passed");
	}
	
	// Nếu TC_02: fail -> TC_03: skip
	@Test(dependsOnMethods = "TC_02_View_User")
	public void TC_03_Edit_User() {
		System.out.println("TC 03 Passed");
	}

	// Nếu TC_03: fail -> TC_04: skip
	@Test(dependsOnMethods = "TC_03_Edit_User")
	public void TC_04_Delete_User() {
		System.out.println("TC 04 Passed");
	}
	
	// TC_05 không phụ thuộc -> vẫn chạy nếu các TC trên fail
	@Test()
	public void TC_05_Information_User() {
		System.out.println("TC 05 Passed");
	}
}