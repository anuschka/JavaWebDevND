package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialsPage {

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id="add-credential")
    private WebElement addButton;

    @FindBy(id="edit-credential")
    private List<WebElement> editButton;

    @FindBy(id="delete-credential")
    private List<WebElement> deleteButton;

    @FindBy(id="credUrl")
    private List<WebElement> urlList;

    @FindBy(id="credUsername")
    private List<WebElement> usernameList;

    @FindBy(id="credPassword")
    private List<WebElement> passwordList;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    //@FindBy(id="credentialSubmit")
    //private WebElement submitButton;

    @FindBy(id="credential-modal-submit")
    private WebElement submitModalButton;

    private final WebDriver driver;

    public CredentialsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getCredentialSize() {
        return this.urlList.size();
    }

    public void deleteCredential(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialsTab);

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", deleteButton.get(0));
    }

    public List<String> getDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialsTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        List<String> detail = new ArrayList<>(
                List.of(urlList.get(0).getText(), usernameList.get(0).getText(), passwordList.get(0).getText()));
        return detail;
    }

    public void editCredential(WebDriver driver, String newurl, String sameusername, String samepassword) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialsTab);

        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0))));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editButton.get(0));

        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + newurl + "';", credentialUrl);

        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + sameusername + "';", credentialUsername);

        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + samepassword + "';", credentialPassword);

        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", this.submitModalButton);
    }

    public void addCredential(WebDriver driver, String url, String username, String password, WebElement cred) {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialsTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addButton);

        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + url + "';", credentialUrl);
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + username + "';", credentialUsername);
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + password + "';", credentialPassword);


        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", submitModalButton);
    }
}
