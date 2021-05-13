package net.astrocube.api.core.utils;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

import javax.annotation.Nullable;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jdk.nashorn.internal.runtime.linker.Bootstrap.isCallable;

public class Methods {
	private Methods() {
	}

	public static String describeParameters(Stream<Class<?>> parameterTypes) {
		return "(" + parameterTypes.map(Class::getSimpleName).collect(Collectors.joining(", ")) + ")";
	}

	public static String describeParameters(Class<?>... parameterTypes) {
		return describeParameters(Stream.of(parameterTypes));
	}

	public static String describeParameters(MethodType methodType) {
		return describeParameters(methodType.parameterList().stream());
	}

	public static String describe(@Nullable Class<?> decl, @Nullable Class<?> returnType, @Nullable String name, @Nullable Stream<Class<?>> parameterTypes) {
		String text = "";
		if (returnType != null) {
			text += returnType.getSimpleName() + " ";
		}
		if (name != null) {
			if (decl != null) {
				text += decl.getSimpleName() + "#" + name;
			} else {
				text += name;
			}
		}
		if (parameterTypes != null) {
			text += describeParameters(parameterTypes);
		}
		return text;
	}

	public static String describe(Class<?> decl, Class<?> returnType, String name, Class<?>... parameterTypes) {
		return describe(decl, returnType, name, Stream.of(parameterTypes));
	}

	public static String describe(Class<?> decl, String name, Class<?>... parameterTypes) {
		return describe(decl, null, name, Stream.of(parameterTypes));
	}

	public static String describe(Class<?> decl, String name) {
		return describe(decl, null, name, (Stream) null);
	}

	public static String describe(Method method) {
		return describe(method.getDeclaringClass(), method.getReturnType(), method.getName(), method.getParameterTypes());
	}

	public static String describe(Class<?> decl, MethodType methodType, String name) {
		return describe(decl, methodType.returnType(), name, methodType.parameterArray());
	}

	public static String describe(MethodType methodType, String name) {
		return describe(null, methodType, name);
	}

	public static boolean respondsTo(Object obj, Method call) {
		return call.getDeclaringClass().isInstance(obj) || respondsTo(obj.getClass(), call);
	}

	public static Method declaredMethod(Class<?> decl, String name, Class<?>... params) {
		try {
			return decl.getDeclaredMethod(name, params);
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodError(describe(decl, name, params));
		}
	}

	public static Method method(Class<?> decl, String name, Class<?>... params) {
		try {
			return decl.getMethod(name, params);
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodError(describe(decl, name, params));
		}
	}

	public static boolean respondsTo(Class<?> cls, Method call) {
		return callableMethod(cls, call) != null;
	}

	public static @Nullable
	Method accessibleMethod(Class<?> cls, Method call) {
		Method method = findClassMethod(cls, call);
		if (method != null) return method;
		return findInterfaceMethod(cls, call, false);
	}

	public static String removeBeanPrefix(String name, String prefix) {
		if (name.startsWith(prefix) && name.length() > prefix.length()) {
			final char first = name.charAt(prefix.length());
			if (Character.isUpperCase(first)) {
				return Character.toLowerCase(first) + name.substring(prefix.length() + 1);
			}
		}
		return name;
	}

	public static String removeBeanPrefix(String name) {
		name = removeBeanPrefix(name, "get");
		name = removeBeanPrefix(name, "set");
		name = removeBeanPrefix(name, "is");
		return name;
	}

	public static @Nullable
	Method callableMethod(Class<?> cls, Method call) {
		Method method = findClassMethod(cls, call);
		if (method != null) {
			// If any superclass declares the method, defaults will not
			// be called, even if the override is abstract.
			return isCallable(method) ? method : null;
		}

		method = findInterfaceMethod(cls, call, true);
		return method;
	}

	private static @Nullable
	Method findClassMethod(@Nullable Class<?> cls, Method call) {
		if (cls == null) return null;
		if (cls.isInterface()) return null;

		try {
			final Method method = cls.getDeclaredMethod(call.getName(), call.getParameterTypes());
			if (!Modifier.isPrivate(method.getModifiers())) return method; // may be abstract
		} catch (NoSuchMethodException ignored) {
		}

		return findClassMethod(cls.getSuperclass(), call);
	}

	private static @Nullable
	Method findInterfaceMethod(@Nullable Class<?> cls, Method call, boolean callable) {
		if (cls == null) return null;
		if (callable && call.getDeclaringClass().isAssignableFrom(Object.class))
			return null; // Object method defaults are not allowed

		if (cls.isInterface()) {
			try {
				final Method method = cls.getDeclaredMethod(call.getName(), call.getParameterTypes());
				if (!callable || isCallable(method)) return method;
			} catch (NoSuchMethodException ignored) {
			}
		}

		for (Class<?> iface : cls.getInterfaces()) {
			final Method method = findInterfaceMethod(iface, call, callable);
			if (method != null) return method;
		}

		return null;
	}

	public static Stream<Method> declaredMethodsInAncestors(Class<?> klass) {
		return Types.ancestors(klass)
			.stream()
			.flatMap(ancestor -> Stream.of(ancestor.getDeclaredMethods()))
			.distinct();
	}

	public static <T> Stream<Invokable<T, Object>> declaredMethodsInAncestors(TypeToken<T> klass) {
		return declaredMethodsInAncestors(klass.getRawType()).map(klass::method);
	}


}