package com.jexecutioner.contracts;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

class ScriptLoaderTests {

	private ZonedDateTime _now;

	@BeforeEach
	void setup() {
		_now = ZonedDateTime.now(ZoneOffset.UTC);
	}
	
	@Test
	void scriptLoaderUtilityReturnsScriptDocument() {
		try {
			ScriptDocument sDoc = getScriptDocument(_now, 0, new Script[] {});
			Assert.assertTrue(sDoc != null);
		}
		catch (Exception err) {
			fail("Test Failed: " + err.getMessage());
		}
	}
	
	@Test
	void scriptLoaderUtilityReturnsFilledInScriptDocument() {
		try {
			ScriptDocument sDoc = getScriptDocument(_now, 0, new Script[] {});
			Assert.assertTrue(sDoc.getDateCreatedUtc().equals(_now));
		}
		catch (Exception err) {
			String msg = "Exception: " + err.getClass().getSimpleName();
			msg += "Message: " + err.getMessage();
			fail(msg);
		}
	}
	
	private ScriptDocument getScriptDocument(ZonedDateTime date, int order, Script[] scripts) 
		throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder();

		sb.append("<ScriptDocument>");
		sb.append("<Id>" + UUID.randomUUID() + "</Id>");
		sb.append("<Order>" + formatter.format(date) + "</Order>");
		sb.append("<Scripts></Scripts>");
		sb.append("</ScriptDocument>");
		
		InputStream iStream = new ByteArrayInputStream(sb.toString().getBytes());
		return ScriptLoaderUtilities.createScriptDocument(iStream, new String(""));
	}

}
