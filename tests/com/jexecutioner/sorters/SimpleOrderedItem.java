package com.jexecutioner.sorters;

import java.time.ZonedDateTime;

import com.jexecutioner.contracts.IOrderedItem;

public class SimpleOrderedItem implements IOrderedItem {
	private char _id;
	private int _order;
	private ZonedDateTime _dateCreatedUtc;
	
	public SimpleOrderedItem(char id, int order, ZonedDateTime dateCreatedUtc) {
		_id = id;
		_order = order;
		_dateCreatedUtc = dateCreatedUtc;
	}
	
	public char getId() { return _id; }

	@Override
	public int getOrder() { return _order; }

	@Override
	public ZonedDateTime getDateCreatedUtc() { return _dateCreatedUtc; }

}