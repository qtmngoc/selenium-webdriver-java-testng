package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_Radio_Checkbox_Default {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExcutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();		
		
		jsExcutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		
		By loginButtonBy = By.cssSelector("button.fhs-btn-login");
		
		// Verify login button is disabled
		Assert.assertFalse(driver.findElement(loginButtonBy).isEnabled());
		
		driver.findElement(By.id("login_username")).sendKeys("button@gmail.com");
		driver.findElement(By.id("login_password")).sendKeys("010322");
		
		// Verify login button is enabled
		Assert.assertTrue(driver.findElement(loginButtonBy).isEnabled());
		
		// Verify background color = Red
		String loginButtonBackgroundColorRgba = driver.findElement(loginButtonBy).getCssValue("background-color");
		System.out.println("RGBA color = " + loginButtonBackgroundColorRgba);
		// Convert qua RGB
		String[] numbers = loginButtonBackgroundColorRgba.replace("rgba(", "").replace(")", "").split(",");
		int r = Integer.parseInt(numbers[0].trim());
		int g = Integer.parseInt(numbers[1].trim());
		int b = Integer.parseInt(numbers[2].trim());
		String loginButtonBackgroundColorRgb = "rgb(" + r + ", " + g + ", " + b + ")";
		System.out.println("RGB color = " + loginButtonBackgroundColorRgb);
		Assert.assertEquals(loginButtonBackgroundColorRgb, "rgb(201, 33, 39)");
		// Convert qua Hexa
		String loginButtonBackgroundColorHexa = (Color.fromString(loginButtonBackgroundColorRgb).asHex()).toUpperCase();
		System.out.println("Hexa color = " + loginButtonBackgroundColorHexa);
		Assert.assertEquals(loginButtonBackgroundColorHexa, "#C92127");
		
		// Refresh page
		driver.navigate().refresh();
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		
		// Remove disabled attribute
		jsExcutor.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(loginButtonBy));
		driver.findElement(loginButtonBy).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), 
				"Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")).getText(), 
				"Thông tin này không thể để trống");
	}

	@Test
	public void TC_02_Radio_Default() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		
		// Accept Cookies
		List<WebElement> cookieBanner = driver.findElements(By.cssSelector("div#onetrust-group-container"));
		if (cookieBanner.size() > 0) {
			driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();
			sleepInSecond(1);
		}
		
		By oneDotEightPetrolRadioBy = By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input");
		Assert.assertFalse(driver.findElement(oneDotEightPetrolRadioBy).isSelected());
		driver.findElement(oneDotEightPetrolRadioBy).click();
		Assert.assertTrue(driver.findElement(oneDotEightPetrolRadioBy).isSelected());
		Assert.assertTrue(driver.findElement(oneDotEightPetrolRadioBy).isEnabled());
		
		By twoDotZeroDieselRadioBy = By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::input");
		driver.findElement(twoDotZeroDieselRadioBy).click();
		Assert.assertFalse(driver.findElement(oneDotEightPetrolRadioBy).isSelected());
		Assert.assertTrue(driver.findElement(twoDotZeroDieselRadioBy).isSelected());
		Assert.assertTrue(driver.findElement(twoDotZeroDieselRadioBy).isEnabled());
		
		// Read only
		By threeDotSixPetrolRadioBy = By.xpath("//label[text()='3.6 Petrol, 191kW']/preceding-sibling::input");
		Assert.assertFalse(driver.findElement(threeDotSixPetrolRadioBy).isEnabled());
	}
	
	@Test
	public void TC_03_Checkbox_Default() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		// Accept Cookies
		List<WebElement> cookieBanner = driver.findElements(By.cssSelector("div#onetrust-group-container"));
		if (cookieBanner.size() > 0) {
			driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();
			sleepInSecond(1);
		}
		
		By readCheckboxBy = By.xpath("//label[text()='Rear side airbags']/preceding-sibling::input");
		By dualCheckboxBy = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		By leatherCheckboxBy = By.xpath("//label[text()='Leather trim']/preceding-sibling::input");
		By towbarCheckboxBy = By.xpath("//label[text()='Towbar preparation']/preceding-sibling::input");

		checkToCheckbox(readCheckboxBy);
		checkToCheckbox(dualCheckboxBy);
		
		// Selected
		Assert.assertTrue(elementIsSelected(readCheckboxBy));
		Assert.assertTrue(elementIsSelected(dualCheckboxBy));
		Assert.assertTrue(elementIsSelected(leatherCheckboxBy));

		// Disabled
		Assert.assertFalse(elementIsEnabled(leatherCheckboxBy));
		Assert.assertFalse(elementIsEnabled(towbarCheckboxBy));
		
		// Deselected
		uncheckToCheckbox(dualCheckboxBy);
		Assert.assertFalse(elementIsSelected(dualCheckboxBy));
		Assert.assertFalse(elementIsSelected(towbarCheckboxBy));
	}
	
	@Test
	public void TC_04_Multiple_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
		System.out.println("Checkbox size = " + checkboxes.size());
		
		// Action - Select
		for (WebElement checkbox : checkboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();	
			}
		}
		
		// Verify - Select
		for (WebElement checkbox : checkboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}
		
		// Action - Deselect
		for (WebElement checkbox : checkboxes) {
			if (checkbox.isSelected()) {
				checkbox.click();	
			}
		}
		
		// Verify - Deselect
		for (WebElement checkbox : checkboxes) {
			Assert.assertFalse(checkbox.isSelected());
		}
	}

	public void checkToCheckbox(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}
	
	public void uncheckToCheckbox(By by) {
		if (driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}
	
	public boolean elementIsSelected(By by) {
		if (driver.findElement(by).isSelected()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean elementIsEnabled(By by) {
		if (driver.findElement(by).isEnabled()) {
			return true;
		} else {
			return false;
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
