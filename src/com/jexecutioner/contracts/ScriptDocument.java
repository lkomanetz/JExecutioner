package com.jexecutioner.contracts;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScriptDocument implements IOrderedItem {

	private UUID _sysId;
	private int _order;
	private List<Script> _scripts;
	private String _resourceName;
	private Boolean _isComplete;
	private ZonedDateTime _dateCreatedUtc;

	public UUID getSysId() { return _sysId; }
	public void setSysId(UUID value) { _sysId = value; }

	public int getOrder() { return _order; }
	public void setOrder(int value) { _order = value; }

	public List<Script> getScripts() { return _scripts; }
	public void setScripts(List<Script> value) { _scripts = value; }

	public String getResourceName() { return _resourceName; }
	public void setResourceName(String value) { _resourceName = value; }

	public Boolean isScriptDocumentComplete() { return _isComplete; }
	public void setScriptDocumentComplete(Boolean value) { _isComplete = value; }

	public ZonedDateTime getDateCreatedUtc() { return _dateCreatedUtc; }
	public void setDateCreatedUtc(ZonedDateTime value) { _dateCreatedUtc = value; }

}
