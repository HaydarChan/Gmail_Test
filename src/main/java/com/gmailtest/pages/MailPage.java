package com.gmailtest.pages;

import com.gmailtest.utils.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MailPage extends BasePage {

    private final By inboxHeader = By.xpath("//h2[@title='Inbox']");
    private final By trashNavButton = By.cssSelector("a[data-testid='navigation-link:trash']");
    private final By deleteButton = By.cssSelector("button[data-testid='message-header-expanded:move-to-trash']");
    private final By moreButton = By.cssSelector("button[title='More'][data-shortcut-target='toggle-more-items']");
    private final By inboxNavButton = By.cssSelector("a[data-testid='navigation-link:inbox']");
    private final By bulkDeleteButton = By.cssSelector("button[data-testid='toolbar:movetotrash']");
    private final By emailCheckboxes = By.cssSelector("input[data-testid='item-checkbox']");
    private final By selectAllInboxCheckbox = By.cssSelector("input#idSelectAll[data-testid='toolbar:select-all-checkbox']");
    private final By moveToTrashButton = By.cssSelector("button[data-testid='toolbar:movetotrash']");


    public boolean isAtInbox() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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

    public List<String> getAllInboxEmailSubjects() {
        By subjectLocator = By.cssSelector("span[data-testid='message-row:subject']");

        if (!isAtInbox()) {
            throw new IllegalStateException("Not at Inbox page.");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(subjectLocator));

        return driver.findElements(subjectLocator).stream()
                .map(element -> element.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public void openEmailBySubject(String subject) {
        By emailSubject = By.xpath("//span[@data-testid='message-row:subject' and @title='" + subject + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(emailSubject));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        click(deleteButton);
    }

    public void goToTrash() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement more = wait.until(ExpectedConditions.elementToBeClickable(moreButton));
            if (more.isDisplayed()) {
                more.click();
                Logger.info("Clicked 'More' to expand hidden sidebar items.");
            }
        } catch (Exception e) {
            Logger.warn("'More' button not found or already expanded.");
        }

        wait.until(ExpectedConditions.elementToBeClickable(trashNavButton));
        click(trashNavButton);
    }

    public void goToInbox() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(inboxNavButton));
        click(inboxNavButton);
    }

    public void selectFirstNEmailsAndDelete(int count) {
        Logger.info("Selecting first " + count + " emails via checkbox...");
        List<WebElement> checkboxes = driver.findElements(emailCheckboxes);

        if (checkboxes.size() < count) {
            throw new IllegalStateException("Only " + checkboxes.size() + " emails found. Need at least " + count);
        }

        for (int i = 0; i < count; i++) {
            checkboxes.get(i).click();
        }

        Logger.info("Waiting for bulk delete button to be clickable...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(bulkDeleteButton)).click();
        Logger.info("Clicked 'Move to trash' toolbar button.");
    }

    public void moveAllInboxEmailsToTrash() {
        Logger.info("Selecting all emails in Inbox...");
        click(selectAllInboxCheckbox);

        Logger.info("Clicking 'Move to Trash'...");
        click(moveToTrashButton);
    }
}
