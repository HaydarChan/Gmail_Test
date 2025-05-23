package com.gmailtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MailPage extends BasePage {
    private final By inboxHeader = By.xpath("//h2[@title='Inbox']");
    private final By mailItems = By.cssSelector("div[data-testid='message-list-item']");
    private final By trashNavButton = By.xpath("//span[text()='Trash']/ancestor::button | //a[contains(@href,'/trash')]");
    private final By deleteButton = By.xpath("//button[@aria-label='Delete']");

    public boolean isAtInbox() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(driver -> {
                try {
                    WebElement header = find(inboxHeader);
                    return header.isDisplayed() && header.getText().equalsIgnoreCase("Inbox");
                } catch (Exception ignored) {
                    return false;
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailWithSubjectPresent(String subject) {
        By emailSubject = By.xpath("//span[@data-testid='message-column:subject' and text()='" + subject + "']");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailSubject));
            return find(emailSubject).isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

    public void openEmailBySubject(String subject) {
        By emailSubject = By.xpath("//span[@data-testid='message-column:subject' and text()='" + subject + "']");
        click(emailSubject);
    }

    public boolean isEmailOpened(String subject) {
        By openedSubjectHeader = By.xpath("//h1[@data-testid='conversation-header:subject' and @title='" + subject + "']");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(openedSubjectHeader));
            return find(openedSubjectHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteOpenedEmail() {
        click(deleteButton);
    }

    public void goToTrash() {
        click(trashNavButton);
    }
}
