package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.BorrowedBooksPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class US07_tay {

    LoginPage loginPage=new LoginPage();
    BookPage bookPage=new BookPage();
    String bookname;
    List<WebElement> actual;
    List<String > emptyactual=new ArrayList<>();
    BorrowedBooksPage borrowedBooksPage=new BorrowedBooksPage();
    @Given("the {string} on the home page")
    public void the_on_the_home_page(String student) {
        loginPage.login("student56@library","libraryUser");
        BrowserUtil.waitFor(1);
    }
    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String books) {
        bookPage.navigateModule(books);
        BrowserUtil.waitFor(1);
    }
    @Given("the user searches for {string} book")
    public void the_user_searches_for_book(String bookname) {
        this.bookname=bookname;
        bookPage.search.sendKeys(bookname);
        BrowserUtil.waitFor(1);
    }
    @When("the user clicks Borrow Book")
    public void the_user_clicks_borrow_book() {
WebElement clickme=Driver.getDriver().findElement(By.xpath("//td[.='"+bookname+"']"));
clickme.click();
BrowserUtil.waitFor(1);
       // bookPage.borrowBook(bookname).click();
      // Assert.assertTrue( bookPage.toastMessage.isDisplayed());
    }
    @Then("verify that book is shown in {string} page")
    public void verify_that_book_is_shown_in_page(String bk) {
        bookPage.navigateModule(bk);

        actual=borrowedBooksPage.allBorrowedBooksName;
        List<String > emptyactual=new ArrayList<>();
        for (WebElement each : actual) {
            emptyactual.add(String.valueOf(each));

        }

        BrowserUtil.waitFor(1);


    }
    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {
        DB_Util.createConnection();
        DB_Util.runQuery("select full_name, b.name, bb.borrowed_date\n" +
                "from users u\n" +
                "         inner join book_borrow bb on u.id = bb.user_id\n" +
                "         inner join books b on bb.book_id = b.id\n" +
                "where full_name = 'Test Student 56'\n" +
                "order by 3 desc");
       List<String> exptected=DB_Util.getColumnDataAsList(2);
        List<String> actual=BrowserUtil.getElementsText(borrowedBooksPage.allBorrowedBooksName);
        System.out.println("exptected = " + Arrays.asList(exptected));
        System.out.println("actual = " + actual);
        Assert.assertEquals(actual.contains(bookname),exptected.contains(bookname));

    }


}
