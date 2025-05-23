# Proton Mail Deletion Automation Test Cases

This document summarizes the automated test cases executed using Selenium and TestNG to verify Proton Mail's email deletion and restoration functionalities.

## How to Run

Ensure you have Maven installed, then run the following command from the root of the project:

```bash
mvn clean test
```

This will compile the tests and execute them using TestNG.

## 🎥 Test Run Video

🔗 [Google Drive - Test Execution Video](https://drive.google.com/drive/u/0/folders/1fab2k6AbTr2kfbbWMQE6U39OPlgVbNR1)

## Test Cases

| Test Case ID | Test Scenario                                     | Test Steps                                                                                           | Test Data                                 | Expected Result                          | Actual Result                                             | Status |
|--------------|--------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------------------------------|------------------------------------------|------------------------------------------------------------|--------|
| TC001        | Login to Proton Mail                             | 1. Launch Proton Mail<br>2. Enter credentials<br>3. Click login                                       | Username: qiyachan@proton.me<br>Password: qiyachan123 | User is redirected to the Inbox page    | Successfully landed in Inbox                              | Pass   |
| TC002        | Check email presence in Inbox                    | 1. Check total number of emails in Inbox                                                              | N/A                                       | At least 5 emails found in inbox         | Found 12 emails in the inbox                              | Pass   |
| TC003        | Set and open email by subject                    | 1. Get first email subject<br>2. Open email with that subject                                         | Subject: test email 9                      | Email opens successfully                 | Email opened and deleted                                   | Pass   |
| TC004        | Move opened email to Trash                       | 1. Delete currently opened email                                                                      | Subject: test email 9                      | Email is moved to Trash                  | Email deleted and found in Trash                          | Pass   |
| TC005        | Delete multiple emails via checkbox              | 1. Select two emails via checkbox<br>2. Click delete<br>3. Verify in Trash                            | Subjects: test email 7, test email 6       | Both emails moved to Trash              | Successfully verified both emails in Trash                | Pass   |
| TC006        | Move all emails from Trash back to Inbox         | 1. Select all in Trash<br>2. Move to Inbox<br>3. Verify Inbox count                                   | All emails in Trash                        | Emails restored to Inbox                 | Inbox now contains: 12 emails                              | Pass   |
| TC007        | Move all Inbox emails to Trash and back          | 1. Move all Inbox emails to Trash<br>2. Restore from Trash                                            | All emails in Inbox                        | All emails restored to Inbox            | All emails successfully restored to Inbox                 | Pass   |
| TC008        | Verify toolbar delete button is hidden           | 1. Navigate to Inbox<br>2. Ensure no email selected<br>3. Check button visibility                     | N/A                                       | Delete button should not be visible     | Delete button correctly not visible without selection     | Pass   |
| TC009        | Restore deleted email from Trash via toolbar     | 1. Delete an email<br>2. Open it in Trash<br>3. Restore via toolbar                                   | Subject: test email 9                      | Email restored to Inbox                  | Email successfully restored to Inbox from Trash detail view| Pass   |
| TC010        | Permanently delete email from Trash              | 1. Delete email<br>2. Open in Trash<br>3. Permanently delete                                          | Subject: test email 9                      | Email permanently removed from Trash     | Email permanently deleted and confirmed removed from Trash| Pass   |

---

## Execution Log

```
[2025-05-23 19:47:19] INFO    - ===== Test: Login to Proton =====
[2025-05-23 19:47:19] INFO    - Starting login test to Proton Mail
[2025-05-23 19:47:33] INFO    - Successfully landed in Inbox
[2025-05-23 19:47:33] INFO    - ===== Test: Verify email presence in Inbox =====
[2025-05-23 19:47:33] INFO    - Checking that there are at least 5 emails in the inbox...
[2025-05-23 19:47:34] INFO    - Found 12 emails in the inbox.
[2025-05-23 19:47:34] INFO    - ===== Test: Set subject from Inbox =====
[2025-05-23 19:47:34] INFO    - Grabbing first available email subject from inbox...
[2025-05-23 19:47:34] INFO    - Selected email subject: test email 9
[2025-05-23 19:47:34] INFO    - ===== Test: Open email by subject =====
[2025-05-23 19:47:34] INFO    - Opening email with subject: test email 9
[2025-05-23 19:47:34] INFO    - ===== Test: Delete opened email =====
[2025-05-23 19:47:34] INFO    - Deleting the opened email...
[2025-05-23 19:47:35] INFO    - Email deleted, expected to be moved to Trash.
[2025-05-23 19:47:35] INFO    - ===== Test: Navigate to Trash =====
[2025-05-23 19:47:35] INFO    - Navigating to Trash...
[2025-05-23 19:47:35] INFO    - Clicked 'More' to expand hidden sidebar items.
[2025-05-23 19:47:35] INFO    - Successfully arrived at Trash page.
[2025-05-23 19:47:35] INFO    - ===== Test: Email appears in Trash =====
[2025-05-23 19:47:35] INFO    - Verifying email 'test email 9' exists in Trash...
[2025-05-23 19:47:36] INFO    - Email successfully found in Trash.
[2025-05-23 19:47:36] INFO    - ===== Test: Delete multiple emails via checkbox =====
[2025-05-23 19:47:36] INFO    - Navigating back to Inbox...
[2025-05-23 19:47:37] INFO    - Retrieving inbox email subjects...
[2025-05-23 19:47:37] INFO    - Selected emails for deletion via checkbox: [test email 7, test email 6]
[2025-05-23 19:47:37] INFO    - Selecting first 2 emails via checkbox...
[2025-05-23 19:47:37] INFO    - Waiting for bulk delete button to be clickable...
[2025-05-23 19:47:37] INFO    - Clicked 'Move to trash' toolbar button.
[2025-05-23 19:47:37] INFO    - Navigating to Trash...
[2025-05-23 19:47:48] WARNING - 'More' button not found or already expanded.
[2025-05-23 19:47:48] INFO    - Verifying presence in Trash: test email 7
[2025-05-23 19:47:48] INFO    - Verifying presence in Trash: test email 6
[2025-05-23 19:47:48] INFO    - Successfully deleted and verified 2 emails in Trash.
[2025-05-23 19:47:48] INFO    - ===== Test: Move emails from Trash back to Inbox =====
[2025-05-23 19:47:48] INFO    - Navigating to Trash...
[2025-05-23 19:47:58] WARNING - 'More' button not found or already expanded.
[2025-05-23 19:47:58] INFO    - Selecting all emails in Trash...
[2025-05-23 19:47:59] INFO    - Waiting for 'Move to Inbox' button to become clickable...
[2025-05-23 19:47:59] INFO    - Clicking 'Move to Inbox'...
[2025-05-23 19:47:59] INFO    - Navigating to Inbox to verify...
[2025-05-23 19:47:59] INFO    - Inbox now contains: 12 emails.
[2025-05-23 19:47:59] INFO    - ===== Test: Move ALL Inbox ➜ Trash ➜ Inbox =====
[2025-05-23 19:47:59] INFO    - Navigating to Inbox...
[2025-05-23 19:48:00] INFO    - Selecting all emails in Inbox...
[2025-05-23 19:48:00] INFO    - Clicking 'Move to Trash'...
[2025-05-23 19:48:00] INFO    - Navigating to Trash...
[2025-05-23 19:48:10] WARNING - 'More' button not found or already expanded.
[2025-05-23 19:48:11] INFO    - Selecting all emails in Trash...
[2025-05-23 19:48:11] INFO    - Waiting for 'Move to Inbox' button to become clickable...
[2025-05-23 19:48:11] INFO    - Clicking 'Move to Inbox'...
[2025-05-23 19:48:11] INFO    - Returning to Inbox to verify...
[2025-05-23 19:48:12] INFO    - All emails successfully restored to Inbox.
[2025-05-23 19:48:12] INFO    - ===== Test: Toolbar delete button hidden with no selection =====
[2025-05-23 19:48:12] INFO    - Delete button correctly not visible without selection.
[2025-05-23 19:48:12] INFO    - ===== Test: Delete one email and restore from Trash via toolbar =====
[2025-05-23 19:48:12] INFO    - Target email to delete and restore: test email 9
[2025-05-23 19:48:23] WARNING - 'More' button not found or already expanded.
[2025-05-23 19:48:24] INFO    - Email successfully restored to Inbox from Trash detail view.
[2025-05-23 19:48:24] INFO    - ===== Test: Permanently delete email from Trash detail view =====
[2025-05-23 19:48:24] INFO    - Deleting email: test email 9
[2025-05-23 19:48:35] WARNING - 'More' button not found or already expanded.
[2025-05-23 19:48:35] INFO    - Clicking 'Delete permanently' from detail view...
[2025-05-23 19:48:36] INFO    - Confirming permanent delete...
[2025-05-23 19:48:50] INFO    - Email permanently deleted and confirmed removed from Trash.
[2025-05-23 19:48:50] INFO    - Shutting down WebDriver and closing log file...
```
