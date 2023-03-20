package dev.pocketo.pocketobackend.exception;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppError {

	private final long timestamp = System.currentTimeMillis();

	private final ErrorCode code;

	private final String description;

	private final Boolean isUnwanted;

	private final int status;

	private final Set<ActionOnError> actions = new HashSet<>();

	private final Map<String, Object> data = new HashMap<>();

}
