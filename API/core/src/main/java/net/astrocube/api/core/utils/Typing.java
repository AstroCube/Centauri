package net.astrocube.api.core.utils;

import com.google.inject.TypeLiteral;

import java.lang.reflect.Type;

public class Typing {

	private Typing() {
	}

	/**
	 * Same as {@link TypeLiteral#get(Type)} but it returns
	 * a generic type literal. If the given {@code type} is
	 * a {@link Class} you should just use {@link TypeLiteral#get(Class)}
	 */
	public static <T> TypeLiteral<T> getUnchecked(Type type) {
		@SuppressWarnings("unchecked")
		TypeLiteral<T> literal = (TypeLiteral<T>) TypeLiteral.get(type);
		return literal;
	}

}
