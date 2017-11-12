package com.jexecutioner.converters;

import com.jexecutioner.contracts.*;
import java.util.*;

public class Converters {
	
	public static final IConverter<List<Script>, List<? extends IOrderedItem>> toScripts = (items) -> {
		List<Script> scripts = new ArrayList<>();
		for (IOrderedItem item : items) {
			scripts.add((Script)item);
		}
		return scripts;
	};

}