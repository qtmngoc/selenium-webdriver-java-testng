package webdriver;

import java.util.List;
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

public class Topic_09_Radio_Checkbox_Custom {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExcutor;

	@BeforeClass
	public void beforeClass() {
		if (osName.startsWith("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else if (osName.startsWith("Mac")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver_mac");
		} else { // Linux
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver_linux");
		}

		driver = new FirefoxDriver();		
		
		jsExcutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Radio_Custom_Angular() {		
	 /* Case 1: Dùng thẻ input
		Selenium click(); -> Not work: ElementNotInteractableException
		isSelected() -> Work
		
		Case 2: Dùng thẻ span
		Click() -> Work
		isSelected() -> Not Work
		
		Case 3:
		Click() thẻ span: Work
		isSelected thẻ input: Work
		Nhưng phải định nghĩa 2 locator khác nhau => Dễ gây nhầm lẫn
		
		Case 4: Dùng thẻ input
		Click(): Dùng Javascript: Không quan tâm Element ẩn hay hiện
		isSelected(): Dùng thẻ input Work */
		
		driver.get("https://material.angular.io/components/radio/examples");
		By summerCheckboxBy = By.cssSelector("input[value='Summer'");
		By option2CheckboxBy = By.cssSelector("input[value='2'");
		
		clickByJavascript(summerCheckboxBy);
		clickByJavascript(option2CheckboxBy);
		
		Assert.assertTrue(elementIsSelected(summerCheckboxBy));
		Assert.assertTrue(elementIsSelected(option2CheckboxBy));
	}
	
	@Test
	public void TC_02_Radio_Custom_TiemChung() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");	
		By myselfRadioBy = By.cssSelector("input[id='mat-radio-2-input']");
		By myFamilyRadioBy = By.cssSelector("input[id='mat-radio-3-input']");
		
		clickByJavascript(myFamilyRadioBy);
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("input[formcontrolname='registerFullname']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("input[formcontrolname='registerPhoneNumber']")).isDisplayed());
		
		clickByJavascript(myselfRadioBy);
		sleepInSecond(1);
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Assert.assertEquals(driver.findElements(By.cssSelector("input[formcontrolname='registerFullname']")).size(), 0);
		Assert.assertEquals(driver.findElements(By.cssSelector("input[formcontrolname='registerPhoneNumber']")).size(), 0);
	}
	
	@Test
	public void TC_03_Checkbox_Custom_Angular() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		
		By checkedCheckboxBy = By.cssSelector("input#mat-checkbox-1-input");
		By indeterminateCheckboxBy = By.cssSelector("input#mat-checkbox-2-input");
		
		clickByJavascript(checkedCheckboxBy);
		clickByJavascript(indeterminateCheckboxBy);
		
		Assert.assertTrue(elementIsSelected(checkedCheckboxBy));
		Assert.assertTrue(elementIsSelected(indeterminateCheckboxBy));
		
		clickByJavascript(checkedCheckboxBy);
		clickByJavascript(indeterminateCheckboxBy);
		
		Assert.assertFalse(elementIsSelected(checkedCheckboxBy));
		Assert.assertFalse(elementIsSelected(indeterminateCheckboxBy));
	}
	
	@Test
	public void TC_04_Radio_Custom_GoogleDoc() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(1);
		
		By haNoiRadioBy = By.cssSelector("div[aria-label='Hải Phòng']");		
		driver.findElement(haNoiRadioBy).click();		
		Assert.assertEquals(driver.findElement(haNoiRadioBy).getAttribute("aria-checked"), "true");
		
		List<WebElement> checkboxesQuang = driver.findElements(By.cssSelector("div[data-params*='tiền tố là Quảng'] div[aria-label^='Quảng']"));
		System.out.println("Số tỉnh bắt đầu là Quảng = " + checkboxesQuang.size());		
		for (WebElement checkbox : checkboxesQuang) {
			if (!checkbox.isSelected()) {
				checkbox.click();	
				Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
			}
		}
	}
	
	public void clickByJavascript(By by) {
		jsExcutor.executeScript("arguments[0].click();", driver.findElement(by));
	}
	
	public boolean elementIsSelected(By by) {
		if (driver.findElement(by).isSelected()) {
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
