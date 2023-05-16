package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloSourceApplication {

	private final StreamBridge streamBridge;

	public HelloSourceApplication(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	@PostMapping(path = "/")
	public void tweet(@RequestBody Tweet tweet) {
		this.streamBridge.send("output", tweet);
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloSourceApplication.class, args);
	}

	record Tweet(String text) {
	}
}