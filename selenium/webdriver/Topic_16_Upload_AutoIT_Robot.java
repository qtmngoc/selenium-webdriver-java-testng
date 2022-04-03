package webdriver;

import java.io.File;
import java.io.IOException;
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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Topic_16_Upload_AutoIT_Robot {
	WebDriver driver;
	WebElement element;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	// Return char "\\" if OS is Window, return "/" if OS is MAC
	String separatorChar = File.separator;
	
	// Folder location
	String uploadFolderLocation =  projectPath + separatorChar + "uploadFiles" + separatorChar;
	String autoITFolderLocation = projectPath + separatorChar + "autoIT" + separatorChar;
	
	// Image Name
	String apiImage = "API.png";
	String appiumImage = "Appium.png";
	String autoTestImage = "ATLM.png";
	String seleniumImage = "Selenium.png";

	// Image location
	String apiImageLocation = uploadFolderLocation + apiImage;
	String appiumImageLocation = uploadFolderLocation + appiumImage;
	String autoTestImageLocation = uploadFolderLocation + autoTestImage;
	String seleniumImageLocation = uploadFolderLocation + seleniumImage;
	
	// AutoIT script locator
	String singleFirefox = autoITFolderLocation + "uploadOne_firefox.exe";
	String singleChromeEdge = autoITFolderLocation + "uploadOne_chrome_edge.exe";
	String multipleFirefox = autoITFolderLocation + "uploadMultiple_firefox.exe";
	String multipleChromeEdge = autoITFolderLocation + "uploadMultiple_chrome_edge.exe";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new EdgeDriver();
		
		jsExecutor = (JavascriptExecutor) driver;
		
		explicitWait = new WebDriverWait(driver, 20);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Upload_By_AutoIT() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		// Load one file per time
		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		if (driver.toString().contains("Chrome") || driver.toString().contains("Edge")) {
			Runtime.getRuntime().exec(new String[] { singleChromeEdge, autoTestImageLocation });
		} else {
			Runtime.getRuntime().exec(new String[] { singleFirefox, autoTestImageLocation });
		}
		sleepInSecond(5);
		
		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		if (driver.toString().contains("Chrome") || driver.toString().contains("Edge")) {
			Runtime.getRuntime().exec(new String[] { singleChromeEdge, appiumImageLocation });
		} else {
			Runtime.getRuntime().exec(new String[] { singleFirefox, appiumImageLocation });
		}
		sleepInSecond(5);
		
		// Load multiple files
		driver.findElement(By.cssSelector("span.fileinput-button")).click();
		if (driver.toString().contains("Chrome") || driver.toString().contains("Edge")) {
			Runtime.getRuntime().exec(new String[] { multipleChromeEdge, apiImageLocation, seleniumImageLocation });
		} else {
			Runtime.getRuntime().exec(new String[] { multipleFirefox, apiImageLocation, seleniumImageLocation });
		}
		sleepInSecond(5);
		
		// Verify Load Success
		List<WebElement> fileName = driver.findElements(By.cssSelector("p.name"));
		Assert.assertEquals(fileName.size(), 4);
		
		// Upload all file
		List<WebElement> uploadButtons = driver.findElements(By.xpath("//span[text()='Start']"));
		for (WebElement start : uploadButtons) {
			start.click();
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']")));
			sleepInSecond(1);
		}
		
		// Verify Uploaded Success
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='" + apiImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='" + appiumImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='" + autoTestImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='" + seleniumImage + "']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Upload_By_Java_Robot() throws AWTException {
		driver.get("https://formstone.it/components/upload/demo/");
		
		// Basic
		uploadFileByJavaRobot(apiImageLocation, "div#example-0 div.fs-upload-element");
		uploadFileByJavaRobot(autoTestImageLocation, "div#example-0 div.fs-upload-element");
		uploadFileByJavaRobot(appiumImageLocation, "div#example-0 div.fs-upload-element");
		uploadFileByJavaRobot(seleniumImageLocation, "div#example-0 div.fs-upload-element");
		sleepInSecond(1);
		
		// Verify Uploaded Success
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-0']//ol[@class='filelist complete']//span[text()='" + apiImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-0']//ol[@class='filelist complete']//span[text()='" + appiumImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-0']//ol[@class='filelist complete']//span[text()='" + autoTestImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='example-0']//ol[@class='filelist complete']//span[text()='" + seleniumImage + "']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void uploadFileByJavaRobot(String imageLocation, String cssLocator) throws AWTException {
		// Copy file paths to Clipboard
		StringSelection select = new StringSelection(imageLocation);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
		
		// Click on "Add Files" to Open File Dialog
		driver.findElement(By.cssSelector(cssLocator)).click();
		sleepInSecond(1);
		
		Robot robot = new Robot();
		
		// Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		// CTRL + V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		sleepInSecond(1);
		
		// Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		sleepInSecond(1);
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}