package com.jexecutioner.contracts;

import java.util.*;

public interface IExecutioner {

	List<ScriptDocument> getScriptDocuments();
	ExecutionResult run(ExecutionRequest request);

}
