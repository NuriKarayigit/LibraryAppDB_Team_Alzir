Feature: Login Functionality

  @ui @db
  Scenario: Librarian logs in with librarian credentials
    Given the user logged in as  "<userType>"
    When user gets username  from user fields
    Then the username should be same with database





