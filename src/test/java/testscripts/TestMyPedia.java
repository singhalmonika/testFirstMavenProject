package testscripts;

import org.testng.annotations.Test;


import com.opencsv.exceptions.CsvException;

import pages.MyPediaLogin;
import utility.Util;
import pages.MyPediaCreateAccount;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class TestMyPedia{
	//Give the path of the chromedriver executable
	String driverPath = "chromedriver";
	WebDriver driver;

	String continueLabelText = "Continue";
	MyPediaLogin objLogin;
	MyPediaCreateAccount objCreateAccount;

	@DataProvider(name = "createAccount")
	public Object[][] createAccountDataProvider(ITestContext test) throws IOException, CsvException {
		System.out.println(test.getCurrentXmlTest().getName());
		Object[][] csvData = Util.readCSVFile("/src/test/java/testdata/UserData.csv");
		return csvData;

	}

	@BeforeTest
	@Parameters("browser")
	public void setup() {
		// Disabling chrome notifications
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.mypedia.pearson.com/");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		objLogin = new MyPediaLogin(driver);
		objCreateAccount = new MyPediaCreateAccount(driver);
	}

	/**
	 * This method closes the iframe which appears on launching the web page
	 */

	@Test()
	public void closeTheIframe() {
		objLogin.clickContinueButton();

	}

	/**
	 * This method will validate that language drop-down has three languages Text of
	 * continue button is changing on changing the language Set default language to
	 * English after text verification Click on Setup Parent Support
	 * 
	 * @throws IOException
	 */

	@Test(dependsOnMethods = { "closeTheIframe" })
	public void verifyLanguageDropdown() throws IOException {
		objLogin.verifyUniqueLanguageCount();
		objLogin.verifyTextChangeOnLanguageChange(continueLabelText);
		objLogin.setDefaultLanguage();
		objLogin.setupParentSupport();
		objCreateAccount.clickCreateAccountButton();

	}

	/**
	 * This method will fill all the details in the create account page and verify
	 * that the button is disabled.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param parentUserName
	 * @param password
	 * @param confirmPassword
	 */
	@Test(dependsOnMethods = { "closeTheIframe", "verifyLanguageDropdown" }, dataProvider = "createAccount")
	public void createNewAccount(String firstName, String lastName, String emailAddress, String parentUserName,
			String password, String confirmPassword) {
		objCreateAccount.createNewUserAccount(firstName, lastName, emailAddress, parentUserName, password,
				confirmPassword);

	}

	/**
	 * This method will take the screenshot of the browser in case of failure
	 * 
	 * @param test
	 */
	@AfterMethod
	public void teardown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			try {

				// To create reference of TakesScreenshot
				TakesScreenshot screenshot = (TakesScreenshot) driver;
				// Call method to capture screenshot
				File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

				// Copy files to specific location
				// result.getName() will return name of test case so that screenshot name will
				// be same as test case name
				FileUtils.copyFile(srcFile, new File(Util.cwd +FilenameUtils.separatorsToSystem("/src/screenshots/") + result.getName() + ".png"));
				System.out.println("Successfully captured a screenshot");

			} catch (Exception e) {

				System.out.println("Exception while taking screenshot " + e.getMessage());
			}

		}

	}
	
	
	
	@AfterTest
	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

}
