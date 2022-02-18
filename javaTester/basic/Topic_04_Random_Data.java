package basic;

import java.util.Random;

public class Topic_04_Random_Data {

	public static void main(String[] args) {
		Random rand = new Random();
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt(999)); // 0->999
		System.out.println(rand.nextDouble());
		System.out.println(rand.nextLong());
		System.out.println(rand.nextFloat());
	}
	
}
