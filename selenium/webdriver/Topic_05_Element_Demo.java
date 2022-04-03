package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Demo {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01() {
		driver.get("http://live.techpanda.org/index.php/customer/account/create/");
		
		String searchPlaceholder = driver.findElement(By.id("search")).getAttribute("placeholder");
		System.out.println(searchPlaceholder);
		
		String instructionText = driver.findElement(By.cssSelector("p.form-instructions")).getText();
		System.out.println(instructionText);
	}

	@Test
	public void TC_02() {
		driver.get("https://www.facebook.com/");
		
		WebElement loginButton = driver.findElement(By.name("login"));
		System.out.println(loginButton.getCssValue("font-size"));
		System.out.println(loginButton.getCssValue("background-color"));
		System.out.println(loginButton.getCssValue("width"));
		System.out.println(loginButton.getCssValue("font-family"));
	}
	
	@Test
	public void TC_03() {
		driver.get("https://www.facebook.com/");
		
		WebElement loginButton = driver.findElement(By.name("login"));
		String logionButtonTagname = loginButton.getTagName();

		// Đầu ra step này là đầu vào step kia
		loginButton = driver.findElement(By.xpath("//" + logionButtonTagname + "[@data-testid='royal_login_button']"));
		loginButton.click();
	}
	
	@Test
	public void TC_04() {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
		
		driver.findElement(By.id("email")).sendKeys("test@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("464487");
		sleepInSecond(1);
		driver.findElement(By.id("send2")).submit();
	}
	
	@Test
	public void TC_05() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		driver.findElement(By.id("name")).sendKeys("Ngoc Ngoc");
		driver.findElement(By.id("address")).sendKeys("Sai Gon");
		driver.findElement(By.id("email")).sendKeys("test@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123487");
		sleepInSecond(1);
		
		driver.findElement(By.id("address")).submit();
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
