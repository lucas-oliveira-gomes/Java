package br.com.lucas.tests.google.pages;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class ResultPage {
	
	public String title() {
		return Selenide.title();
	}

	public SelenideElement searchInput() {
		return $(By.name("q"));
	}

	public SelenideElement searchButton() {
		return $(By.name("btnK"));
	}
}
