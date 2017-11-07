package com.jexecutioner.contracts;

import java.util.*;
import java.time.*;

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

}
