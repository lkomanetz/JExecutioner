package com.jexecutioner.sorters;

import java.util.*;
import com.jexecutioner.contracts.*;

public interface ISorter {
	
	List<IOrderedItem> sort(List<IOrderedItem> collection, List<Comparator<IOrderedItem>> comparers);
	
}
