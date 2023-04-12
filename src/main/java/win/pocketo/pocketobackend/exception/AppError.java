package win.pocketo.pocketobackend.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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

	private String path; // Only set when response

	@JsonIgnore
	private AppException exception; // Cache linked exception

	public AppError(ErrorCode code) {
		this(code, code.getDescription(), code.getIsUnwanted(), code.getStatus());
		actions.addAll(code.getActions());
	}

	public AppError(ErrorCode code, String description) {
		this(code, description, code.getIsUnwanted(), code.getStatus());
		actions.addAll(code.getActions());
	}

	public ResponseEntity<AppError> toResponseEntity(HttpServletRequest request) {
		path = request.getRequestURI();
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(this);
	}

	public AppException toException() {
		if (exception == null) {
			exception = new AppException(this);
		}
		return exception;
	}

}
