package ru.otus.hw11webflux.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(final String message) {
		super(message);
	}
}
