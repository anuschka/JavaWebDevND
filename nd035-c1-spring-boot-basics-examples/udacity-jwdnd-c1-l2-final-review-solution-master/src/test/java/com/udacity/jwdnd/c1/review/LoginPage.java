package com.udacity.jwdnd.c1.review;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id ="inputUsername")
    private WebElement usernameField;

    @FindBy(id ="inputPassword")
    private WebElement passwordField;

    @FindBy(id ="submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void signup(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }
}
