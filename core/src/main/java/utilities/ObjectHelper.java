package utilities;

import java.lang.reflect.Method;

public class ObjectHelper {

	public static Class<?> getClass(String path, String name) {
		try {
			return Class.forName(String.format("%s.%s", path, name));
		} catch (Exception e) {
			return null;
		}
	}

	public static Object getInstance(Class<?> cls) {
		try {
			return cls.getConstructor().newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public static Method getMethod(Class<?> cls, String method) {
		try {
			return cls.getDeclaredMethod(method);
		} catch (Exception e) {
			return null;
		}
	}

	public static Object invokeMethod(Object object, Method method) {
		try {
			return method.invoke(object);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getName(Object object) {
		return object.getClass().getSimpleName();
	}


}
