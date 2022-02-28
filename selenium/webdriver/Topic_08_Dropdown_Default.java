package webdriver;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Dropdown_Default {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Rode() {
		driver.get("https://www.rode.com/wheretobuy");
		
		// Chỉ khởi tạo khi element xuất hiện
		Select select = new Select(driver.findElement(By.id("where_country")));
		
		// Ko support multiple select 
		Assert.assertFalse(select.isMultiple());
		
		// Select giá trị Vietnam
		select.selectByVisibleText("Vietnam");
		sleepInSecond(1);
		
		// Verify Vietnam selected
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		
		// Click Search
		driver.findElement(By.id("search_loc_submit")).click();
		sleepInSecond(1);
		
		// Verify have 33 results
		Assert.assertEquals(driver.findElement(By.cssSelector(".result_count>span")).getText(), "33");
		
		// Java Generic
		List<WebElement> storeName = driver.findElements(By.cssSelector("div#search_results div.store_name"));
		
		// Verify tổng số lượng Store Name
		Assert.assertEquals(storeName.size(), 33);
		
		// Print Distributor name
		for (WebElement store : storeName) {
			System.out.println(store.getText());
		}
	}

	@Test
	public void TC_02_NopCommerce() {
		String firstName = "Default";
		String lastName = "Dropdown";
		String date = "22";
		String month = "February";
		String year = "1995";
		String email = "dropdown" + getRandomNumber() + "@hotmail.com";
		String password = "321112";
		
		By firstNameTextBoxBy = By.id("FirstName");
		By lastNameTextBoxBy = By.id("LastName");
		By dateDropdownBy = By.name("DateOfBirthDay");
		By monthDropdownBy = By.name("DateOfBirthMonth");
		By yearDropdownBy = By.name("DateOfBirthYear");
		By emailTextBoxBy = By.id("Email");
		
		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.className("ico-register")).click();
		
		// Number of year options
		Calendar cal = Calendar.getInstance();
		int numberYearOptions = cal.get(Calendar.YEAR) - 1912 + 2;
		
		// Verify number of options in dropdown
		Select dayDropdown = new Select(driver.findElement(dateDropdownBy));
		Select monthDropdown = new Select(driver.findElement(monthDropdownBy));	
		Select yearDropdown = new Select(driver.findElement(yearDropdownBy));
		Assert.assertEquals(dayDropdown.getOptions().size(), 32);
		Assert.assertEquals(monthDropdown.getOptions().size(), 13);
		Assert.assertEquals(yearDropdown.getOptions().size(), numberYearOptions);
		
		driver.findElement(firstNameTextBoxBy).sendKeys(firstName);
		driver.findElement(lastNameTextBoxBy).sendKeys(lastName);
		
		// Select Date, Month, Year
		dayDropdown.selectByVisibleText(date);
		monthDropdown.selectByVisibleText(month);
		yearDropdown.selectByVisibleText(year);
		
		driver.findElement(emailTextBoxBy).sendKeys(email);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");
		
		driver.findElement(By.className("ico-account")).click();
		
		// Verify
		Assert.assertEquals(driver.findElement(firstNameTextBoxBy).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNameTextBoxBy).getAttribute("value"), lastName);
		
		// Đến trang khác nên phải new
		dayDropdown = new Select(driver.findElement(dateDropdownBy));
		Assert.assertEquals(dayDropdown.getFirstSelectedOption().getText(), date);
		
		monthDropdown = new Select(driver.findElement(monthDropdownBy));
		Assert.assertEquals(monthDropdown.getFirstSelectedOption().getText(), month);
		
		yearDropdown = new Select(driver.findElement(yearDropdownBy));
		Assert.assertEquals(yearDropdown.getFirstSelectedOption().getText(), year);
		
		Assert.assertEquals(driver.findElement(emailTextBoxBy).getAttribute("value"), email);
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
