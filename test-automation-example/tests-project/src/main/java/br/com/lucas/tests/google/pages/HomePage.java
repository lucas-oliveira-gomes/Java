package br.com.lucas.tests.google.pages;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class HomePage {
	public static final String HOMEPAGE = "https://www.google.com";

	public static HomePage open() {
		HomePage toReturn = new HomePage();
		Selenide.open(HOMEPAGE);
		return toReturn;
	}

	public String title() {
		return Selenide.title();
	}

	public SelenideElement searchInput() {
		return $(By.name("q"));
	}

	public SelenideElement searchButton() {
		return $(By.name("btnK"));
	}

	public ResultPage searchFor(String term) {
		searchInput().setValue(term);
		searchButton().click();
		return new ResultPage();
	}
}
