package win.pocketo.pocketobackend.util;

import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;

public class CodeUtil {

	public static String getFirstExceptionMessage(BindException ex, String defaultMessage) {
		if (!ex.hasErrors() || ex.getAllErrors().isEmpty()) {
			return defaultMessage;
		}
		return ex.getAllErrors().get(0).getDefaultMessage();
	}

	public static String getFirstExceptionMessage(ConstraintViolationException ex, String defaultMessage) {
		var firstViolation = ex.getConstraintViolations().stream().findFirst();
		if (firstViolation.isEmpty()) {
			return defaultMessage;
		}
		return firstViolation.get().getMessage();
	}

}