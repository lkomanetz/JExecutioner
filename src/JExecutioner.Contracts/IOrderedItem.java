package com.jexecutioner.contracts;

import java.time.*;

public interface IOrderedItem {

	int getOrder();
	ZonedDateTime getDateCreatedUtc();

}
