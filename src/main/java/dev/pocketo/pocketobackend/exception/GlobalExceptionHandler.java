package dev.pocketo.pocketobackend.exception;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.NestedCheckedException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import dev.pocketo.pocketobackend.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler({ AppException.class })
	public ResponseEntity<AppError> handleAppException(AppException ex) {
		return ex.getError().toResponseEntity();
	}

	@ExceptionHandler({ BindException.class, ConstraintViolationException.class })
	public ResponseEntity<AppError> handleValidationException(Exception ex) {
		String defaultDescription = ErrorCode.REQUEST_VALIDATION_FAILED.getDescription();
		String description = defaultDescription;
		if (ex instanceof BindException castedEx) {
			description = CodeUtil.getFirstExceptionMessage(castedEx, defaultDescription);
		}
		else if (ex instanceof ConstraintViolationException castedEx) {
			description = CodeUtil.getFirstExceptionMessage(castedEx, defaultDescription);
		}
		return new AppError(ErrorCode.REQUEST_VALIDATION_FAILED, description).toResponseEntity();
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public ResponseEntity<AppError> handleUploadFileTooLargeException() {
		return new AppError(ErrorCode.UPLOAD_FILE_TOO_LARGE).toResponseEntity();
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<AppError> handleAccessDeniedException() {
		return new AppError(ErrorCode.ACCESS_DENIED).toResponseEntity();
	}

	@ExceptionHandler({ NestedCheckedException.class, NestedRuntimeException.class, ServletException.class })
	public ResponseEntity<AppError> handleOtherSpringException(Exception ex) {
		log.warn("Spring error!", ex);
		return new AppError(ErrorCode.SPRING_ERROR).toResponseEntity();
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<AppError> handleOtherException(Exception ex) {
		log.error("Server error!", ex);
		return new AppError(ErrorCode.SERVER_ERROR).toResponseEntity();
	}

}
