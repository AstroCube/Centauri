package net.astrocube.api.core.utils;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("all")
public final class Types {
	private Types() {
	}

	/**
	 * Convert a Guava {@link TypeToken} to a Guice {@link TypeLiteral}
	 */
	public static <T> TypeLiteral<T> toLiteral(TypeToken<T> typeToken) {
		return (TypeLiteral<T>) TypeLiteral.get(typeToken.getType());
	}

	public static <T, P> TypeLiteral<T> resolve(TypeLiteral<T> type, TypeParameter<P> parameter, Class<P> argument) {
		return toLiteral(toToken(type).where(parameter, argument));
	}

	public static <T, P> TypeLiteral<T> resolve(TypeLiteral<T> type, TypeParameter<P> parameter, TypeLiteral<P> argument) {
		return toLiteral(toToken(type).where(parameter, toToken(argument)));
	}

	public static <T> TypeLiteral<T> resolve(TypeLiteral<T> type, Class<?> declaringClass) {
		return (TypeLiteral<T>) toLiteral(TypeToken.of(declaringClass).resolveType(type.getType()));
	}

	public static <D extends GenericDeclaration> TypeVariable<D> typeVariable(D decl, String name) {
		for (TypeVariable<?> var : decl.getTypeParameters()) {
			if (name.equals(var.getName())) {
				return (TypeVariable<D>) var;
			}
		}
		throw new IllegalArgumentException(decl + " has no type parameter named '" + name + "'");
	}

	public static boolean isFullySpecified(Type type) {
		checkNotNull(type);
		if (type instanceof Class) {
			return true;
		} else if (type instanceof TypeVariable) {
			return false;
		} else if (type instanceof GenericArrayType) {
			return isFullySpecified(((GenericArrayType) type).getGenericComponentType());
		} else if (type instanceof WildcardType) {
			final WildcardType wildcard = (WildcardType) type;
			return Stream.of(wildcard.getLowerBounds()).allMatch(Types::isFullySpecified) &&
				Stream.of(wildcard.getUpperBounds()).allMatch(Types::isFullySpecified);
		} else if (type instanceof ParameterizedType) {
			final ParameterizedType parameterized = (ParameterizedType) type;
			return isFullySpecified(parameterized.getRawType()) &&
				(parameterized.getOwnerType() == null || isFullySpecified(parameterized.getOwnerType())) &&
				Stream.of(parameterized.getActualTypeArguments()).allMatch(Types::isFullySpecified);
		} else {
			throw new IllegalArgumentException("Unhandled metatype " + type.getClass());
		}
	}

	public static <T> TypeToken<T> toToken(TypeLiteral<T> typeLiteral) {
		return (TypeToken<T>) TypeToken.of(typeLiteral.getType());
	}

	public static boolean isFullySpecified(TypeToken<?> type) {
		return isFullySpecified(type.getType());
	}

	public static Type assertFullySpecified(Type type) {
		if (!isFullySpecified(type)) {
			throw new IllegalArgumentException("Type " + type + " is not fully specified");
		}
		return type;
	}

	public static <T> TypeLiteral<T> assertFullySpecified(TypeLiteral<T> type) {
		assertFullySpecified(type.getType());
		return type;
	}

	public static <T> TypeToken<T> assertFullySpecified(TypeToken<T> type) {
		assertFullySpecified(type.getType());
		return type;
	}

	private static final ImmutableBiMap<Class<?>, Class<?>> BOXINGS;

	static {
		BOXINGS = ImmutableBiMap.<Class<?>, Class<?>>builder()
			.put(boolean.class, Boolean.class)
			.put(char.class, Character.class)
			.put(byte.class, Byte.class)
			.put(short.class, Short.class)
			.put(int.class, Integer.class)
			.put(long.class, Long.class)
			.put(float.class, Float.class)
			.put(double.class, Double.class)
			.build();
	}

	public static <T> Class<T> box(Class<T> type) {
		return type.isPrimitive() ? (Class<T>) BOXINGS.get(type) : type;
	}

	public static <T> TypeToken<T> box(TypeToken<T> type) {
		return type.isPrimitive() ? TypeToken.of(box((Class<T>) type.getRawType())) : type;
	}

	/**
	 * Get the immediate parent types of the given class i.e. the
	 * direct superclass (if any) and any directly implemented interfaces.
	 */
	public static <T> Iterable<Class<? super T>> parents(Class<T> type) {
		Class<? super T> superclass = type.getSuperclass();
		Class<? super T>[] interfaces = (Class<? super T>[]) type.getInterfaces();
		if (superclass == null) {
			return Arrays.asList(interfaces);
		} else {
			return Iterables.concat(Arrays.asList(interfaces), Collections.singleton(superclass));
		}
	}

	/**
	 * Get all ancestor types of the given type, both classes and interfaces,
	 * in breadth-first order. The superclass of each class is traversed before
	 * its interfaces.
	 * @param allowDuplicates If true, duplicate interfaces will appear in the result wherever
	 *                        they occur in the ancestry graph. If false, all but the
	 *                        first occurance of each interface will be omitted from the result.
	 *                        This makes the operation somewhat more expensive, so duplicates
	 *                        should be allowed if possible.
	 */
	public static <T> Collection<Class<? super T>> ancestors(Class<T> type, boolean allowDuplicates) {
		List<Class<? super T>> list = new ArrayList<>();
		list.add(type);
		for (int i = 0; i < list.size(); i++) {
			final Class<?> t = list.get(i);
			if (t.getSuperclass() != null) list.add((Class<? super T>) t.getSuperclass());
			final Class<? super T>[] interfaces = (Class<? super T>[]) t.getInterfaces();
			if (allowDuplicates) {
				Collections.addAll(list, interfaces);
			} else {
				for (Class<? super T> iface : interfaces) {
					if (!list.contains(iface)) list.add(iface);
				}
			}
		}
		return list;
	}

	/**
	 * Equivalent to {@link #ancestors(Class, boolean)} with duplicate results allowed.
	 */
	public static <T> Collection<Class<? super T>> ancestors(Class<T> type) {
		return ancestors(type, true);
	}
}