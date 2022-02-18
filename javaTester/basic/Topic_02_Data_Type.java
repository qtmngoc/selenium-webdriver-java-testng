package basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Topic_02_Data_Type {
	WebDriver driver;

	public void main(String[] args) {
		// 2 loại kiểu dữ liệu
		// - Kiểu nguyên thủy (Primitive Type): char, byte, short, int, long, float, double, boolean
		char c = 'A';
		
		byte bNumber = 15;
		
		short sNumber = -32000;
		
		int iNumber = 2000000000;
		
		long lNumber = 432543931;
		
		float fNumber = 9.5f;
		
		double dNumber = 10.5d;
		
		boolean marriedStatus = true;
		
		// - Kiểu tham chiếu (Reference Type): object, array, string, class, interface, collection,...
		Object o = new Object();
		
		String[] address = {"Ha Noi", "Sai Gon", "Da Nang"};
		Integer[] phone = {98435, 4212};
		long q[] = {10000L, 43243L, 896L, 978968L};
		
		String name = "Ngoc432~#%$@#";
		String cityName = new String("Ho Chi Minh");
		
		Topic_02_Data_Type topic = new Topic_02_Data_Type();
		
		WebDriver driver = null;
		
		List<String> addresses = new ArrayList<String>();
		
		// Dùng local driver 
		WebElement emailTextBox = driver.findElement(By.cssSelector(""));
		
		// Muốn dùng global driver thì thêm this
		List<WebElement> checkboxes = this.driver.findElements(By.tagName("input"));
		
		// Exercise 1:
		int a, b;
		Scanner ip = new Scanner(System.in);
		
		System.out.println("*** Exercise 1: ");
		
		System.out.print("Enter a = ");
		a = ip.nextInt();
		
		System.out.print("Enter b = ");
		b = ip.nextInt();
		
		int addition = a + b;
		int subtraction = a - b;
		int multiplication = a * b;
		double division = a/b;
		
		System.out.println("a + b = " + addition);
		System.out.println("a - b = " + subtraction);
		System.out.println("a * b = " + multiplication);
		System.out.println("a / b = " + division);

		System.out.println("*** Exercise 2:");
		System.out.println("Length = 7.5");
		System.out.println("Width = 3.8");
		double  P = 7.5*3.8;
		System.out.println("Area of rectangle = " + P);
		
		System.out.println("*** Exercise 3:");
		String n = "Hello Automation Testing";
		System.out.println(n);

	}

}
