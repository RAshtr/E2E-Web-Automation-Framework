package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.WikipediaPage;

public class WikipediaTest extends BaseTest {

    @Test
    public void searchTest() {

        WikipediaPage wiki = new WikipediaPage(driver);
        wiki.search("Selenium");
    }
}