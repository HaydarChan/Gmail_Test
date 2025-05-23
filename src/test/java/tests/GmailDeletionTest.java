// GmailDeletionTest.java
package tests;

import com.gmailtest.utils.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GmailDeletionTest extends BaseTest {

    private String subject;

    @Test(description = "Verify user can login to Proton successfully")
    public void testLoginToProton() {
        Logger.info("Starting login test to Proton Mail");
        loginPage.loginAs("qiyachan@proton.me", "qiyachan123");
        Assert.assertTrue(mailPage.isAtInbox(), "Inbox page is not visible, login might have failed.");
        Logger.info("Successfully landed in Inbox");
    }

    @Test(dependsOnMethods = "testLoginToProton", description = "Verify email with specific subject exists in Inbox")
    public void testEmailPresenceInInbox() {
        Logger.info("Checking that there are at least 5 emails in the inbox...");
        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertTrue(subjects.size() >= 5, "Expected at least 5 emails, but found: " + subjects.size());
        Logger.info("Found " + subjects.size() + " emails in the inbox.");
    }

    @Test(dependsOnMethods = "testEmailPresenceInInbox", description = "Set target email subject from top of inbox")
    public void testSetEmailSubjectFromInbox() {
        Logger.info("Grabbing first available email subject from inbox...");
        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertFalse(subjects.isEmpty(), "No email subjects found in inbox.");
        subject = subjects.get(0);
        Logger.info("Selected email subject: " + subject);
    }

    @Test(dependsOnMethods = "testSetEmailSubjectFromInbox", description = "Verify email with specific subject can be opened")
    public void testOpenEmailBySubject() {
        Logger.info("Opening email with subject: " + subject);
        mailPage.openEmailBySubject(subject);

        boolean opened = retry(() -> mailPage.isEmailOpened(subject), 3);
        Assert.assertTrue(opened, "Email with subject '" + subject + "' could not be opened.");
    }

    @Test(dependsOnMethods = "testOpenEmailBySubject", description = "Delete the opened email")
    public void testDeleteEmail() {
        Logger.info("Deleting the opened email...");
        mailPage.deleteOpenedEmail();
        Logger.info("Email deleted, expected to be moved to Trash.");
    }

    @Test(dependsOnMethods = "testDeleteEmail", description = "Verify email appears in Trash")
    public void testEmailMovedToTrash() {
        Logger.info("Navigating to Trash...");
        mailPage.goToTrash();

        boolean foundInTrash = retry(() -> trashPage.isEmailInTrash(subject), 3);
        Assert.assertTrue(foundInTrash, "Deleted email was not found in Trash.");
        Logger.info("Email successfully found in Trash.");
    }

    private boolean retry(Check check, int attempts) {
        for (int i = 0; i < attempts; i++) {
            if (check.run()) return true;
            Logger.warn("Retrying... attempt " + (i + 1));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    private interface Check {
        boolean run();
    }
}
