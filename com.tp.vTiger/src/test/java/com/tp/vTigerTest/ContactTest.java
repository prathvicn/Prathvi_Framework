package com.tp.vTigerTest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.tp.vTiger.generic.BaseClassTest.BaseClass;
import com.tp.vTiger.generic.ObjectRepository.ContactInformationPage;
import com.tp.vTiger.generic.ObjectRepository.CreatingNewContactPage;
import com.tp.vTiger.generic.ObjectRepository.CreatingNewOrganizationPage;
import com.tp.vTiger.generic.ObjectRepository.OrganizationPopupPage;
/**
 * Author: Prathvi
 * Module: Contact
 * Test_cases: *Create Contact
 * 				*Contact with Org
 * 				*Contact with Support Date
 * 				
 */
public class ContactTest extends BaseClass {
	String contact_name = null;
	String Org_name = null;

	@Test(groups = "smokeTest")
	public void createContactTest() throws IOException, Throwable {
		CreatingNewContactPage newContactPage = new CreatingNewContactPage(driver);
		wLib.navigateToPage(driver, "Contacts", "Create Contact...");
		contact_name = eu.getDataFromExcelFile("Contact", 1, 2) + jLib.getRandomNumber();

		newContactPage.createNewContact(contact_name);

		ContactInformationPage cip = new ContactInformationPage(driver);
		cip.verifyingHeader(cip.getContactInfoHeader_Txt(), contact_name);
		cip.verifyingTextfield(cip.getLastname_Edt(), contact_name);
		wLib.toTakeScreenShotEntirePage(driver);
		wLib.toTakeScreenShotofWebElement(driver, cip.getContactInfoHeader_Txt());
		Thread.sleep(2000);

	}

	@Test
	public void createContactWithOrganizationTest() throws IOException, Throwable {
	wLib.navigateToPage(driver, "Organizations", "Create Organization...");
		CreatingNewContactPage newContactPage = new CreatingNewContactPage(driver);
		CreatingNewOrganizationPage newOrgPage = new CreatingNewOrganizationPage(driver);
		Org_name = eu.getDataFromExcelFile("Org", 1, 2) + jLib.getRandomNumber();
		newOrgPage.getAccountname_Edt().sendKeys(Org_name);
		newOrgPage.getSave_Btn().click();

		Thread.sleep(3000);
		wLib.navigateToPage(driver, "Contacts", "Create Contact...");
		contact_name = eu.getDataFromExcelFile("Contact", 1, 2)+jLib.getRandomNumber();
		
		newContactPage.getLastname_Edt().sendKeys(contact_name);
		newContactPage.getOrg_lookup().click();
		wLib.switchToWindow(driver, "Accounts&action");
		OrganizationPopupPage orgPopupPage = new OrganizationPopupPage(driver);
		orgPopupPage.getSearch_txt().sendKeys(Org_name);
		orgPopupPage.getSearchNow_Btn().click();

		driver.findElement(By.xpath("//a[text()='"+Org_name+"']")).click();

		wLib.switchToWindow(driver, "Contacts&action");
		newContactPage.getSave_Btn().click();

		ContactInformationPage cip = new ContactInformationPage(driver);
		cip.verifyingHeader(cip.getContactInfoHeader_Txt(), contact_name);
		cip.verifyingTextfield(cip.getLastname_Edt(), contact_name);
		cip.verifyingTextfield(cip.getOrgname_edt(), Org_name);

	}
	
	@Test
	public void createContactWithSupportDateTest() throws IOException, Throwable {
	wLib.navigateToPage(driver, "Contacts", "Create Contact...");
	CreatingNewContactPage newContactPage = new CreatingNewContactPage(driver);
	contact_name = eu.getDataFromExcelFile("Contact", 1, 2)+jLib.getRandomNumber();
		newContactPage.getLastname_Edt().sendKeys(contact_name);
		String actDate = jLib.getSystemDate();
		String date_req = jLib.getRequiredDate();

		WebElement startDate_WE = newContactPage.getSupportStartDate_Edt();
		startDate_WE.clear();
		startDate_WE.sendKeys(actDate);
		WebElement EndDate_WE = newContactPage.getSupportEndDate_Edt();
		EndDate_WE.clear();
		EndDate_WE.sendKeys(date_req);
		
		newContactPage.getSave_Btn().click();
	

		
		ContactInformationPage contactInfoPage = new ContactInformationPage(driver);
		//Verify Contact Info Header
		
		validate(contactInfoPage.getContactInfoHeader_Txt(), contact_name);
		validateTextfield(contactInfoPage.getLastname_Edt(), contact_name);
		validateTextfield(contactInfoPage.getSupportStartDate_Edt(), actDate);
		validateTextfield(contactInfoPage.getSupportEndDate_Edt(), date_req);		

	}

}
