package com.jexecutioner.sorters;

import java.util.*;


import com.jexecutioner.contracts.IOrderedItem;

public class OrderComparer implements Comparator<IOrderedItem> {

	@Override
	public int compare(final IOrderedItem itemA, final IOrderedItem itemB) {
		if (itemA.getOrder() < itemB.getOrder()) return -1;
		else if (itemA.getOrder() == itemB.getOrder()) return 0;
		else return 1;
	}

}
