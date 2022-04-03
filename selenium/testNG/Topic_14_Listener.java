package testNG;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Topic_14_Listener implements ITestListener {

	// Định nghĩa các hành vi khi test case đạt trạng thái nào đó
	// Dùng cho mục đích báo cáo. Vd: khi fail test case sẽ screenshot màn hình lại

	// Khi hoàn thành Test case sẽ làm gì?
	@Override
	public void onFinish(ITestContext arg0) {

	}

	// Trước khi chạy test case
	@Override
	public void onStart(ITestContext arg0) {

	}

	// Khi fail Test case với tỉ lệ % pass sẽ làm gì?
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	// Khi fail Test case sẽ làm gì?
	@Override
	public void onTestFailure(ITestResult arg0) {
		System.out.println("Capture screenshot");
		System.out.println("Attach to Report HTML");
	}

	// Khi Test case bị skip sẽ làm gì?
	@Override
	public void onTestSkipped(ITestResult arg0) {

	}

	// Khi Test case bắt đầu sẽ làm gì?
	@Override
	public void onTestStart(ITestResult arg0) {

	}

	// Khi Test case pass sẽ làm gì?
	@Override
	public void onTestSuccess(ITestResult arg0) {

	}

}