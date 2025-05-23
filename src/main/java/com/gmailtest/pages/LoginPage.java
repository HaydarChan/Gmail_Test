package com.gmailtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    private final By emailInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By signInButton = By.xpath("//button[contains(., 'Sign in') and @type='submit']");

    public void enterEmail(String email) {
        set(emailInput, email);
    }

    public void enterPassword(String password) {
        set(passwordInput, password);
    }

    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        click(signInButton);
    }
}
