package com.jexecutioner.contracts;

public class ExecutionResult {

	private int _scriptDocsCompleted;
	private int _scriptsCompleted;

	public int getScriptDocumentsCompletedCount() { return _scriptDocsCompleted; }
	public void setScriptDocumentsCompletedCount(int value) { _scriptDocsCompleted = value; }

	public int getScriptsCompletedCount() { return _scriptsCompleted; }
	public void setScriptsCompletedCount(int value) { _scriptsCompleted = value; }
	
	
	@Override
	public int hashCode() {
		return (++_scriptDocsCompleted * ++_scriptsCompleted) / 11;
	}
	
	@Override
	public boolean equals(Object obj) {
		ExecutionResult resultB = (obj instanceof ExecutionResult) ? (ExecutionResult)obj : null;
		if (resultB == null) return false;
		
		return resultB == this;
	}

}
