package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Browser_Basic_Knowledge {
	WebDriver driver;
	WebElement element;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public void TC_01_Browser() {
		// Các hàm/ method để thao tác với Browser thông qua biến driver
		
		// Mở URL
		driver.get("https://www.google.com/"); //**
		driver.get("https://live.techpanda.org");
		
		// Đóng browser nếu chỉ có 1 tab. Đóng tab hiện tại nếu có nhiều tab.
		// WebDriver API - Windows/ Tabs
		driver.close(); //*
		
		// Đóng hẳn browser
		driver.quit(); //**
		
		// Tìm 1 element trên page, trả về data type là WebElement
		WebElement emailTextBox = driver.findElement(By.id("email")); //**
		
		// Tìm nhiều hơn 1 element trên page, trả về data type là List<WebElement>
		List<WebElement> links = driver.findElements(By.xpath("//a")); //**
		
		// Trả về URL của page hiện tại.
		String homePageUrl = driver.getCurrentUrl(); //*
		
		// Verify tuyệt đối
		Assert.assertEquals(homePageUrl, "https://demo.nopcommerce.com/"); 
		
		// Lấy ra source code của trang hiện tại (HTML/ CSS/ JS/ JQuery/...)
		// Verify tương đối 1 giá trị trong trang
		String homePageSource = driver.getPageSource();
		Assert.assertTrue(homePageSource.contains("Welcome to our store"));
		Assert.assertTrue(homePageSource.contains("Online shopping is the process consumers go through to purchase products or services over the Internet."));
		
		// Lấy ra title của page hiện tại
		String homePageTitle = driver.getTitle(); //*
		Assert.assertEquals(homePageTitle, "nopCommerce demo store");
		
		// WebDriver API - Windows/ Tabs
		// Trả về 1 ID của tab hiện tại (1)
		String signUpTabID = driver.getWindowHandle(); //*
		
		// Trả về ID của tất cả các tab đang có (n)
		Set<String> allTabID = driver.getWindowHandles(); //*
		
		// Xử lí cookies (Framework)
		driver.manage().getCookies(); //*
		
		// Lấy log của browser (Framework)
		driver.manage().logs();
		
		// Thời gian chờ để findElement/ findElements
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //**
		
		// Thời gian chờ page load xong
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		// Thời gian để 1 đoạn async script được thực thi thành công (JavaScriptExecutor)
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		
		// Fullscreen browser
		driver.manage().window().fullscreen();
		
		// End User thường dùng Maximize hơn Fullscreen
		driver.manage().window().maximize(); //*
		
		// Test GUI
		
		// Lấy ra vị trí của browser so với độ phân giải màn hình
		Point browserPosition = driver.manage().window().getPosition();
		
		// Set vị trí của browser tại điểm 0 x 250
		driver.manage().window().setPosition(new Point(0,250));
		
		// Lấy ra chiều rộng, chiều cao của browser
		Dimension browserSize = driver.manage().window().getSize();
		
		// Set browser mở với kích thước nào
		// Test Reponsive
		driver.manage().window().setSize(new Dimension(1920, 1080));
		
		// Back
		driver.navigate().back();
		
		// Forward
		driver.navigate().forward();
		
		// F5
		driver.navigate().refresh();
		
		driver.navigate().to("https://www.sendo.vn/");
		
		// WebDriver API - Alert/ Authentication Alert
		driver.switchTo().alert(); //**
		
		// WebDriver API - Frame/ Iframe
		driver.switchTo().frame(1); //**
		
		// WebDriver API - Windows/ Tabs
		driver.switchTo().window(""); //**
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
