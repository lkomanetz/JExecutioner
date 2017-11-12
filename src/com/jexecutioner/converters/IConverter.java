package com.jexecutioner.converters;

public interface IConverter<TResult, TInput> {
	
	TResult convert(TInput itemToConvert);
	
}
