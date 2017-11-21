package com.trackx.truelocate.flow;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trackx.truelocate.common.utils.Constants;
import com.trackx.truelocate.common.utils.GeneralActions;
import com.trackx.truelocate.common.utils.ReusableActions;
import com.trackx.truelocate.pagecomponents.CommonElements;
import com.trackx.truelocate.pagecomponents.FacilityElements;
import com.trackx.truelocate.pagecomponents.Truelocatelogin;

public class FacilityCreateFlow extends GeneralActions {
	WebDriver driver;
	Truelocatelogin truelocatelogin;
	FacilityElements facilityElements;
	CommonElements commonElements;
	Constants constants = new Constants();
	String className = this.getClass().getSimpleName();
	
	@BeforeClass
	public void setUp() throws IOException {
		driver = GeneralActions.launchBrowser(driver, "chrome");
		truelocatelogin = PageFactory.initElements(driver, Truelocatelogin.class);
		facilityElements = PageFactory.initElements(driver, FacilityElements.class);
		commonElements = PageFactory.initElements(driver, CommonElements.class);
		ReusableActions.loadPropFileValues();
		ReusableActions.openUrl(driver, ReusableActions.getPropFileValues("Url"));
	}

	/**
	 * Login Script
     */
	@Test(priority = 1, dataProviderClass = Truelocatelogin.class, dataProvider = "getData")
	public void userclickflow(String sUsername, String sPassword)
			throws Exception {
		try {

			truelocatelogin.enterUsernamepassword(sUsername, sPassword);
			ReusableActions.takeSnapshot(driver, className);
			Thread.sleep(1000);
			if (truelocatelogin.pageTitleValidation()) {
				TestNGResults.put("2", new Object[] { "Login screen",
						"Login successful", "Pass" });
			}else {
				TestNGResults.put("2", new Object[] { "Login screen",
						"Login Failed", "Fail" });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Facility Create
	 */
	@Test(priority = 2, dataProvider = "createData")
	public void facilityCreateFlow(String sCode, String sName, String sDescription,
			String sRegion, String sFacilityType, String sApplicationType,
			String sAddressLine1, String sAddressLine2, String sCity, String sState,
			String sZipCode, String sCountry, String sContactName, String sContact,
			String sEmail, String sLatitude, String sLongitude, String sGeoFence, 
			String sVerifiedAddress, String sInventoryManaged, String sBillTo,
			String sBillToFacility, String sShipTo, String sShipFrom, String sCrossDock,
			String sTimeZone, String sSystemofMeasurement)throws Exception {
		try {
			facilityElements.menuClick();
			Thread.sleep(1000);
			ReusableActions.takeSnapshot(driver, className);
			commonElements.clickCreatebutton();
			
			facilityElements.enterFacilityInfo(sCode, sName, sDescription, sRegion, 
					sFacilityType, sApplicationType);
			facilityElements.enterAddressInfo(sAddressLine1, sAddressLine2, sCity,
					sState, sZipCode, sCountry, sContactName, sContact, sEmail,
					sLatitude, sLongitude, sGeoFence, sVerifiedAddress);
			facilityElements.enterConfigurationInfo(sInventoryManaged, sBillTo, 
					sBillToFacility, sShipTo, sShipFrom, sCrossDock, sTimeZone, 
					sSystemofMeasurement);
			commonElements.clickCreateOrUpdatebutton();
			ReusableActions.takeSnapshot(driver, className);
			String alertMessage = commonElements.alertMessage();
			Assert.assertEquals(alertMessage, constants.add_identifiertype_successmsg);
			if (alertMessage.equalsIgnoreCase(constants.add_identifiertype_successmsg)) {
				TestNGResults.put("3", new Object[] { "Item Type screen",
						"Item Type added successfully", "Pass" });
			} else {
				TestNGResults.put("3", new Object[] { "Item Type screen",
						"Item Type not created", "Fail" });
			}
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}
	@AfterClass
	public void quitDriver() {
		try {
			Thread.sleep(5000);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DataProvider
	public static Object[][] createData() {
		return GeneralActions.getData("FacilityCreate");
	}
}
