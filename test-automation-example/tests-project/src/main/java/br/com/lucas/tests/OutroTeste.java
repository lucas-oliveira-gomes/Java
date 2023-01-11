package br.com.lucas.tests;

import com.codeborne.selenide.Configuration;

import br.com.lucas.tests.google.pages.HomePage;

public class OutroTeste {
	public void runTest() {
		Configuration.browser = "firefox";
		Configuration.remote = "http://localhost:4444/wd/hub";
		final String searchTerm = "Meteorito";
		final String resultPageTitle = HomePage.open().searchFor(searchTerm).title();
		System.out.println(resultPageTitle.startsWith(searchTerm));
	}
}