package com.jexecutioner.contracts;

import java.util.List;

import com.jexecutioner.sorters.ISorter;

public class MockSorter implements ISorter {

	@Override
	public List<IOrderedItem> sort(List<IOrderedItem> collection) {
		return collection;
	}

}
