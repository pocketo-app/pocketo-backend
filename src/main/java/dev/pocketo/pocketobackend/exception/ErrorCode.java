package dev.pocketo.pocketobackend.exception;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	SERVER_ERROR("Server error", true, 500, List.of(ActionOnError.SHOW, ActionOnError.REFRESH));

	private final String description;

	private final Boolean isUnwanted;

	private final int status;

	private final List<ActionOnError> actions;

}
