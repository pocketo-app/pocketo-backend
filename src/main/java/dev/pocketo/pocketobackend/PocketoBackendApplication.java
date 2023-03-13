package dev.pocketo.pocketobackend;

import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PocketoBackendApplication {

	public static void main(String[] args) {
		if (!isAppRunViaDockerCompose()) {
			log.error("Please run the app via Docker Compose");
			System.exit(1);
		}
		SpringApplication.run(PocketoBackendApplication.class, args);
	}

	private static boolean isAppRunViaDockerCompose() {
		String env = System.getenv("RUN_VIA_DOCKER_COMPOSE");
		return Objects.equals(env, "1");
	}

}
