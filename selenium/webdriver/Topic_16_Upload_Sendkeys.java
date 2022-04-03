package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Upload_Sendkeys {
	WebDriver driver;
	WebElement element;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	// Return char "\\" if OS is Window, return "/" if OS is MAC
	String separatorChar = File.separator;
	
	// Image Name
	String apiImage = "API.png";
	String appiumImage = "Appium.png";
	String autoTestImage = "ATLM.png";
	String seleniumImage = "Selenium.png";

	// Image location
	String uploadFolderLocation =  projectPath + separatorChar + "uploadFiles" + separatorChar;
	String apiImageLocation = uploadFolderLocation + apiImage;
	String appiumImageLocation = uploadFolderLocation + appiumImage;
	String autoTestImageLocation = uploadFolderLocation + autoTestImage;
	String seleniumImageLocation = uploadFolderLocation + seleniumImage;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new EdgeDriver();
		
		explicitWait = new WebDriverWait(driver, 20);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Upload_Ufile() {
		driver.get("https://ufile.io/");
		
		By uploadFileBy = By.cssSelector("input[type='file']");
		
		// Load multiple files
		driver.findElement(uploadFileBy).sendKeys(apiImageLocation + "\n" + appiumImageLocation + "\n" + autoTestImageLocation + "\n" + seleniumImageLocation);
		
		// Verify Load Success
		WebElement sucessMessage = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.success_message")));
		Assert.assertTrue(sucessMessage.isDisplayed());
		
		// Folder URL
		String folerUrl = driver.findElement(By.cssSelector("div#share-file")).getAttribute("data-url");
		driver.get(folerUrl);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("span[title='" + apiImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("span[title='" + appiumImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("span[title='" + autoTestImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("span[title='" + seleniumImage + "']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Upload_Gofile() {
		driver.get("https://gofile.io/?t=uploadFiles");
		
		// Load multiple files per time
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(apiImageLocation + "\n" + appiumImageLocation + "\n"  + autoTestImageLocation + "\n" + seleniumImageLocation);
		
		// Verify Uploaded Success
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.callout-success>h5")));
		Assert.assertTrue(driver.findElement(By.xpath("//h5[contains(text(),'successfully uploaded')]")).isDisplayed());
		
		driver.findElement(By.cssSelector("button#rowUploadSuccess-showFiles")).click();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + apiImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + appiumImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + autoTestImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + seleniumImage + "']")).isDisplayed());
		
		driver.findElement(By.cssSelector("h4.text-truncate button.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("h4.text-truncate a.contentInfo")).click();
		sleepInSecond(1);
		
		// Info popup
		Assert.assertTrue(driver.findElement(By.cssSelector("div.swal2-backdrop-show")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Contents')]/following-sibling::div[1]")).getText(), "4");
		driver.findElement(By.cssSelector("button.swal2-confirm")).click();
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