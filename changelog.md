# Week 3
## 19/04/2019
* added activities
* created project
* begun work on graphics


# Week 4
## 27/04/2019
* completed icons
* completed splash screen graphic

## Henry notes
- Missing significant progress recently.
- Deakin requires a minimum of 10-14 hours per week, per unit. Please work more days (and say which day)

# Week 5
## 4/04/2019 
* implemented video background on intro page (place holder background)
* set out initial layouts for all features
* naming conventions applied to activities, classes, layouts

## 5/04/2019 
* layouts for the activities for one of the main features is now developed, can be used to test account system functionalities
* user object is now created, contains attributes as discussed in the project proposal
* splash screen is now active, current graphic needs to be replaced

## 6/04/2019 
* buttons now functional (all features): transitions the screen to the targeted/relevant activity
* layouts complete for all other features (can now be used to test feature implementations)
* remaining classes set up (budget, expenses, accounts)

## 7/04/2019 
* users can now create accounts (edit texts are now saved and imported into a user class)
* initial validation available, needs to be extended upon

## Wednesday 3/April [Henry]
- Update this file each day you work
- Include multi-line comments (/* comment here */) before each function, outlining what the function does, and how to call it, and what to do with what it returns.

# Week 6

## Monday 8/April [Henry]
- Good to see more progress, I like it.
- Classes need to start with a capital letter, and their filenames (standard practice for programming)

## Monday 11/April [Henry]
- Repeating last comments.

## 12/04/2019
- changed class names to the appropriate naming conventions
- allowed for information between layout changes to be retained (username, pword, email - saved in variables)
- changed several functions to return a boolean value

## Week 7, Monday 15 April
- Feedback: Good to see some more progress. [Henry]
- Feedback: Some functions still missing function comments. This is a major problem [Henry]
- Feedback: Some of your parameter names I cannot understand. E.g. "intro_account_validate(String s1, String s2, String s3, String s4, String s5, String s6)". What are s1, s2, s3, etc? [Henry]
- Feedback: I feel your project is falling a lot behind, and you're not putting in enough time to pass. Show me 10-14 hours each per week.. where was it spent? I'd like you to start writing the time taken per changelog item (in brackets at the end of each line), so that you can see where time is being spent / not being spent enough. [Henry]

## 15/04/2019
- added comments to the functions that were lacking it (15 min)

## 16/04/2019
- new activity created to fix the issue of data not being updated in the second layout, intents are now used to pass the data, with the new activity adopting the second layout (1 hour)
- created a function to write the user object to a file (30 min)

## 17/04/2019
- changed edit text placeholder texts to correspond to the type of input required (10 min)
- added validation for both passwords to match (10 min)
- general changes surrounding bug fixing and layout sizing changes (30 min)

## 18/04/2019
- created login function (read from user object) /w validation, users can now login after creating their account (2 hours)
- buttons perform their relevant functions in the login activity (20 min)

## 27/04/2019
- changed the read and write functions to accept a arraylist of accounts, added serialization to user class, and the app now retains user information (no issue of overwritten files, which was found in the previous build) (1 hour)
- corrected placeholder text and adjusted/rearraged incorrect user class values (password mixed with email, etc) (30 min)
- changed onClick: created a for loop to traverse across multiple accounts to find the correct match (30 min)
- removed action bar from the application as it was interfering with general layout proportions (10 min)

## 28/04/2019
- secondary feature: created a layout/activity to list the user's personal details, where they can view their current information and change their password/pincode (1 hour)
- values from the passed down user object is assigned to the personal layout textviews, buttons are assigned (30 min)
- welcome message added on the home screen with the user's first name (10 min)
- more documentation/comments added (20 min)

# Week 8
## 29/04/2019
- user can now successfully change their password/pincode and update their account (user class), user detail change layout and activity created, function created to update user details with minor validation (1 hour 30min)


## 4/05/2019
- created interchangeable layout for main feature (budget planning) (1 hour)

## 5/05/2019
- main feature: the user can now create expenses that are tied to their accounts (expense object saved under the user object), included functionality to overwrite files, implemented date picker (4 hours)
- comments/documentation (continued) (30 min)
- added attributes/variables/constructor to expenses class (30 min)
- added possible entries for spinner in values file (20 min)
# Week 9
## 6/05/2019
- main feature: layout created for account creation process, activities made (budget/account creation) (40 min)

## 9/05/2019
- main feature: updated account class to include necessary details, implementation and layout for account creation complete (read/write), added intent passing to all activities, entry values created for spinners (2 hours)

## 10/05/2019

[Henry]
- Good progress. Glad to see it's getting closer. Final stretch now. 
- Make sure you go over everything in the Grading Criteria super carefully, to ensure you meet each point.


