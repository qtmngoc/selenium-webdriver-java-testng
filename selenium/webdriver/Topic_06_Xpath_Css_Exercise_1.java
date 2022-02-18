package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_Xpath_Css_Exercise_1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	By fullNameTextboxBy = By.id("txtFirstname"); 
	By emailTextboxBy = By.id("txtEmail"); 
	By confirmEmailTextboxBy = By.id("txtCEmail"); 
	By passwordTextboxBy = By.id("txtPassword");
	By confirmPasswordTextboxBy = By.id("txtCPassword");
	By phoneTextboxBy = By.id("txtPhone");
	By registerButtonBy = By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']");
	
	By firstNameErrorMessage = By.id("txtFirstname-error");
	By emailErrorMessage = By.id("txtEmail-error");
	By confirmEmailErrorMessage = By.id("txtCEmail-error");
	By passwordErrorMessage = By.id("txtPassword-error");
	By confirmPasswordErrorMessage = By.id("txtCPassword-error");
	By phoneErrorMessage = By.id("txtPhone-error");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");	
	}

	@Test
	public void Register_01_Empty_Data() {
		// Đảm bảo các field này luôn rỗng. Tránh trường hợp data có default value.
		driver.findElement(fullNameTextboxBy).sendKeys("");
		driver.findElement(emailTextboxBy).sendKeys("");
		driver.findElement(confirmEmailTextboxBy).sendKeys("");
		driver.findElement(passwordTextboxBy).clear();
		driver.findElement(confirmPasswordTextboxBy).clear();
		driver.findElement(phoneTextboxBy).clear();
		driver.findElement(registerButtonBy).click();
	
		Assert.assertEquals(driver.findElement(firstNameErrorMessage).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(emailErrorMessage).getText(), "Vui lòng nhập email");		
		Assert.assertEquals(driver.findElement(confirmEmailErrorMessage).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(passwordErrorMessage).getText(), "Vui lòng nhập mật khẩu");		
		Assert.assertEquals(driver.findElement(confirmPasswordErrorMessage).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(phoneErrorMessage).getText(), "Vui lòng nhập số điện thoại.");		
	}

	@Test
	public void Register_02_Invalid_Email() {
		driver.findElement(fullNameTextboxBy).sendKeys("Test Case 2");
		driver.findElement(emailTextboxBy).sendKeys("TC2@TC2@gmail.com");
		driver.findElement(confirmEmailTextboxBy).sendKeys("TC2@TC2@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("222222");
		driver.findElement(confirmPasswordTextboxBy).sendKeys("222222");
		driver.findElement(phoneTextboxBy).sendKeys("0987654321");
		driver.findElement(registerButtonBy).click();
		
		Assert.assertEquals(driver.findElement(emailErrorMessage).getText(), "Vui lòng nhập email hợp lệ");	
		// TH này confirmEmail bị lỗi, vì nhập confirm giống với ô email thì sẽ ko hiển thị lỗi
		// Assert.assertEquals(driver.findElement(confirmEmailErrorMessage).getText(), "Email nhập lại không đúng");
		Assert.assertEquals(driver.findElement(confirmEmailErrorMessage).getText(), "Vui lòng nhập email hợp lệ");
	}
	
	@Test
	public void Register_03_Incorrect_Confirm_Email() {
		driver.findElement(fullNameTextboxBy).sendKeys("Test Case 3");
		driver.findElement(emailTextboxBy).sendKeys("TC3@TC3@gmail.com");
		driver.findElement(confirmEmailTextboxBy).sendKeys("TC3@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("333333");
		driver.findElement(confirmPasswordTextboxBy).sendKeys("33333");
		driver.findElement(phoneTextboxBy).sendKeys("0987654321");
		driver.findElement(registerButtonBy).click();
		
		Assert.assertEquals(driver.findElement(confirmEmailErrorMessage).getText(), "Email nhập lại không đúng");
	}
	
	@Test
	public void Register_04_Invalid_Password() {
		driver.findElement(fullNameTextboxBy).sendKeys("Test Case 4");
		driver.findElement(emailTextboxBy).sendKeys("TC4@gmail.com");
		driver.findElement(confirmEmailTextboxBy).sendKeys("TC4@gmail@com");
		driver.findElement(passwordTextboxBy).sendKeys("444");
		driver.findElement(confirmPasswordTextboxBy).sendKeys("444");
		driver.findElement(phoneTextboxBy).sendKeys("0987654321");
		driver.findElement(registerButtonBy).click();
		
		Assert.assertEquals(driver.findElement(passwordErrorMessage).getText(), "Mật khẩu phải có ít nhất 6 ký tự");	
		Assert.assertEquals(driver.findElement(confirmPasswordErrorMessage).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
	}
	
	@Test
	public void Register_05_Incorrect_Confirm_Password() {
		driver.findElement(fullNameTextboxBy).sendKeys("Test Case 5");
		driver.findElement(emailTextboxBy).sendKeys("TC5@gmail.com");
		driver.findElement(confirmEmailTextboxBy).sendKeys("TC5@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("555555");
		driver.findElement(confirmPasswordTextboxBy).sendKeys("444636");
		driver.findElement(phoneTextboxBy).sendKeys("0987654321");
		driver.findElement(registerButtonBy).click();
			
		Assert.assertEquals(driver.findElement(confirmPasswordErrorMessage).getText(), "Mật khẩu bạn nhập không khớp");
	}
	
	@Test
	public void Register_06_Invalid_Phone() {
		driver.findElement(fullNameTextboxBy).sendKeys("Test Case 6");
		driver.findElement(emailTextboxBy).sendKeys("TC6@gmail.com");
		driver.findElement(confirmEmailTextboxBy).sendKeys("TC6@gmail.com");
		driver.findElement(passwordTextboxBy).sendKeys("666666");
		driver.findElement(confirmPasswordTextboxBy).sendKeys("666666");
		
		driver.findElement(phoneTextboxBy).sendKeys("09876543");
		driver.findElement(registerButtonBy).click();	
		Assert.assertEquals(driver.findElement(phoneErrorMessage).getText(), "Số điện thoại phải từ 10-11 số.");
		
		driver.findElement(phoneTextboxBy).clear();
		driver.findElement(phoneTextboxBy).sendKeys("0897654321");
		driver.findElement(registerButtonBy).click();
		Assert.assertEquals(driver.findElement(phoneErrorMessage).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
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
