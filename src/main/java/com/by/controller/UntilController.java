package com.by.controller;

import java.util.Optional;
import java.util.function.Supplier;

import com.by.exception.NotFoundException;
import com.by.exception.Status;

public class UntilController<T> {
	protected Status fold(Optional<T> optional, Supplier<Status> fail, Supplier<Status> success) {
		if (optional.isPresent())
			return success.get();
		else
			return fail.get();
	}

	protected T lift(Supplier<Optional<T>> supplier) {
		return supplier.get().orElseThrow(() -> new NotFoundException());
	}

}
