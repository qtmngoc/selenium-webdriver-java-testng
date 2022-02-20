package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Textbox_Textarea_Exercise_1 {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String loginPageUrl, userID, userPassword, customerID;
	String customerName, gender, dateOfBirth, addressInput, addressOutput, city, state, pinNumber, phoneNumber, email, password;
	String editAddressInput, editAddressOutput, editCity, eidtState, editPin, editPhone, editEmail;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		// Ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		loginPageUrl = "https://demo.guru99.com/v4/";
		customerName = "Textbox Textarea One";
		gender = "male";
		dateOfBirth = "1995-04-21";
		addressInput = "242 Los Angeles\nUnited States";
		addressOutput = "242 Los Angeles United States";
		city = "New York";
		state = "California";
		pinNumber = "612693";
		phoneNumber = "0983214362";
		email = "textbox" + getRandomNumber() + "@mail.net";
		password = "123123";

		editAddressInput = "99 Daehakro\nKorean";
		editAddressOutput = "99 Daehakro Korean";
		editCity = "Daejeon";
		eidtState = "Yuseonggu";
		editPin = "222951";
		editPhone = "0428215114";
		editEmail = "textbox" + getRandomNumber() + "@mail.vn";
	}

	@Test
	public void TC_01_Register() {
		driver.get(loginPageUrl);

		driver.findElement(By.xpath("//a[text()='here']")).click();
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("btnLogin")).click();

		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
	}

	@Test
	public void TC_02_Login() {
		driver.get(loginPageUrl);

		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.name("btnLogin")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//marquee")).getText(), "Welcome To Manager's Page of Guru99 Bank");
		Assert.assertEquals(driver.findElement(By.xpath("//tr[@class='heading3']/td")).getText(), "Manger Id : " + userID);
	}

	@Test
	public void TC_03_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		driver.findElement(By.name("name")).sendKeys(customerName);

		WebElement dobTextbox = driver.findElement(By.name("dob"));
		jsExecutor.executeScript("arguments[0].removeAttribute('type')", dobTextbox);
		dobTextbox.sendKeys(dateOfBirth);

		driver.findElement(By.name("addr")).sendKeys(addressInput);
		driver.findElement(By.name("city")).sendKeys(city);
		driver.findElement(By.name("state")).sendKeys(state);
		driver.findElement(By.name("pinno")).sendKeys(pinNumber);
		driver.findElement(By.name("telephoneno")).sendKeys(phoneNumber);
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("sub")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='heading3']")).getText(), "Customer Registered Successfully!!!");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pinNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phoneNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);

		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
	}

	@Test
	public void TC_04_Edit_Customer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();

		// Verify new customer displayed at Edit Customer correctly
		Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
		Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(By.name("dob")).getAttribute("value"), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.name("addr")).getText(), addressInput);
		Assert.assertEquals(driver.findElement(By.name("city")).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(By.name("state")).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(By.name("pinno")).getAttribute("value"), pinNumber);
		Assert.assertEquals(driver.findElement(By.name("telephoneno")).getAttribute("value"), phoneNumber);
		Assert.assertEquals(driver.findElement(By.name("emailid")).getAttribute("value"), email);

		// Clear old data
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("state")).clear();
		driver.findElement(By.name("pinno")).clear();
		driver.findElement(By.name("telephoneno")).clear();
		driver.findElement(By.name("emailid")).clear();
		
		// Edit
		driver.findElement(By.name("addr")).sendKeys(editAddressInput);
		driver.findElement(By.name("city")).sendKeys(editCity);
		driver.findElement(By.name("state")).sendKeys(eidtState);
		driver.findElement(By.name("pinno")).sendKeys(editPin);
		driver.findElement(By.name("telephoneno")).sendKeys(editPhone);
		driver.findElement(By.name("emailid")).sendKeys(editEmail);
		driver.findElement(By.name("sub")).click();
		
		// Verify
		// Assert.assertEquals(driver.findElement(By.xpath("//p[@class='heading3']")).getText(), "Customer details updated Successfully!!!");
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddressOutput);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), eidtState);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editPhone);
		// Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);
		
		// Click OK trong popup
		driver.switchTo().alert().accept();
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();
		
		Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
		Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(By.name("dob")).getAttribute("value"), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.name("addr")).getText(), editAddressInput);
		Assert.assertEquals(driver.findElement(By.name("city")).getAttribute("value"), editCity);
		Assert.assertEquals(driver.findElement(By.name("state")).getAttribute("value"), eidtState);
		Assert.assertEquals(driver.findElement(By.name("pinno")).getAttribute("value"), editPin);
		Assert.assertEquals(driver.findElement(By.name("telephoneno")).getAttribute("value"), editPhone);
		Assert.assertEquals(driver.findElement(By.name("emailid")).getAttribute("value"), editEmail);
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

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
}
