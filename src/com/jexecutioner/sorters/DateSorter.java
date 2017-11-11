package com.jexecutioner.sorters;

import java.util.*;

import com.jexecutioner.contracts.IOrderedItem;

//TODO(Logan) -> Create unit tests around the date sorter
public class DateSorter implements ISorter {
	
	@Override
	public List<IOrderedItem> sort(List<IOrderedItem> collection) {
		collection
			.stream()
			.sorted((itemA, itemB) -> itemA.getDateCreatedUtc().compareTo(itemB.getDateCreatedUtc()));
		
		return collection;
	}

}
