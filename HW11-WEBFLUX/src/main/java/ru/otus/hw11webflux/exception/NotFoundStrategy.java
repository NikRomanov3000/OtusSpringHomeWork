package ru.otus.hw11webflux.exception;

public class NotFoundStrategy extends RuntimeException{
	public NotFoundStrategy(final String message) {
		super(message);
	}
}
