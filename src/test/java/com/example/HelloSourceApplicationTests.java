package com.example;

import com.example.HelloSourceApplication.Tweet;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Import(TestChannelBinderConfiguration.class)
class HelloSourceApplicationTests {
	@Autowired
	HelloSourceApplication app;

	@Autowired
	OutputDestination destination;

	@Autowired
	CompositeMessageConverter messageConverter;

	@Test
	void contextLoads() {
		final Tweet tweet = new Tweet("hello!");
		this.app.tweet(tweet);
		final Message<byte[]> message = this.destination.receive(3, "hello");
		final Tweet output = (Tweet) this.messageConverter.fromMessage(message, Tweet.class);
		assertThat(output).isNotNull();
		assertThat(output.text()).isEqualTo("hello!");
	}

}