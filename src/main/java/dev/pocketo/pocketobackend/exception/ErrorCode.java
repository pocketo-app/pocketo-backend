package dev.pocketo.pocketobackend.exception;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// @formatter:off
	SERVER_ERROR("Server error", true, 500, List.of(ActionOnError.SHOW, ActionOnError.REFRESH)),
	SPRING_ERROR("Spring error", true, 500, List.of(ActionOnError.SHOW, ActionOnError.REFRESH)),
	URL_NOT_FOUND("URL not found", true, 404, List.of(ActionOnError.SHOW, ActionOnError.REFRESH)),
	ACCESS_DENIED("Access denied", false, 403, List.of(ActionOnError.SHOW, ActionOnError.GO_HOME)),
	BAD_REQUEST("Bad request", true, 400, List.of(ActionOnError.SHOW, ActionOnError.REFRESH)),
	REQUEST_VALIDATION_FAILED("Request validation failed", true, 400, List.of(ActionOnError.SHOW, ActionOnError.REFRESH)),
	UPLOAD_FILE_TOO_LARGE("Upload file too large", false, 400, List.of(ActionOnError.SHOW));
	// @formatter:on

	private final String description;

	private final Boolean isUnwanted;

	private final int status;

	private final List<ActionOnError> actions;

}
