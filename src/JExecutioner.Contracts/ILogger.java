package com.jexecutioner.contracts;

import java.util.*;

public interface ILogger {

	List<UUID> getCompletedDocumentIds();
	List<UUID> getCompletedScriptIdsFor(UUID documentId);
	void add(ScriptDocument document);
	void add(Script script);
	void update(Script script);
	void clean();

}
