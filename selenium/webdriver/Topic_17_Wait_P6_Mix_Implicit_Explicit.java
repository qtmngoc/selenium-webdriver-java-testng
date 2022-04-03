package webdriver;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Topic_17_Wait_P6_Mix_Implicit_Explicit {
	WebDriver driver;
	WebDriverWait explicitWait;
	
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new FirefoxDriver();	
	}

	public void TC_01_Element_Found() {
		System.out.println("1. Element Found");
		driver.get("https://www.facebook.com/");
		
		By emailIDBy = By.id("email");
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("Start Implicit: " + getTimeNow());
		driver.findElement(emailIDBy).isDisplayed();
		System.out.println("End Implicit: " + getTimeNow());
		
		explicitWait = new WebDriverWait(driver, 15);
		System.out.println("Start Explicit: " + getTimeNow());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		System.out.println("End Explicit: " + getTimeNow());
		
		// => Pass after 0s.
	}
	
	public void TC_02_Element_Not_Found_Only_Implicit() {
		System.out.println("2. Element Not Found - Only Implicit");
		driver.get("https://www.facebook.com/");
		
		By emailIDBy = By.id("implicit");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Start: " + getTimeNow());
		
		try {
			driver.findElement(emailIDBy).isDisplayed();
		} catch (Exception e) {}
		// => Fail after 5s: NoSuchElementException: Unable to locate element.
		
		System.out.println("End: " + getTimeNow());
	}

	public void TC_03_Element_Not_Found_Only_Explicit_By() {
		System.out.println("3. Element Not Found - Only Explicit - By");
		driver.get("https://www.facebook.com/");
		
		By emailIDBy = By.id("explicitBy");
		
		explicitWait = new WebDriverWait(driver, 7);
		System.out.println("Start: " + getTimeNow());
		
		try {
			// Wrapper việc findElement trong hàm visibility
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		} catch (Exception e) {}
		// => Fail after 7s: TimeoutException: Expected condition failed: waiting for visibility of element located by
		
		System.out.println("End: " + getTimeNow());
	}
	
	public void TC_04_Element_Not_Found_Only_Explicit_WebElement() {
		System.out.println("4. Element Not Found - Only Explicit - WebElement");
		driver.get("https://www.facebook.com/");
		
		explicitWait = new WebDriverWait(driver, 10);
		System.out.println("Start: " + getTimeNow());
		
		try {
			// Chạy findElement trước
			// Pass thì mới xét visibility
			// Fail thì không xét visibility
			explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("explicitWebElement"))));
		} catch (Exception e) {}
		// => Fail after 0s: NoSuchElementException: Unable to locate element (implicit wait = 0)
		
		System.out.println("End: " + getTimeNow());
	}
	
	public void TC_05_Element_Not_Found_Implicit_Explicit() {
		System.out.println("5. Element Not Found - Implicit & Explicit");
		driver.get("https://www.facebook.com/");
		
		By emailIDBy = By.id("mix");
		
		// Implicit Wait chạy độc lập, không bị ảnh hưởng bởi Wait khác.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Start Implicit: " + getTimeNow());
		try {
			driver.findElement(emailIDBy).isDisplayed();
		} catch (Exception e) {e.printStackTrace();}
		// => Fail after 10s.
		
		System.out.println("End Implicit: " + getTimeNow());
		
		// Hàm visibility của Explicit có phụ thuộc vào Implicit Wait.
		explicitWait = new WebDriverWait(driver, 5);
		System.out.println("Start Explicit: " + getTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		} catch (Exception e) {e.printStackTrace();}
		// => Fail after 10s.
		
		System.out.println("End Explicit: " + getTimeNow());	
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getTimeNow() {
		Date date = new Date();
		return date.toString();
	}
}