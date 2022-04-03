package webdriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_P5_Explicit {
	WebDriver driver;
	
	// Wait rõ ràng: với các điều kiện/ trạng thái cụ thể
	WebDriverWait explicitWait;
	
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;
	
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldText = By.cssSelector("div#finish>h4");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new ChromeDriver();	
	}

	@Test
	public void TC_01_Invisible() {	
		explicitWait = new WebDriverWait(driver, 30);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Loading icon biến mất sau 30s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Visible() {	
		explicitWait = new WebDriverWait(driver, 50);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.cssSelector("div#start>button")).click();

		// "Hello World!" hiển thị sau 30s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloWorldText));
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}

	@Test
	public void TC_03_Number() {	
		explicitWait = new WebDriverWait(driver, 30);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// "Hello World!" element = 1
		explicitWait.until(ExpectedConditions.numberOfElementsToBe(helloWorldText, 1));
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}
	
	@Test
	public void TC_04_Ajax_Loading() {	
		explicitWait = new WebDriverWait(driver, 20);
		
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		// Accept Cookies
		List<WebElement> cookieBanner = driver.findElements(By.cssSelector("div#onetrust-group-container"));
		if (cookieBanner.size() > 0) {
			explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#onetrust-accept-btn-handler"))).click();
		}
	
		// Wait cho Date Picker xuất hiện
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_Panel1")));
		
		By selectedDateTextBy = By.cssSelector("span#ctl00_ContentPlaceholder1_Label1");
		Assert.assertEquals(driver.findElement(selectedDateTextBy).getText(), "No Selected Dates to display.");
		
		// Date Format
		SimpleDateFormat formatDate = new SimpleDateFormat("d");
		String date = formatDate.format(new Date());
		SimpleDateFormat formatFull = new SimpleDateFormat("EEEE',' MMMM d',' yyyy");
		String dateFull = formatFull.format(new Date());
		
		// Wait cho ngày hôm nay có thể click và click vào
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + date + "']"))).click();
		
		// Wait cho Loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1']>div.raDiv")));
		
		// Verify ngày được update
		Assert.assertEquals(driver.findElement(selectedDateTextBy).getText(), dateFull);
		
		// Wait cho ngày selected thành công (visible)
		WebElement dateSelected = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='" + date + "']")));
		
		// Verify ngày được chọn
		Assert.assertTrue(dateSelected.isDisplayed());
	}
	
	@Test
	public void TC_05_Upload_Files( ) {	
		// Image Name
		String cakeImage = "Cake.jpg";
		String dogImage = "Dog.jpg";
		String flowerImage = "Flower.jpg";

		// Image location
		String uploadFolderLocation =  projectPath + separatorChar + "uploadFiles" + separatorChar;
		String cakeImageLocation = uploadFolderLocation + cakeImage;
		String dogImageLocation = uploadFolderLocation + dogImage;
		String flowerImageLocation = uploadFolderLocation + flowerImage;
		
		explicitWait = new WebDriverWait(driver, 60);
		
		driver.get("https://gofile.io/uploadFiles");
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("img.animation__wobble")));
		
		// Load + uploading
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(cakeImageLocation + "\n" + dogImageLocation + "\n"  + flowerImageLocation);
		
		// Wait cho file được upload thành công
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress"))));
		
		// Wait cho successfully text visible
		WebElement successText = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		Assert.assertTrue(successText.isDisplayed());
		
		// Click on Download page
		driver.findElement(By.cssSelector("a#rowUploadSuccess-downloadPage")).click();
		
		// Switch qua tab mới
		String currentTab = driver.getWindowHandle();
		Set<String> allTabIDs = driver.getWindowHandles();
		for (String id : allTabIDs) {
			if (!id.equals(currentTab)) {
				driver.switchTo().window(id);
				break;
			}
		}
		
		// Wait cho thông tin ảnh hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#rowFolder-tableContent")));
		
		// Verify
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + cakeImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + dogImage + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rowFolder-tableContent']//span[text()='" + flowerImage + "']")).isDisplayed());
		Assert.assertEquals(driver.findElements(By.cssSelector("div#rowFolder-table button#contentId-download")).size(), 3);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}