package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Upload_Headless {
	WebDriver driver;
	WebElement element;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	// Return char "\\" if OS is Window, return "/" if OS is MAC
	String separatorChar = File.separator;
	
	// Image Name
	String cakeImage = "Cake.jpg";
	String dogImage = "Dog.jpg";
	String flowerImage = "Flower.jpg";

	// Image location
	String uploadFolderLocation =  projectPath + separatorChar + "uploadFiles" + separatorChar;
	String cakeImageLocation = uploadFolderLocation + cakeImage;
	String dogImageLocation = uploadFolderLocation + dogImage;
	String flowerImageLocation = uploadFolderLocation + flowerImage;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1920x1080");
		driver = new ChromeDriver(options);
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@Test
	public void Upload_Formstone() {
		driver.get("https://formstone.it/components/upload/demo/");
		
		// Manual Upload
		By uploadFileBy = By.cssSelector("div#example-2 input[type='file']");
		
		// Load one file per time
		driver.findElement(uploadFileBy).sendKeys(cakeImageLocation);
		
		// Load multiple files
		driver.findElement(uploadFileBy).sendKeys(dogImageLocation + "\n" + flowerImageLocation);
		
		// Verify Load Success
		List<WebElement> fileName = driver.findElements(By.cssSelector("div#example-2 ol.queue>li"));
		Assert.assertEquals(fileName.size(), 3);
		
		// Upload all file
		driver.findElement(By.cssSelector("div#example-2 span.start_all")).click();
		
		// Verify Uploaded Success
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-2']//ol[@class='filelist complete']//span[text()='" + cakeImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-2']//ol[@class='filelist complete']//span[text()='" + dogImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-2']//ol[@class='filelist complete']//span[text()='" + flowerImage + "']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}