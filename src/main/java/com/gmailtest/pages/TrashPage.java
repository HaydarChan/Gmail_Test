package com.gmailtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TrashPage extends BasePage {

    private final By trashMailItems = By.cssSelector("div[data-testid='message-list-item']");

    private By emailSubject(String subject) {
        return By.cssSelector("span[data-testid='message-row:subject'][title='" + subject + "']");
    }

    public boolean isEmailInTrash(String subject) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailSubject(subject)));
            return find(emailSubject(subject)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void permanentlyDeleteEmail(String subject) {
        // Implementasi ini butuh context menu atau tombol delete di Trash
        // Belum ditampilkan di screenshot. Placeholder method.
    }

    public List<WebElement> getAllTrashEmails() {
        return driver.findElements(trashMailItems);
    }
}
