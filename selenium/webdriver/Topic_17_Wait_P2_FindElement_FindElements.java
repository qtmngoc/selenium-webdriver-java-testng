package webdriver;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_P2_FindElement_FindElements {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String separatorChar = File.separator;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "chromedriver.exe");
		System.setProperty("webdriver.edge.driver", projectPath + separatorChar + "browserDrivers" + separatorChar + "msedgedriver.exe");
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Find_Element() {
		driver.get("https://www.sendo.vn/");	
		
	 /* - Có duy nhất 1 element
	  	Nếu element xuất hiện ngay -> trả về element đó không cần chờ hết timeout của implicitWay.
	  	Nếu element chưa xuất hiện -> tìm lại sau mỗi 0.5s cho đến khi hết timeout. */

		String startTimeFindLogo = getCurrentTime();
		System.out.println("1. Find Element\n- Find Sendo logo\nStart Time: " + startTimeFindLogo);
		driver.findElement(By.cssSelector("a[aria-label='sendo logo']"));
		String endTimeFindLogo = getCurrentTime();
		System.out.println("End Time: " + endTimeFindLogo);
		
		String startTimeFindPopup = getCurrentTime();
		System.out.println("- Find Sendo popup\nStart Time: " + startTimeFindPopup);
		driver.findElement(By.xpath("//span[text()='Nhận tin từ Sendo']"));
		String endTimeFindPopup = getCurrentTime();
		System.out.println("End Time: " + endTimeFindPopup);	
		
		// Close popup
		if (driver.findElements(By.cssSelector("img[title]")).size() > 0) {
			driver.findElement(By.cssSelector("button[aria-label='button close']")).click();
		}
		driver.findElement(By.xpath("//span[text()='Không, cảm ơn']")).click();
		
	 /* - Có nhiều hơn 1 element
	  	Lấy element đầu tiên để thao tác */
	  	driver.findElement(By.cssSelector("a.b91-fd09f4")).click();
		
	 /*	- Không có element nào
	  	Tìm đi tìm lại cho đến khi hết timeout.
	  	Hết timeout thì đánh fail cả test case.
	  	Không chạy tiếp các step sau.
	  	Throw/ Log ra 1 exception (ngoại lệ): NoSuchElement */
		driver.findElement(By.cssSelector("div.automation"));
	}

	@Test
	public void TC_02_Find_Elements() {
		driver.get("https://www.facebook.com/");
		
	 /* - Có 1 hoặc nhiều element
	  	Nếu element xuất hiện ngay -> trả về element đó không cần chờ hết timeout của implicit.
	  	Nếu element chưa xuất hiện -> tìm lại sau mỗi 0.5s cho đến khi hết timeout. */
		int elementNumber = 0;
		elementNumber = driver.findElements(By.cssSelector("input#email")).size();
		System.out.println("2. Find Elements\n1 element = " + elementNumber);
		elementNumber = driver.findElements(By.xpath("//div[@id='pageFooterChildren']//a[text()]")).size();
		System.out.println("n elements = " + elementNumber);
		
	 /* - Không có element nào
	  	Tìm đi tìm lại cho đến khi hết timeout.
	  	Hết timeout thì không đánh fail test case.
	  	Chạy tiếp các step sau. */
		System.out.println("Start time = " + getCurrentTime());
		elementNumber = driver.findElements(By.cssSelector("div.automation")).size();
		System.out.println("End time = " + getCurrentTime());
		System.out.println("0 element = " + elementNumber);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getCurrentTime() {
		Date date = new Date();
		return date.toString();
	}
}