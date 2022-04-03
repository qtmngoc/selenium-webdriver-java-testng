package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Dropdown_Custom {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExcutor;
	
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// Wait cho các trạng thái của element: visible, presence, invisible, staleness.
		explicitWait = new WebDriverWait(driver, 15);
		
		jsExcutor = (JavascriptExecutor) driver;
		
		// Wait để tìm element (findElement)
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_jQuery_Select() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		// Speed	
		selectItemInCustomDropdownList("span#speed-button", "ul#speed-menu div", "Slower");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(), "Slower");
		
		// Number
		selectItemInCustomDropdownList("span#number-button", "ul#number-menu div", "17");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(), "17");
		
		// Title
		selectItemInCustomDropdownList("span#salutation-button", "ul#salutation-menu div", "Prof.");
		Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button span.ui-selectmenu-text")).getText(), "Prof.");	
	}
	
	@Test
	public void TC_02_jQuery_Enter() {
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		
		// Effects Default - select
		selectItemInCustomDropdownList("div#default-place", "div#default-place li", "Volkswagen");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#default-place input")).getAttribute("value"), "Volkswagen");
		
		// Effects Slide
		enterToInCustomDropdownList("div#slide-place input", "div#slide-place li", "Porsche");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#slide-place input")).getAttribute("value"), "Porsche");	
		
		// HTML support
		enterToInCustomDropdownList("div#html-place input", "div#html-place li", "Nissan");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#html-place input")).getAttribute("value"), "Nissan");
	}

	@Test
	public void TC_03_React_Select() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

		selectItemInCustomDropdownList("div.dropdown", "div.item>span.text", "Stevie Feliciano");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
		
		selectItemInCustomDropdownList("div.text", "div.item>span.text", "Justen Kitsune");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Justen Kitsune");
		
		selectItemInCustomDropdownList("i.dropdown", "div.item>span.text", "Elliot Fu");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Elliot Fu");
	}

	@Test
	public void TC_04_React_Enter() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

		enterToInCustomDropdownList("input.search", "div.item>span.text", "Belarus");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Belarus");
		
		selectItemInCustomDropdownList("div.dropdown", "div.item>span.text", "Austria");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Austria");
		
		enterToInCustomDropdownList("input[type='text']", "div.item>span.text", "Belize");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Belize");
	}
	
	@Test
	public void TC_05_Vue_Select() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");

		selectItemInCustomDropdownList("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Third Option");
		
		selectItemInCustomDropdownList("li.dropdown-toggle", "ul.dropdown-menu a", "First Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");
		
		selectItemInCustomDropdownList("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
	}
	
	@Test
	public void TC_06_Angular_Enter() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		
		// Tỉnh/Thành phố
		enterToInCustomDropdownList("ng-select[bindvalue='provinceCode'] input", "div[role='option']>span.ng-option-label", "Thành phố Hồ Chí Minh");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='provinceCode'] span.ng-value-label")).getText(), "Thành phố Hồ Chí Minh");
		
		// Quận/Huyện
		enterToInCustomDropdownList("ng-select[bindvalue='districtCode'] input", "div[role='option']>span.ng-option-label", "Thành phố Thủ Đức");
		String actualDistrict = (String) jsExcutor.executeScript("return document.querySelector(\"ng-select[bindvalue='districtCode'] span.ng-value-label\").innerText");
		Assert.assertEquals(actualDistrict, "Thành phố Thủ Đức");
		
		// Xã/Phường
		enterToInCustomDropdownList("ng-select[bindvalue='wardCode'] input", "div[role='option']>span.ng-option-label", "Phường Trường Thạnh");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='wardCode'] span.ng-value-label")).getAttribute("innerText"), "Phường Trường Thạnh");

		// Dân tộc		
		enterToInCustomDropdownList("ng-select[bindvalue='ethnicityCode'] input", "div[role='option']>span", "Thái");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='ethnicityCode'] span.ng-value-label")).getAttribute("textContent"), "Thái");
		enterToInCustomDropdownList("ng-select[bindvalue='ethnicityCode'] input", "div[role='option']>span", "Hoa");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='ethnicityCode'] span.ng-value-label")).getAttribute("textContent"), "Hoa");
		
		// Quốc tịch
		enterToInCustomDropdownList("ng-select[bindvalue='nationCode'] input", "div[role='option']>span", "Hong Kong");
		String actualNation = (String) jsExcutor.executeScript("return document.querySelector(\"ng-select[bindvalue='nationCode'] span.ng-value-label\").innerHTML");
		Assert.assertEquals(actualNation, "Hong Kong");
		
		// Quan hệ 
		enterToInCustomDropdownList("ng-select[formcontrolname='guardianRelationshipId'] input", "div[role='option']>span", "Mẹ");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[formcontrolname='guardianRelationshipId'] span.ng-value-label")).getText(), "Mẹ");	
	}
	
	public void selectItemInCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {
		// - Step 1: click vào cho xổ ra các items
		driver.findElement(By.cssSelector(parentLocator)).click();
		
		// - Step 2: chờ các item load ra hết 
		// Lưu ý: lấy locator chứa tất cả item và lấy đến node cuối cùng chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		
		// - Step 3: tìm item cần chọn
		//   + Nếu item nằm trong vùng nhìn thấy thì chọn luôn
		//   + Nếu item nằm ở dưới thì scroll đến item đó
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		for (WebElement item : allItems) {
			String actualText = item.getText();
			
			if (actualText.equals(expectedTextItem)) {
				// Scroll
				jsExcutor.executeScript("arguments[0].scrollIntoView(true);", item);

				// - Step 4: click vào item
				item.click();
				sleepInMilisecond(300);
				// Thoát
				break;
			}
		}		
	}
	
	public void enterToInCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {
		// - Step 1: lấy locator đến thẻ input (textbox) để sendkeys 
		driver.findElement(By.cssSelector(parentLocator)).sendKeys(expectedTextItem);
		
		// - Step 2: chờ các item load ra hết 
		// Lưu ý: lấy locator chứa tất cả item và lấy đến node cuối cùng chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		
		// - Step 3: tìm item cần chọn
		//   + Nếu item nằm trong vùng nhìn thấy thì chọn luôn
		//   + Nếu item nằm ở dưới thì scroll đến item đó
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		for (WebElement item : allItems) {
			String actualText = item.getText();
			
			if (actualText.equals(expectedTextItem)) {
				// Scroll
				jsExcutor.executeScript("arguments[0].scrollIntoView(true);", item);
				
				// - Step 4: click vào item
				item.click();
				sleepInMilisecond(300);
				// Thoát
				break;
			}
		}		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInMilisecond(long milisecond) {
		try {
			Thread.sleep(milisecond);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
