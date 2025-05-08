package com.tp.vTigerTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.tp.vTiger.generic.BaseClassTest.BaseClass;
import com.tp.vTiger.generic.ObjectRepository.CreatingNewVendorPage;
import com.tp.vTiger.generic.ObjectRepository.VendorInfoPage;

/**
 * Author: Prathvi
 * Module: Vendor
 * Test_cases: *Create Vendor
 */
public class VendorTest extends BaseClass {
	String vendor_name = null;
	@Test
	//(retryAnalyzer = com.tp.vTiger.generic.ListenerUtility.RetryAnalyserImp.class)
	public void createVendorTest() throws Throwable {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.linkText("More"))).perform();

		wLib.navigateToPage(driver, "Vendors", "Create Vendor...");
	
		//Generate Random Number using Java Utility
		//Read Test Script data from Excel file using ExcelUtility
		vendor_name = eu.getDataFromExcelFile("Vendor", 1, 2) + jLib.getRandomNumber();

		CreatingNewVendorPage newVendorPage = new CreatingNewVendorPage(driver);
		newVendorPage.getVendorname_Edt().sendKeys(vendor_name);
		newVendorPage.getSave_Btn().click();
		Thread.sleep(3000);
		VendorInfoPage vendorInfoPage = new VendorInfoPage(driver);
		WebElement elmt = vendorInfoPage.getVendorInfoHeader_Txt();
		
		validate(elmt, vendor_name);
	}

}
