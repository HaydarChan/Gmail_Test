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
    private final By toolbarMoveToInboxButton = By.cssSelector("button[data-testid='toolbar:movetoinbox']");
    private final By deletePermanentlyButton = By.cssSelector("button[data-testid='toolbar:deletepermanently']");
    private final By toolbarDeletePermanentlyButton = By.cssSelector("button[data-testid='toolbar:deletepermanently']");

    private final By confirmPermanentDeleteButton = By.cssSelector("button[data-testid='permanent-delete-modal:submit']");

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

    public void openEmailBySubjectInTrash(String subject) {
        By emailSubject = By.xpath("//span[@data-testid='message-row:subject' and @title='" + subject + "']");
        click(emailSubject);
    }

    public void moveOpenedEmailToInboxFromToolbar() {
        click(toolbarMoveToInboxButton);
    }

    public void selectAllInTrash() {
        click(selectAllCheckbox);
        Logger.info("Checkbox 'Select All' in Trash clicked.");
    }

    public void deleteSelectedPermanently() {
        click(deletePermanentlyButton);
        Logger.info("Clicked 'Delete permanently' in Trash toolbar.");
    }

    public void deleteOpenedEmailPermanently() {
        Logger.info("Clicking 'Delete permanently' from detail view...");
        click(toolbarDeletePermanentlyButton);

        Logger.info("Confirming permanent delete...");
        click(confirmPermanentDeleteButton);
    }
}
