package testNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Always_Run {
	String projectPath = System.getProperty("user.dir");
	WebDriver driver;

	// Always Run giúp cho Before class và After Class luôn được chạy khi Run Test case 
	// Khi bị fail ở Before Class nó vẫn sẽ tiếp tục Run Test case khác và cả After Class
	@BeforeClass(alwaysRun = true)
	public void initBrowser() {
		System.out.println("Open Browser");
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test(groups = "user")
	public void TC_01_Create_User() {

	}

	@Test(groups = { "user" })
	public void TC_02_View_User() {

	}

	@Test(groups = { "user", "admin" })
	public void TC_03_Edit_User() {

	}

	@Test(groups = "admin")
	public void TC_04_Delete_User() {

	}
	
	@AfterClass(alwaysRun = true)
	public void cleanBrowser() {
		System.out.println("Close Browser");
		driver.quit();
	}
}