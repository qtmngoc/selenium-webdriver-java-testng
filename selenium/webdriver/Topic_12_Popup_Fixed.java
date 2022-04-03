package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Popup_Fixed {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		// Để chờ trạng thái của element
		explicitWait = new WebDriverWait(driver, 30);
	
		// Để tìm element
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Fixed_Popup_Eng() {
		driver.get("https://ngoaingu24h.vn/");
		
		By loginPopupBy = By.cssSelector("div#modal-login-v1");		
		Assert.assertFalse(driver.findElement(loginPopupBy).isDisplayed());
		
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(loginPopupBy).isDisplayed());
		
		driver.findElement(By.id("account-input")).sendKeys("fixedpopup1");
		driver.findElement(By.id("password-input")).sendKeys("03032022");
		
		driver.findElement(By.cssSelector("button.btn-login-v1")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
		
		driver.findElement(By.cssSelector("div#modal-login-v1 button.close")).click();
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(loginPopupBy).isDisplayed());
	}

	@Test
	public void TC_02_Fixed_Popup_Bizbooks() {
		driver.get("https://bizbooks.vn/");
		
		By loginPopupBy = By.cssSelector("div#md-signin");
		By registerPopupBy = By.cssSelector("div#md-signup");
		
		// Cả 2 không hiển thị
		Assert.assertFalse(driver.findElement(loginPopupBy).isDisplayed());
		Assert.assertFalse(driver.findElement(registerPopupBy).isDisplayed());
		
		driver.findElement(By.xpath("//span[text()='ĐĂNG NHẬP']")).click();
		driver.findElement(By.xpath("//div[@class='header__elements']//a[text()='Đăng nhập']")).click();
		
		// Chỉ có Login hiển thị
		Assert.assertTrue(driver.findElement(loginPopupBy).isDisplayed());
		Assert.assertFalse(driver.findElement(registerPopupBy).isDisplayed());
		
		driver.findElement(By.cssSelector("div#md-signin input[name='email']")).sendKeys("fixedpopup1@gmail.com");
		driver.findElement(By.cssSelector("div#md-signin input[name='password']")).sendKeys("04032022");
		driver.findElement(By.cssSelector("button.js-btn-member-login")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div#md-signin span.text-danger")).getText(), "Tài khoản không tồn tại");
		
		// Close popup (Không có close button)
		driver.findElement(By.cssSelector("div#md-signin input[name='email']")).sendKeys(Keys.ESCAPE);
		sleepInSecond(1);
		
		Assert.assertFalse(driver.findElement(loginPopupBy).isDisplayed());
		Assert.assertFalse(driver.findElement(registerPopupBy).isDisplayed());
	}
	
	@Test
	public void TC_03_Fixed_Popup_JnT() {
		driver.get("https://jtexpress.vn/");
		
		By homePagePopupBy = By.cssSelector("div#homepage-popup");
		
		// Chờ cho popup hiển thị rồi verify
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(homePagePopupBy));
		Assert.assertTrue(driver.findElement(homePagePopupBy).isDisplayed());
		
		driver.findElement(By.cssSelector("div#homepage-popup button.close")).click();	
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(homePagePopupBy));
		Assert.assertFalse(driver.findElement(homePagePopupBy).isDisplayed());
		
		String billCode = "840000598444";		
		driver.findElement(By.cssSelector("input#billcodes")).sendKeys(billCode);
		driver.findElement(By.xpath("//div[@id='track']//button[text()='Tra cứu vận đơn']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//h5")).getText(), "Mã vận đơn: " + billCode);
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