import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


public class Ognl {

	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	public static boolean isEmpty(Object o) {
		if (o == null)
			return true;

		if (o instanceof String) {
			if (((String) o).length() == 0) {
				return true;
			}
		} else if (o instanceof Collection) {
			if (((Collection) o).isEmpty()) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if (Array.getLength(o) == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).isEmpty()) {
				return true;
			}
		} else {
			return false;
		}

		return false;

	}

	public static void main(String[] args) {
		System.out.println(isEmpty(null));
	}
}
