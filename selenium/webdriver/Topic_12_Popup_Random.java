package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Popup_Random {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();	
		
		jsExecutor = (JavascriptExecutor) driver;
		
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Random_Popup_In_DOM_KMplayer() {
		driver.get("https://kmplayer.com/home");	
		
		// Popup có hiển thị hay không thì element luôn có trong DOM
		WebElement supportHomePopup = driver.findElement(By.cssSelector("img#support-home"));
		
		// Nếu popup hiển thị thì close hoặc actions vào popup
		if (supportHomePopup.isDisplayed()) {
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("area#btn-r")));
			sleepInSecond(1);
		}
		
		// Click on PC 64X -> hiện ra layer popup
		driver.findElement(By.xpath("//div[@class='mv_btn']//a[text()='PC 64X']")).click();
		
		WebElement layerPopup = driver.findElement(By.cssSelector("img.layer-popup"));
		sleepInSecond(1);
		Assert.assertTrue(layerPopup.isDisplayed());
		
		// Click download
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("area#down-url")));
		sleepInSecond(2);
	}
	
	@Test
	public void TC_02_Random_Popup_In_DOM_Vnk() {
		driver.get("https://vnk.edu.vn/");	
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#tve_editor")));
		
		// Nếu popup hiển thị thì close hoặc actions vào popup
		if (driver.findElement(By.cssSelector("div#tve_editor")).isDisplayed()) {
			
			// Click on Group
			driver.findElement(By.xpath("//span[text()='Group Kĩ Sư PCCC']")).click();	
			switchToTabByTitle("Zalo - GROUP KỸ SƯ PCCC VIỆT NAM");
			Assert.assertTrue(driver.findElement(By.cssSelector("div.pduhumo")).getText().contains("PCCC"));
			
			// Quay về VNK
			switchToTabByTitle("VNK EDU | Trung tâm đào tạo cơ điện, plc hàng đầu Việt Nam");

			// Close popup
			driver.findElement(By.cssSelector("div.tcb-icon-display")).click();
			sleepInSecond(1);
			
			Assert.assertFalse(driver.findElement(By.cssSelector("div#tve_editor")).isDisplayed());
		}
		
		// Close iframe Messenger
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[data-testid='dialog_iframe']")));
		driver.findElement(By.cssSelector("div[aria-label='đóng']")).click();
		driver.switchTo().defaultContent();
		
		// Click on Liên hệ
		driver.findElement(By.cssSelector("a[title='Liên hệ']")).click();
		Assert.assertEquals(driver.getTitle(), "Liên hệ | VNK EDU");
	}

	@Test
	public void TC_03_Random_Popup_Not_In_DOM_Dehieu() {
		driver.get("https://dehieu.vn/");
		
		// Element của popup chỉ xuất hiện khi popup hiển thị
		List<WebElement> couponPopup = driver.findElements(By.cssSelector("div.popup-content"));
		
		// Nếu popup hiển thị thì size của element>0
		if (couponPopup.size() > 0) {
			driver.findElement(By.cssSelector("button#close-popup")).click();
			sleepInSecond(1);
		}
		
		driver.findElement(By.xpath("//h4[text()='Khóa học thiết kế hệ thống M&E - Tòa nhà']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(), 'thiết kế điện tòa nhà')]")).isDisplayed());
	}
	
	public void switchToTabByTitle(String expectedTitle) {
		Set<String> allTabIDs =  driver.getWindowHandles();
		
		for (String id : allTabIDs) {
			// Switch vào từng tab rồi lấy title kiểm tra
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			
			// Nếu bằng với expectedTitle thì thoát
			if (actualTitle.equals(expectedTitle)) {
				break;
			}
		}
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