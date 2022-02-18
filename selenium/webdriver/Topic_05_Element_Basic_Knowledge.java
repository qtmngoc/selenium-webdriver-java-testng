package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Basic_Knowledge {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();	
		
		driver.get("https://www.facebook.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	// Các hàm (function/ method)/ câu lệnh (method):
	// Tương tác với browser: driver.	
	// Tương tác với element: by.
	
	public void TC_01() {
		WebElement element = driver.findElement(By.xpath(""));
		
		// Xóa dữ liệu trong textbox/ textarea/ editable dropdown (cho phép edit/ nhập liệu)
		element.clear(); //*
		
		// Nhập dữ liệu vào textbox/ textarea/ editable dropdown (cho phép edit/ nhập liệu)
		element.sendKeys(""); //**
		
		// Click vào button/ radio button/ checkbox/ link/ image...
		element.click(); //**
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		// Không phổ biến, nên dùng ở trên
		driver.findElement(By.xpath("//div[@class='footer']")).findElement(By.xpath("//a[@title='My Account']")).click();
		
		// Lấy giá trị của attribute
		element.getAttribute("placeholder"); //*
		
		// Lấy text của element
		element.getText(); //**
		
		// Lấy ra các thuộc tính của CSS. Dùng để test GUI: font/ color/ size/ location/ position/...
		element.getCssValue(""); //*
		
		// Ít dùng
		element.getLocation();
		element.getRect();
		Dimension elementSize = element.getSize();
		
		// Chụp hình của element rồi lưu bằng format nào đó
		String base64Image = element.getScreenshotAs(OutputType.BASE64); //*
		element.getScreenshotAs(OutputType.BYTES);
		element.getScreenshotAs(OutputType.FILE);

		// Lấy ra tên thẻ HTML của element
		element = driver.findElement(By.xpath("//input[@id='email']"));
		element = driver.findElement(By.cssSelector("input[id='email']"));
		String elementTagname = element.getTagName();
		
		// Kiểm tra 1 element có hiển thị hay không (user thấy được)
		// Mong đợi element hiển thị
		Assert.assertTrue(element.isDisplayed()); //**
		// Mong đợi element không hiển thị
		Assert.assertFalse(element.isDisplayed());
		
		// Kiểm tra 1 element có thể thao tác được hay không.
		// Mong đợi element thao tác được
		Assert.assertTrue(element.isEnabled()); //*
		// Mong đợi element read-only hoặc disable
		Assert.assertFalse(element.isEnabled());

		// Kiểm tra 1 element đã được chọn hay chưa. Radio button/ Checkbox/ Dropdown
		// Mong đợi element đã được chọn
		Assert.assertTrue(element.isSelected()); //*
		// Mong đợi element chưa được chọn/ bỏ chọn
		Assert.assertFalse(element.isSelected());
		
		// Giống với nhấn enter trên bàn phím. Chỉ dùng với element là form hoặc nằm trong form.
		element.submit();
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
