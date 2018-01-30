package com.jexecutioner.sorters;

import java.time.*;
import java.util.*;

import com.jexecutioner.contracts.IOrderedItem;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SorterTests {

	@Test
	void itemsSortedByDate() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		List<SimpleOrderedItem> items = new ArrayList<>();

		items.add(new SimpleOrderedItem('c', 0, now.plusDays(1)));
		items.add(new SimpleOrderedItem('d', 0, now.plusMonths(1)));
		items.add(new SimpleOrderedItem('b', 0, now));
		items.add(new SimpleOrderedItem('a', 0, now.minusDays(1)));
		
		List<Comparator<IOrderedItem>> comparers = new ArrayList<Comparator<IOrderedItem>>();
		comparers.add(new DateComparer());

		List<? extends IOrderedItem> sortedList = new ItemSorter(comparers).sort(items);
		assertOrder(sortedList, "abcd");
	}
	
	@Test
	void itemsSortedByOrder() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		List<SimpleOrderedItem> items = new ArrayList<>();
		items.add(new SimpleOrderedItem('c', 2, now));
		items.add(new SimpleOrderedItem('d', 3, now.plusDays(1)));
		items.add(new SimpleOrderedItem('b', 1, now.plusDays(2)));
		items.add(new SimpleOrderedItem('a', 0, now.plusMonths(1)));
		
		List<Comparator<IOrderedItem>> comparers = new ArrayList<Comparator<IOrderedItem>>();
		comparers.add(new OrderComparer());
		
		List<? extends IOrderedItem> sortedList = new ItemSorter(comparers).sort(items);
		assertOrder(sortedList, "abcd");
	}
	
	@Test
	void itemsSortedByDateThenOrder() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		SimpleOrderedItem[] items = new SimpleOrderedItem[] {
			new SimpleOrderedItem('d', 0, now.plusMonths(1)),
			new SimpleOrderedItem('b', 1, now),
			new SimpleOrderedItem('c', 0, now.plusDays(1)),
			new SimpleOrderedItem('a', 0, now),
		};
		
		List<Comparator<IOrderedItem>> comparers = new ArrayList<Comparator<IOrderedItem>>();
		comparers.add(new DateComparer());
		comparers.add(new OrderComparer());

		List<? extends IOrderedItem> sortedList = new ItemSorter(comparers).sort(Arrays.asList(items));

		assertOrder(sortedList, "abcd");
	}
	
	private void assertOrder(List<? extends IOrderedItem> sortedList, String expectedIdOrder) {
		Assert.assertTrue(sortedList.size() == expectedIdOrder.length());
		for (short i = 0; i < expectedIdOrder.length(); ++i) {
			SimpleOrderedItem item = (SimpleOrderedItem)sortedList.get(i);
			Assert.assertTrue(item.getId() == expectedIdOrder.charAt(i));
		}
	}

}
