package com.jexecutioner.sorters;

import java.util.*;

import com.jexecutioner.contracts.IOrderedItem;

public class DateComparer implements Comparator<IOrderedItem> {
	
	@Override
	public int compare(final IOrderedItem itemA, final IOrderedItem itemB) {
		return itemA.getDateCreatedUtc().compareTo(itemB.getDateCreatedUtc());
	}

}
