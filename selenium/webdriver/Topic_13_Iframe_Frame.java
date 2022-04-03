package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Iframe_Frame {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
	
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Iframe_Kyna() {
		driver.get("https://kyna.vn/");
		
		// Switch qua iframe Facebook
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));	
		
		String kynaFBLikes = driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText();
		System.out.println("Number of likes on Kyna's Facebook page: " + kynaFBLikes);
		
		// Switch về trang chính
		driver.switchTo().defaultContent();
		
		// Switch qua iframe chat
		driver.switchTo().frame("cs_chat_iframe");
		driver.findElement(By.cssSelector("div.button_bar")).click();
		sleepInSecond(1);
		
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("Black Pink");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0897654321");
		new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Thông tin khóa học Java");
		sleepInSecond(2);
		
		driver.switchTo().defaultContent();

		String keyword = "Excel";
		driver.findElement(By.id("live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(2);
		
		List<WebElement> coursesName = driver.findElements(By.cssSelector("div.content h4"));
		int numberOfCourses = coursesName.size();
		Assert.assertTrue(driver.findElement(By.cssSelector("span.menu-heading")).getText().contains(numberOfCourses + " Kết quả"));
		System.out.println(driver.findElement(By.cssSelector("span.menu-heading")).getText());
		for (WebElement course : coursesName) {
			System.out.println("- " + course.getText());
			Assert.assertTrue(course.getText().toLowerCase().contains(keyword.toLowerCase()));
		}
	}
	
	@Test
	public void TC_02_Iframe_Blog() {
		// A (Automationfc)
		driver.get("https://automationfc.com/2020/02/18/training-online-automation-testing/");
		
		// A -> B (Youtube)
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#video-2679-1_youtube_iframe")));
		driver.findElement(By.cssSelector("button.ytp-large-play-button")).click();
		sleepInSecond(5);
		
		// B -> A
		driver.switchTo().defaultContent();
		
		// A -> C (Facebook)
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fb-page iframe")));
		String autoFBLikes = driver.findElement(By.xpath("//div[contains(text(), 'likes')]")).getText();
		System.out.println("Number of likes on Automationfc's Facebook page: " + autoFBLikes);
		
		// C -> A
		driver.switchTo().defaultContent();
		Assert.assertTrue(driver.findElement(By.cssSelector("h3[id*='fullstack-selenium-webdriver-framework-in-java']")).isDisplayed());
	}

	@Test
	public void TC_03_Frame_HDFC() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// Switch vào frame
		driver.switchTo().frame("login_page");
		
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("frame");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(1);
		
		// Verify password textbox is displayed
		Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}