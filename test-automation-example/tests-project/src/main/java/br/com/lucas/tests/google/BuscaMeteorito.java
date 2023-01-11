package br.com.lucas.tests.google;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Configuration;

import br.com.lucas.tests.google.pages.HomePage;

public class BuscaMeteorito {

	@Test
	void testeBuscaMeteorito() {
		Configuration.browser = "firefox";
		Configuration.remote = "http://localhost:4444/wd/hub";
		final String searchTerm = "Meteorito";
		final String resultPageTitle = HomePage.open().searchFor(searchTerm).title();

		assertTrue(resultPageTitle.startsWith(searchTerm));
	}
}
