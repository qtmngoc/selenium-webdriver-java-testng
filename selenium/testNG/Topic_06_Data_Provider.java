package testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Topic_06_Data_Provider {
	WebDriver driver;
	By emailTextbox = By.cssSelector("input#email");
	By passwordTextbox = By.cssSelector("input#pass");
	By loginButton = By.cssSelector("button#send2");
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	// Chạy 3 lần test case với 3 dữ liệu khác nhau, tham số là username và password
	// Gọi provider name = user_pass
	@Test(dataProvider = "user_pass")
	public void TC_01_Login_To_System(String username, String password) {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
		
		driver.findElement(emailTextbox).sendKeys(username);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(loginButton).click();
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div.col-1 p")).getText().contains(username));
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.cssSelector("div#header-account a[title='Log Out']")).click();
	}
	
	// Tạo 3 dữ liệu khác nhau cho mỗi lần test case
	// Đặt tên cho prodiver là name = user_pass
	@DataProvider(name = "user_pass")
	public Object[][] UserAndPasswordData(){
		return new Object[][] {
			{"selenium_11_01@gmail.com","111111"},
			{"selenium_11_02@gmail.com","111111"},
			{"selenium_11_03@gmail.com","111111"}
		};
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}