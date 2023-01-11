package br.com.lucas.tests;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import java.io.PrintWriter;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class Teste {

	public static void main(String[] args) {
		opcao1();
	}
	
	private static void opcao2() {
		SummaryGeneratingListener listener = new SummaryGeneratingListener();
		LauncherDiscoveryRequest request = request()
				.selectors(selectClass("br.com.lucas.tests.google.BuscaMeteorito"))
				.build();
		Launcher launcher = LauncherFactory.create();
		launcher.discover(request);
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);
		
		TestExecutionSummary summary = listener.getSummary();
		summary.printTo(new PrintWriter(System.out));
	}
	private static void opcao1() {
		OutroTeste ot = new OutroTeste();
		ot.runTest();
	}
}
