package win.pocketo.pocketobackend;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PocketoBackendApplication {

	public static void main(String[] args) {
		if (!isAppRunViaDockerCompose()) {
			log.warn("The app is running without Docker Compose");
		}
		SpringApplication.run(PocketoBackendApplication.class, args);
	}

	private static boolean isAppRunViaDockerCompose() {
		String env = System.getenv("RUN_VIA_DOCKER_COMPOSE");
		return Objects.equals(env, "1");
	}

}
