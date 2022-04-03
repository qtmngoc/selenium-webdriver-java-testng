package testNG;

import org.testng.annotations.Test;

public class Topic_03_Group_Employee {

	@Test(groups = "employee")
	public void TC_01_Create_User() {

	}

	@Test(groups = { "employee" })
	public void TC_02_View_User() {

	}

	@Test(groups = { "employee", "admin" })
	public void TC_03_Edit_User() {

	}

	@Test(groups = { "employee", "admin" })
	public void TC_04_Delete_User() {

	}
}
