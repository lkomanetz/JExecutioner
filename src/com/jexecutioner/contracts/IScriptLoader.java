package com.jexecutioner.contracts;

import java.util.*;

public interface IScriptLoader {
	
	List<ScriptDocument> getDocument();
	void loadDocuments();

}
