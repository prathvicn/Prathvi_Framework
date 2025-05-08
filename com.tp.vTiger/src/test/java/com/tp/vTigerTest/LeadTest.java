package com.tp.vTigerTest;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.tp.vTiger.generic.BaseClassTest.BaseClass;
import com.tp.vTiger.generic.ObjectRepository.CreatingNewLeadPage;
import com.tp.vTiger.generic.ObjectRepository.LeadInfoPage;
/**
 * Author: Prathvi
 * Module: Lead
 * Test_cases: *Create Lead
 * 				*Lead with Leadsource & Industry
 */
public class LeadTest extends BaseClass{
	
	String lead_lastname,company,leadsource,industry = null;
	
	@Test
	public void createLeadTest() throws Throwable {
		wLib.navigateToPage(driver, "Leads", "Create Lead...");
		//Generate Random Number using Java utility
		//Read Test Script data from Excel file using Excel Utility
		lead_lastname = eu.getDataFromExcelFile("Lead", 1, 2) + jLib.getRandomNumber();
		company = eu.getDataFromExcelFile("Lead", 1, 3);

		//creating lead with pom class
		CreatingNewLeadPage newLeadPage = new CreatingNewLeadPage(driver);
		newLeadPage.getLastname_Edt().sendKeys(lead_lastname);
		newLeadPage.getCompany_Edt().sendKeys(company);
		newLeadPage.getSave_Btn().click();
	
		LeadInfoPage leadInfoPage = new LeadInfoPage(driver);
		
		//verifying Lead name
		
		validate(leadInfoPage.getLeadInfoHeader_Txt(), lead_lastname);	
	}

	@Test
	public void createLeadWithLeadSourceAndIndustryTest() throws  Throwable {
		wLib.navigateToPage(driver, "Leads", "Create Lead...");

		//Generate Random Number using Java utility
		//Read Test Script data from Excel file using Excel Utility
		lead_lastname = eu.getDataFromExcelFile("Lead", 3, 2) + jLib.getRandomNumber();
		company = eu.getDataFromExcelFile("Lead", 3, 3);
		leadsource = eu.getDataFromExcelFile("Lead", 3, 4);
		industry = eu.getDataFromExcelFile("Lead", 3, 5);

		
		CreatingNewLeadPage newLeadPage = new CreatingNewLeadPage(driver);
		newLeadPage.getLastname_Edt().sendKeys(lead_lastname);
		newLeadPage.getCompany_Edt().sendKeys(company);
	
		//dropdown handling using webdriver utility
		WebElement LeadSource_element = newLeadPage.getLeadsource_dropdown();
		WebElement Industry_element = newLeadPage.getIndustry_dropdown();
		wLib.selectByVisibleTextDropDown(LeadSource_element, leadsource);
		wLib.selectByVisibleTextDropDown(Industry_element, industry);

	
		newLeadPage.getSave_Btn().click();
			LeadInfoPage leadInfoPage = new LeadInfoPage(driver);
			//verifying lead info page
			validate(leadInfoPage.getLeadInfoHeader_Txt(), lead_lastname);
		
		//verifying lead name
			validateTextfield(leadInfoPage.getLeadLastName_Edt(), lead_lastname);
		//verifying lead source
			validateTextfield(leadInfoPage.getLeadsource_Edt(), leadsource);
		//verifying industry
			validateTextfield(leadInfoPage.getIndustry_Edt(), industry);
	}
}
