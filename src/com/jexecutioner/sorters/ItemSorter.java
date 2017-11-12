package com.jexecutioner.sorters;

import java.util.*;

import com.jexecutioner.contracts.IOrderedItem;

public class ItemSorter implements ISorter {
	
	private List<Comparator<IOrderedItem>> _comparers;

	public ItemSorter(List<Comparator<IOrderedItem>> comparers) { _comparers = comparers; }

	@Override
	public List<? extends IOrderedItem> sort(List<? extends IOrderedItem> collection) {
		Comparator<IOrderedItem> newComparer = null;

		if (_comparers.size() == 1) newComparer = _comparers.get(0);
		else if (_comparers.size() > 1) newComparer = buildCompoundComparer(_comparers);
		
		List<IOrderedItem> newList = new ArrayList<IOrderedItem>();
		collection.stream()
			.sorted(newComparer)
			.forEach(item -> newList.add(item));
		
		return newList;
	}

	private Comparator<IOrderedItem> buildCompoundComparer(List<Comparator<IOrderedItem>> comparers) {
			Comparator<IOrderedItem> newComparer = null;
			for (short i = 0; i < comparers.size(); ++i) {
				if ((i + 1) < comparers.size()) {
					newComparer = comparers.get(i).thenComparing(comparers.get(i + 1));
				}
			}
			
			return newComparer;
	}
}
