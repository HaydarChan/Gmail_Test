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

    @Test(dependsOnMethods = "testDeleteEmail", description = "Verify that the current page is Trash")
    public void testAtTrashPage() {
        Logger.info("Navigating to Trash...");
        mailPage.goToTrash();

        boolean atTrash = retry(() -> trashPage.isAtTrash(), 3);
        Assert.assertTrue(atTrash, "Not currently in Trash page.");
        Logger.info("Successfully arrived at Trash page.");
    }

    @Test(dependsOnMethods = "testAtTrashPage", description = "Verify email appears in Trash")
    public void testEmailMovedToTrash() {
        Logger.info("Verifying email '" + subject + "' exists in Trash...");
        boolean foundInTrash = retry(() -> trashPage.isEmailInTrash(subject), 3);
        Assert.assertTrue(foundInTrash, "Deleted email was not found in Trash.");
        Logger.info("Email successfully found in Trash.");
    }

    @Test(dependsOnMethods = "testEmailMovedToTrash", description = "Delete two emails using checkbox and verify both appear in Trash")
    public void testDeleteMultipleEmails() {
        Logger.info("Navigating back to Inbox...");
        mailPage.goToInbox();
        Assert.assertTrue(mailPage.isAtInbox(), "Not at Inbox page.");

        Logger.info("Retrieving inbox email subjects...");
        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertTrue(subjects.size() >= 3, "Need at least 3 emails to delete 2 more.");

        List<String> targets = subjects.subList(0, 2);
        Logger.info("Selected emails for deletion via checkbox: " + targets);

        mailPage.selectFirstNEmailsAndDelete(2);

        Logger.info("Navigating to Trash...");
        mailPage.goToTrash();
        Assert.assertTrue(trashPage.isAtTrash(), "Failed to navigate to Trash.");

        for (String subj : targets) {
            Logger.info("Verifying presence in Trash: " + subj);
            Assert.assertTrue(trashPage.isEmailInTrash(subj), "Email not found in Trash: " + subj);
        }

        Logger.info("Successfully deleted and verified 2 emails in Trash.");
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
