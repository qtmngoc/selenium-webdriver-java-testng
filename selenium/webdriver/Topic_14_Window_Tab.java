package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Window_Tab {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Alert alert;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();	
		jsExecutor = (JavascriptExecutor) driver;
	
		explicitWait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_More_Than_One_Window_Or_Tab() {
		// Page A: parent
		driver.get("https://automationfc.github.io/basic-form/");
		String parentPageID = driver.getWindowHandle();
		
		// Mở Google Tab
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		
		// Driver switch qua Google tab
		switchToTabByTitle("Google");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		
		// Switch về parent tab
		switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		// Mở Facebook tab
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		switchToTabByTitle("Facebook – log in or sign up");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
		
		switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		// Mở Tiki tab
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		switchToTabByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		Assert.assertEquals(driver.getCurrentUrl(), "https://tiki.vn/");
		
		switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		// Mở Lazada tab
		driver.findElement(By.xpath("//a[text()='LAZADA']")).click();
		switchToTabByTitle("Shopping online - Buy online on Lazada.vn");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.lazada.vn/");
		
		switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/");
		
		Assert.assertTrue(closeAllExceptParent(parentPageID));
	}
	
	@Test
	public void TC_02_Kyna() {
		driver.get("https://kyna.vn/");
		String kynaID = driver.getWindowHandle();
		
	/*	By salePopupBy = By.cssSelector("div.fancybox-inner img");	
		List<WebElement> salePopup = driver.findElements(salePopupBy);	
		if (salePopup.size() > 0) {
			driver.findElement(By.cssSelector("a.fancybox-close")).click();
			sleepInSecond(1);
		} */

		// Switch to facebook link
		driver.findElement(By.cssSelector("div.hotline img[alt='facebook']")).click();
		switchToTabByTitle("Kyna.vn - Home | Facebook");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
				
		switchToTabByTitle("Kyna.vn - Học online cùng chuyên gia");
				
		// Switch to youtube link
		driver.findElement(By.cssSelector("div.hotline img[alt='youtube']")).click();
		switchToTabByTitle("Kyna.vn - YouTube");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
				
		switchToTabByTitle("Kyna.vn - Học online cùng chuyên gia");	
		Assert.assertEquals(driver.getCurrentUrl(), "https://kyna.vn/");

		closeAllExceptParent(kynaID);
	}
	
	@Test
	public void TC_03_Techpanda() {
		driver.get("http://live.techpanda.org/");	
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		
		// Click on Add to compare (Sony Xperia)
		driver.findElement(By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
		
		// Click on Add to compare (Samsung Galaxy)
		driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");
		
		// Click on Compare button
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
	
		// Switch to Compare window
		switchToTabByID(driver.getWindowHandle());
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/catalog/product_compare/index/");
		driver.findElement(By.cssSelector("button[title='Close Window']")).click();
		
		switchToTabByTitle("Mobile");
		
		// Click Clear All, accept alert and verify message.
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "Are you sure you would like to remove all products from your comparison?");
		alert.accept();

		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The comparison list was cleared.");
	}
	
	@Test
	public void TC_04_Cambridge( ) {
		driver.get("https://dictionary.cambridge.org/vi/");
		
		// Click on Đăng nhập
		driver.findElement(By.cssSelector("span.cdo-login-button")).click();
		
		// Switch to Log in window
		switchToTabByID(driver.getWindowHandle());
		
		// Click on Log in button
		driver.findElement(By.cssSelector("input[value='Log in']")).click();
		
		// Verify error messages
		Assert.assertEquals(driver.findElement(By.cssSelector("div#login_content span[data-bound-to='loginID']")).getText(), "This field is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#login_content span[data-bound-to='password']")).getText(), "This field is required");
		
		driver.findElement(By.cssSelector("div#login_content input[name='username']")).sendKeys("automationfc.com@gmail.com");
		driver.findElement(By.cssSelector("div#login_content input[name='password']")).sendKeys("Automation000***");
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input[value='Log in']")).click();
		sleepInSecond(1);
		
		// Verify Login success
		switchToTabByTitle("Cambridge Dictionary | Từ điển tiếng Anh, Bản dịch & Từ điển từ đồng nghĩa");
		Assert.assertEquals(driver.findElement(By.cssSelector("span.cdo-username")).getText(), "Automation FC");
	}
	
	// Chỉ đúng cho TH có 2 tabs/ windows
	public void switchToTabByID(String currentTabID) {
		// Lấy ra ID của tất cả tabs/windows đang mở
		Set<String> allTabIDs =  driver.getWindowHandles();
		
		for (String id : allTabIDs) {
			// ID nào khác với currentTabID thì switch qua
			if(!id.equals(currentTabID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}

	// Đúng cho mọi trường hợp
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
	
	// Đóng tất cả tabs/ windows ngoại trừ parent
	public boolean closeAllExceptParent(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		
		for (String runWindows : allWindows) {
			if(!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		
		driver.switchTo().window(parentID);
		if (driver.getWindowHandles().size() == 1) 
			return true;
		else
			return false;	
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