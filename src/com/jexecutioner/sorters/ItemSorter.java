package com.jexecutioner.sorters;

import java.util.*;

import com.jexecutioner.contracts.IOrderedItem;

public class ItemSorter implements ISorter {

	@Override
	public List<IOrderedItem> sort(List<IOrderedItem> collection, List<Comparator<IOrderedItem>> comparers) {
		Comparator<IOrderedItem> newComparer = null;

		if (comparers.size() == 1) newComparer = comparers.get(0);
		else if (comparers.size() > 1) newComparer = buildCompoundComparator(comparers);
		
		List<IOrderedItem> newList = new ArrayList<IOrderedItem>();
		collection.stream()
			.sorted(newComparer)
			.forEach(item -> newList.add(item));
		
		return newList;
	}

	private Comparator<IOrderedItem> buildCompoundComparator(List<Comparator<IOrderedItem>> comparers) {
			Comparator<IOrderedItem> newComparer = null;
			for (short i = 0; i < comparers.size(); ++i) {
				if ((i + 1) < comparers.size()) {
					newComparer = comparers.get(i).thenComparing(comparers.get(i + 1));
				}
			}
			
			return newComparer;
	}
}
