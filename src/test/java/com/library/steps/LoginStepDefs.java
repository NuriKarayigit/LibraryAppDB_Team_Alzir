package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefs {

    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String actualUserName;
    String email;

    @Given("the user logged in as  {string}")
    public void theUserLoggedInAs(String userType) {
        String email = null;
        String password = null;
        if(userType.equalsIgnoreCase("student")){
            email= ConfigurationReader.getProperty("student_username");
            password=ConfigurationReader.getProperty("student_password");
        } else if (userType.equalsIgnoreCase("librarian")) {
            email=ConfigurationReader.getProperty("librarian_username");
            password=ConfigurationReader.getProperty("librarian_password");

        }
    }


    @When("user gets username  from user fields")
    public void user_gets_username_from_user_fields() {
        actualUserName = dashBoardPage.accountHolderName.getText();
        System.out.println("actualUserName = " + actualUserName);


    }
    @Then("the username should be same with database")
    public void the_username_should_be_same_with_database() {

        //get data from database
        String query = "select full_name from users where email='"+email+"'";
        DB_Util.runQuery(query);
        String expectedUserName = DB_Util.getFirstRowFirstColumn();

        System.out.println("expectedUserName = " + expectedUserName);

        //compare actual vs expected
        Assert.assertEquals(expectedUserName,actualUserName);



    }


}
