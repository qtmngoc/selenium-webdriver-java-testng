package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Javascript_Executor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();	
		
		// Khởi tạo != Null
		jsExecutor = (JavascriptExecutor) driver;
	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// Chỉ dùng được cho executeAsyncScript
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Javascript_Executor() {
		// jsExecutor: not apply wait to find an element or visible element 

		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(1);
		
		String liveGuruDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(liveGuruDomain, "live.techpanda.org");
		
		String liveGuruUrl = (String) executeForBrowser("return document.URL;");
		Assert.assertEquals(liveGuruUrl, "http://live.techpanda.org/");
		
		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(1);
		
		hightlightElement("//a[@title='Samsung Galaxy']/parent::li//button");
		clickToElementByJS("//a[@title='Samsung Galaxy']/parent::li//button");
		sleepInSecond(1);
		
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		
		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(1);
		
		String customerServiceTitle = (String) executeForBrowser("return document.title;");
		Assert.assertEquals(customerServiceTitle, "Customer Service");
		
		hightlightElement("//input[@id='newsletter']");
		scrollToElementOnTop("//input[@id='newsletter']");
		sendkeyToElementByJS("//input[@id='newsletter']", "jsexecutor" + getRandomNumber() + "@hotmail.vn");
		
		hightlightElement("//button[@title='Subscribe']");
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(1);
		
		String subscribeMessage = getInnerText();
		Assert.assertTrue(subscribeMessage.contains("Thank you for your subscription."));
		
		navigateToUrlByJS("https://demo.guru99.com/v4/");
		sleepInSecond(1);
		Assert.assertEquals(executeForBrowser("return document.domain"), "demo.guru99.com");
	}

	@Test
	public void TC_02_HTML5_Validation_Message() {
		driver.get("https://automationfc.github.io/html5/index.html");
		
		WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
		
		// Click on Submit and verify message at Name textbox
		submitButton.click();
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"), "Please fill out this field.");
		
		// Enter data to Name textbox, click on Submit and verify message at Password textbox
		driver.findElement(By.id("fname")).sendKeys("java");
		submitButton.click();
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");
		
		// Enter data to Password textbox, click on Submit and verify message at Email textbox
		driver.findElement(By.id("pass")).sendKeys("060322");
		submitButton.click();
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		
		// Enter invalid format to Email textbox, click on Submit and verify message
		driver.findElement(By.id("em")).sendKeys("123@456");
		submitButton.click();
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please match the requested format.");

		// Enter data to Email textbox, click on Submit and verify message at Address dropdown
		driver.findElement(By.id("em")).clear();
		driver.findElement(By.id("em")).sendKeys("java@mail.com");
		submitButton.click();
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//select"), "Please select an item in the list.");
		
		// Click to select an option
		Select select = new Select(driver.findElement(By.xpath("//select")));
		select.selectByVisibleText("HO CHI MINH");
		sleepInSecond(1);
		Assert.assertEquals("HO CHI MINH", select.getFirstSelectedOption().getText());
		
		submitButton.click();
		Assert.assertEquals(executeForBrowser("return document.title"), "405 Not Allowed");
	}
	
	@Test
	public void TC_03_HTML5_Validation_Message() {
		driver.get("https://login.ubuntu.com/");
		
		// Popup not in DOM
		List<WebElement> cookiePopup = driver.findElements(By.cssSelector("div[aria-describedby='cookie-policy-content']"));
		if (cookiePopup.size() > 0) {
			driver.findElement(By.cssSelector("button#cookie-policy-button-accept")).click();
			sleepInSecond(1);
		}
		
		String emailTextboxXpath = "//input[@id='id_email']";
		String passwordTextboxXpath = "//input[@id='id_password']";
		WebElement loginButton = driver.findElement(By.cssSelector("button[data-qa-id='login_button']"));
		
		loginButton.click();
		Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "Please fill out this field.");
		
		driver.findElement(By.xpath(emailTextboxXpath)).sendKeys("123@111#");
		if (driver.toString().contains("ChromeDriver")) {
			Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "A part following '@' should not contain the symbol '#'.");
		} else if (driver.toString().contains("FirefoxDriver")) {
			Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "Please enter an email address.");
		}
		sleepInSecond(1);

		driver.findElement(By.xpath(emailTextboxXpath)).clear();
		driver.findElement(By.xpath(emailTextboxXpath)).sendKeys("javascript@gmail.com");
		loginButton.click();
		Assert.assertEquals(getElementValidationMessage(passwordTextboxXpath), "Please fill out this field.");
		sleepInSecond(1);
		
		driver.findElement(By.xpath(passwordTextboxXpath)).sendKeys("123123");
		loginButton.click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p.p-notification__response")).getText(), "There were some problems with the information you gave us. Please check below and try again.");	
	}
	
	@Test
	public void TC_04_Create_An_Account() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(1);
		clickToElementByJS("//div[@id='header-account']//a[@title='My Account']");
		sleepInSecond(1);
		clickToElementByJS("//a[@title='Create an Account']");
		sleepInSecond(1);	
		Assert.assertEquals(executeForBrowser("return document.title;"), "Create New Customer Account");
		
		sendkeyToElementByJS("//input[@id='firstname']", "Javascript");
		sendkeyToElementByJS("//input[@id='lastname']", "Executor");
		sendkeyToElementByJS("//input[@id='email_address']", "java_script" + getRandomNumber() + "@gmail.vn");
		sendkeyToElementByJS("//input[@id='password']", "1234567");
		sendkeyToElementByJS("//input[@id='confirmation']", "1234567");
		clickToElementByJS("//button[@title='Register']");
		sleepInSecond(1);
		
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for registering with Main Website Store."));
		
		clickToElementByJS("//div[@id='header-account']//a[@title='Log Out']");
	
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'This is demo site for')]")).isDisplayed());
		sleepInSecond(1);
		Assert.assertEquals(executeForBrowser("return document.title;"), "Home page");
	}
	
	@Test
	public void TC_05_Image() {
		driver.get("https://automationfc.github.io/basic-form/");
		
		Assert.assertTrue(isImageLoaded("//img[@alt='User Avatar 05']"));
		Assert.assertFalse(isImageLoaded("//img[@alt='broken_04']"));
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
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}