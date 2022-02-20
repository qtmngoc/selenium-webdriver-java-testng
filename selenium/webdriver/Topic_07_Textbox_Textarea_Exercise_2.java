package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Textbox_Textarea_Exercise_2 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	By firstNameTextboxBy = By.id("personal_txtEmpFirstName");
	By lastNameTextboxBy = By.id("personal_txtEmpLastName");
	By employeeIdTextboxBy = By.id("personal_txtEmployeeId");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Add_Employee() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		// Textbox
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		
		// Button
		driver.findElement(By.id("btnLogin")).click();
		sleepInSecond(3);
		
		// At Dashboard page: 'Add Employee' sub menu link is not displayed
		Assert.assertFalse(driver.findElement(By.cssSelector("a#menu_pim_addEmployee")).isDisplayed());
		
		// Open 'Add Employee' page
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
		
		// At Add Employee page: 'Add Employee' sub menu link is displayed
		Assert.assertTrue(driver.findElement(By.cssSelector("a#menu_pim_addEmployee")).isDisplayed());
		
		// Declaring and Initializing Variables
		String firstName = "Textbox";
		String lastName = "Textarea Two";
		
		// Enter to 'First Name/ Last Name'
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		
		String employeeID = driver.findElement(By.id("employeeId")).getAttribute("value");
		
		// Click 'Save'
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(3);
		
		// Verify 'First Name/ Last Name/ EmployeeID' textboxes are disabled
		Assert.assertFalse(driver.findElement(firstNameTextboxBy).isEnabled());
		Assert.assertFalse(driver.findElement(lastNameTextboxBy).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIdTextboxBy).isEnabled());
		
		// Verify 'First Name/ Last Name/ EmployeeID' value matching with input value
		Assert.assertEquals(driver.findElement(firstNameTextboxBy).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNameTextboxBy).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(employeeIdTextboxBy).getAttribute("value"), employeeID);
	}
	

	@Test
	public void TC_02_Edit_Employee() {
		// Click 'Edit' Button
		driver.findElement(By.id("btnSave")).click();
				
		// Verify 'First Name/ Last Name/ EmployeeID' textboxes are enabled
		Assert.assertTrue(driver.findElement(firstNameTextboxBy).isEnabled());
		Assert.assertTrue(driver.findElement(lastNameTextboxBy).isEnabled());
		Assert.assertTrue(driver.findElement(employeeIdTextboxBy).isEnabled());
		
		String editFirstName = "Exercise";
		String editLastName = "Two";
				
		// Edit 'First Name/ Last Name'
		driver.findElement(firstNameTextboxBy).clear();
		driver.findElement(firstNameTextboxBy).sendKeys(editFirstName);
		driver.findElement(lastNameTextboxBy).clear();
		driver.findElement(lastNameTextboxBy).sendKeys(editLastName);
				
		// Click 'Save' Button
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(3);
				
		// Verify 'First Name/ Last Name/ EmployeeID' textboxes are disabled
		Assert.assertFalse(driver.findElement(firstNameTextboxBy).isEnabled());
		Assert.assertFalse(driver.findElement(lastNameTextboxBy).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIdTextboxBy).isEnabled());
				
		// Verify 'First Name/ Last Name/ EmployeeID' value matching with edit value
		Assert.assertEquals(driver.findElement(firstNameTextboxBy).getAttribute("value"), editFirstName);
		Assert.assertEquals(driver.findElement(lastNameTextboxBy).getAttribute("value"), editLastName);		
	}

	@Test
	public void TC_03_Immigration() {
		// Click to 'Immigration' tab
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		
		// Click 'Add' button
		driver.findElement(By.id("btnAdd")).click();
		
		String immigrationNumber = "325697450";
		String immigrationNumberComments = "Update\nPassport\nID";
		
		// Enter to 'Immigration' number textbox
		driver.findElement(By.id("immigration_number")).sendKeys(immigrationNumber);
		driver.findElement(By.cssSelector("textarea#immigration_comments")).sendKeys(immigrationNumberComments);		
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(3);
		
		driver.findElement(By.xpath("//td[@class='document']/a[text()='Passport']")).click();
		
		// Verify
		// Attribute 'value' bị ẩn. Chrome: tìm trong tab Properties
		Assert.assertEquals(driver.findElement(By.id("immigration_number")).getAttribute("value"), immigrationNumber);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea#immigration_comments")).getAttribute("value"), immigrationNumberComments);		
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
