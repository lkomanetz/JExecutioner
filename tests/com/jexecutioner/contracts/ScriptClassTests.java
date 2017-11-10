package com.jexecutioner.contracts;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

class ScriptClassTests {
	private ZonedDateTime _now;
	private UUID _docId;
	private UUID _scriptId;
	private String _executorName;
	private int _order;
	
	@BeforeEach
	void setup() {
		_now = ZonedDateTime.now(ZoneOffset.UTC);
		_executorName = "SqlServerExecutor";
		_docId = UUID.randomUUID();
		_scriptId = UUID.randomUUID();
		_order = 0;
	}

	@Test
	void toStringFormatsProperlyWithoutOrder() {
		Script script = createScript();	
		String expectedStr = getExpectedString();
		Assert.assertTrue(
			expectedStr.equals(script.toString())
		);
	}
	
	@Test
	void toStringFormatsPropertyWithOrder() {
		Script script = createScript();
		script.setOrder(++_order);
		String expectedStr = getExpectedString();
		
		Assert.assertTrue(expectedStr.equals(script.toString()));
	}
	
	private String getExpectedString() {
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = dtFormatter.format(_now);
		StringBuilder expectedStr = new StringBuilder();
		String orderStr = (_order != 0) ? 
			String.format("%s:%d", formattedDate, _order) :
			String.format("%s", formattedDate);
		
		expectedStr.append(String.format("<Script Id='%s' Executor='%s' Order='%s'>\n", _scriptId, _executorName, orderStr));
		expectedStr.append("</Script>\n");
		return expectedStr.toString();
	}
	
	private Script createScript() {
		Script script = new Script();
		script.setSysId(_scriptId);
		script.setDateCreatedUtc(_now);
		script.setDocumentId(_docId);
		script.setExecutorName(_executorName);
		
		return script;
	}

}
