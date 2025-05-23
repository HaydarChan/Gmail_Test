package com.gmailtest.pages;

import com.gmailtest.utils.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TrashPage extends BasePage {

    private final By trashHeader = By.xpath("//h2[@title='Trash']");
    private final By trashMailItems = By.cssSelector("div[data-testid='message-list-item']");
    private final By selectAllCheckbox = By.cssSelector("input#idSelectAll[data-testid='toolbar:select-all-checkbox']");
    private final By moveToInboxButton = By.cssSelector("button[data-testid='toolbar:movetoinbox']");



    private By emailSubject(String subject) {
        return By.cssSelector("span[data-testid='message-row:subject'][title='" + subject + "']");
    }

    public boolean isAtTrash() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(trashHeader));
            WebElement header = find(trashHeader);
            return header.isDisplayed() && header.getText().equalsIgnoreCase("Trash");
        } catch (Exception e) {
            return false;
        }
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

    public void moveAllTrashEmailsToInbox() {
        Logger.info("Selecting all emails in Trash...");
        click(selectAllCheckbox);

        Logger.info("Waiting for 'Move to Inbox' button to become clickable...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(moveToInboxButton));
        
        Logger.info("Clicking 'Move to Inbox'...");
        btn.click();
    }
}
