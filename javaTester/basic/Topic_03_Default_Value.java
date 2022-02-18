package basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Topic_03_Default_Value {
	// Khai báo - chưa khởi tạo
	boolean status;
	char c;
	byte bNumber;
	short sNumber;
	int iNumber;
	long lNumber;
	float fNumber;
	double dNumber;
	String address;
	WebDriver driver;
	WebElement element;
	
	// Khai báo + khởi tạo
	String name = "Ngoc Quach";
	
	@Test
	public void Demo() {
		System.out.println(status);
		System.out.println(c);
		System.out.println(bNumber);
		System.out.println(sNumber);
		System.out.println(iNumber);
		System.out.println(lNumber);
		System.out.println(fNumber);
		System.out.println(dNumber);
		System.out.println(address);
		System.out.println(driver);
		System.out.println(element);
	}

}
