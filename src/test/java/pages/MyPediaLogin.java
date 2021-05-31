package pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.Util;

public class MyPediaLogin {

	WebDriver driver;
	String localizeText;
	String divLanguage;
	int sizeWebElementLanguage;
	HashMap<String, String> langMap;
	Set<WebElement> uniqueLanguageElements;

	@FindBy(xpath = "//div[@id='divNext']")
	WebElement nextButton;

	@FindBy(className = "accountDetailsLangDropDown")
	WebElement langDropDown;

	@FindBy(xpath = "//div[@role='menu']//div[contains(@style,'margin-left')]/div")
	List<WebElement> languageDiv;

	@FindBy(xpath = "//div[@class='Stage loginWrap']/div[2]/button[@type='submit']/div/div")
	WebElement continueButtonLogin;
	
	@FindBy(className="childSupportLink")
	WebElement setupParentSupButton;

	public MyPediaLogin(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Click on Continue button on the iframe
	 */
	public void clickContinueButton() {
		driver.switchTo().frame("contentIframe");
		nextButton.click();
		driver.switchTo().defaultContent();
	}

	/**
	 * Click on language dropdown
	 */
	public void clickLanguageDropdown() {
		langDropDown.click();
	}
	
	/**
	 * Setup Parent support
	 */
	
	public void setupParentSupport() {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(setupParentSupButton));
		setupParentSupButton.click();
	}

	/**
	 * Verify the language drop-down has at least 3 languages Verify the drop-down
	 * has different languages
	 */
	public void verifyUniqueLanguageCount() {
		clickLanguageDropdown();
		uniqueLanguageElements = new HashSet<WebElement>(languageDiv);
		sizeWebElementLanguage = languageDiv.size();
		Assert.assertTrue(sizeWebElementLanguage == 3);
		Assert.assertTrue(uniqueLanguageElements.size() == sizeWebElementLanguage);
	}
	/**
	 * Map language to property file
	 */
    public void createHashmapLanguages() {
		langMap = new HashMap<String, String>();
		langMap.put("English", "/src/test/java/resources/en.properties");
		langMap.put("हिंदी", "/src/test/java/resources/hindi.properties");
		langMap.put("Español", "/src/test/java/resources/spanish.properties");
	}
    /**
     * Verify text on Continue label in different languages
     * @param continueLabelText
     * @throws IOException
     */

	public void verifyTextChangeOnLanguageChange(String continueLabelText) throws IOException {
       createHashmapLanguages();
       WebDriverWait wait = new WebDriverWait(driver, 40);
       JavascriptExecutor executor = (JavascriptExecutor) driver;
       for (int i = 0; i < sizeWebElementLanguage; i++) {
			divLanguage = languageDiv.get(i).getText();
			localizeText = Util.getDataFromPropertyFile(langMap.get(divLanguage), continueLabelText);
			wait.until(ExpectedConditions.elementToBeClickable(languageDiv.get(i)));
			executor.executeScript("arguments[0].click();", languageDiv.get(i));
			wait.until(ExpectedConditions.textToBePresentInElement(continueButtonLogin, localizeText));
			Assert.assertEquals(continueButtonLogin.getText(), localizeText);
			clickLanguageDropdown();
		}		
	}
	/**
	 * Setting default language to English
	 */
	public void setDefaultLanguage() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(languageDiv.get(0)));
		executor.executeScript("arguments[0].click();", languageDiv.get(0));
		
	}

}
