package com.jexecutioner.sorters;

import java.util.*;
import com.jexecutioner.contracts.*;

public interface ISorter {
	
	List<? extends IOrderedItem> sort(List<? extends IOrderedItem> collection);
	
}
