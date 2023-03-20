package dev.pocketo.pocketobackend.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.NestedCheckedException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import dev.pocketo.pocketobackend.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	// https://stackoverflow.com/a/48575275/13779659
	private final HttpServletRequest request;

	@ExceptionHandler({ AppException.class })
	public ResponseEntity<AppError> handleAppException(AppException ex) {
		return ex.getError().toResponseEntity(request);
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
		return new AppError(ErrorCode.REQUEST_VALIDATION_FAILED, description).toResponseEntity(request);
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public ResponseEntity<AppError> handleUploadFileTooLargeException() {
		return new AppError(ErrorCode.UPLOAD_FILE_TOO_LARGE).toResponseEntity(request);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<AppError> handleAccessDeniedException() {
		return new AppError(ErrorCode.ACCESS_DENIED).toResponseEntity(request);
	}

	@ExceptionHandler({ NoHandlerFoundException.class })
	public ResponseEntity<AppError> handleNoHandlerFoundException() {
		return new AppError(ErrorCode.URL_NOT_FOUND).toResponseEntity(request);
	}

	@ExceptionHandler({ NestedCheckedException.class, NestedRuntimeException.class, ServletException.class })
	public ResponseEntity<AppError> handleOtherSpringException(Exception ex) {
		log.warn("Spring error!", ex);
		return new AppError(ErrorCode.SPRING_ERROR).toResponseEntity(request);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<AppError> handleOtherException(Exception ex) {
		log.error("Server error!", ex);
		return new AppError(ErrorCode.SERVER_ERROR).toResponseEntity(request);
	}

}
