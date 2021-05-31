package pages;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MyPediaCreateAccount {
	WebDriver driver;

	@FindBy(xpath = "//div[@class='acrCheckCodeButton'][1]/button")
	WebElement createAccountButton;
	
	@FindBy(xpath = "//input[contains(@id,'Firstname')]")
	WebElement firstName;
	
	@FindBy(xpath = "//input[contains(@id,'Lastname')]")
	WebElement lastName;
	
	@FindBy(xpath = "//input[contains(@id,'Emailaddress')]")
	WebElement emailAddress;
	
	@FindBy(xpath ="//label[text()='Create parent username']/../input")
	WebElement parentUserName;
	
	@FindBy(xpath ="//label[text()='Create parent password']/../input")
	WebElement password;
	
	@FindBy(xpath ="//input[contains(@id,'Confirmpassword')]")
	WebElement confirmPassword;
	
	@FindBy(xpath="//span[text()='CREATE ACCOUNT']")
	WebElement createAccountSpan;
	


	public MyPediaCreateAccount(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
    /**
     * Click on create account button
     */
	public void clickCreateAccountButton() {
		createAccountButton.click();
	}
   /**
    * Wait for driver to be in ready state
    */
	public void waitForLoaderToDisappear() {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		
	}
   /**
    * Create new user account and verify the create account button
    * @param firstName
    * @param lastName
    * @param emailAddress
    * @param parentUserName
    * @param password
    * @param confirmPassword
    */
	public void createNewUserAccount(String firstName, String lastName, String emailAddress, String parentUserName,
			String password, String confirmPassword) {
		driver.navigate().refresh();
		waitForLoaderToDisappear();
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		this.emailAddress.sendKeys(emailAddress);
		this.parentUserName.sendKeys(parentUserName);
		this.password.sendKeys(password);
		this.confirmPassword.sendKeys(confirmPassword);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(createAccountSpan));
		Assert.assertEquals(createAccountSpan.getCssValue("opacity"),"0.3");
		
	}
	

}
