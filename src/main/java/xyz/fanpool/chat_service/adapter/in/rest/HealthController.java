package xyz.fanpool.chat_service.adapter.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/chat/health")
	public ResponseEntity<Void> health() {
		return ResponseEntity.ok().build();
	}
}
