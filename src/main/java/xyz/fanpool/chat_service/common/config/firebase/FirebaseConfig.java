package xyz.fanpool.chat_service.common.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Slf4j
@Component
@Configuration
public class FirebaseConfig {

	private final String CREDENTIAL_PATH;
	private final ResourceLoader resourceLoader;

	@Autowired
	public FirebaseConfig(@Value("${firebase.credential-path}") String path, ResourceLoader resourceLoader) {
		this.CREDENTIAL_PATH = path;
		this.resourceLoader = resourceLoader;
	}

	@PostConstruct
	public void init() {
		try {
			FileInputStream serviceAccount = new FileInputStream(resourceLoader.getResource(CREDENTIAL_PATH).getFile());

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			log.error("Firebase Initialization Error", e);
		}
	}
}
