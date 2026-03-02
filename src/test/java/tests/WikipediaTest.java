package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.WikipediaPage;

public class WikipediaTest extends BaseTest {

	@Test
	public void searchSeleniumTest() {

	    driver.get("https://www.wikipedia.org");

	    WikipediaPage wikiPage = new WikipediaPage(driver);
	    wikiPage.search("Selenium");

	    System.out.println("Current URL: " + driver.getCurrentUrl());
	    System.out.println("Page Title: " + driver.getTitle());

	    Assert.assertTrue(driver.getTitle().toLowerCase().contains("selenium"),
	            "Page title validation failed. Actual title: " + driver.getTitle());
	}
}