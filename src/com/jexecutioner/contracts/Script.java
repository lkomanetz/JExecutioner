package com.jexecutioner.contracts;

import java.util.*;

import java.time.*;
import java.time.format.*;

public class Script implements IOrderedItem {

	private UUID _sysId;
	private UUID _documentId;
	private int _order;
	private ZonedDateTime _dateCreatedUtc;
	private String _scriptText;
	private String _executorName;
	private Boolean _isComplete;

	public Boolean isScriptComplete() { return _isComplete; }
	public void setScriptComplete(Boolean value) { _isComplete = value; }

	public String getExecutorName() { return _executorName; }
	public void setExecutorName(String value) { _executorName = value; }

	public String getScriptText() { return _scriptText; }
	public void setScriptText(String value) { _scriptText = value; }

	public int getOrder() { return _order; }
	public void setOrder(int value ) { _order = value; }

	public UUID getSysId() { return _sysId; }
	public void setSysId(UUID value) { _sysId = value; }

	public UUID getDocumentId() { return _documentId; }
	public void setDocumentId(UUID value) { _documentId = value; }

	public ZonedDateTime getDateCreatedUtc() { return _dateCreatedUtc; }
	public void setDateCreatedUtc(ZonedDateTime value) { _dateCreatedUtc = value; }
	
	@Override
	public int hashCode() {
		return _sysId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		Script scriptB = (obj instanceof Script) ? (Script)obj : null;
		if (scriptB == null) return false;
		return isEqual(this, scriptB);
	}
	
	@Override
	public String toString() {
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String orderStr = dtFormat.format(_dateCreatedUtc);
		if (_order != 0) orderStr += String.format(":%d", _order);

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<Script Id='%s' Executor='%s' Order='%s'>\n", _sysId, _executorName, orderStr));
		sb.append((_scriptText != null) ? _scriptText : "");
		sb.append("</Script>\n");
		
		return sb.toString();
	}

	private boolean isEqual(Script scriptA, Script scriptB) {
		return (
			scriptA.getSysId().equals(scriptB.getSysId()) &&
			scriptA.isScriptComplete().equals(scriptB.isScriptComplete()) &&
			scriptA.getDateCreatedUtc().equals(scriptB.getDateCreatedUtc()) &&
			scriptA.getExecutorName().equals(scriptB.getExecutorName()) &&
			scriptA.getDocumentId().equals(scriptB.getDocumentId()) &&
			scriptA.getOrder() == scriptB.getOrder()
		);
	}
}
