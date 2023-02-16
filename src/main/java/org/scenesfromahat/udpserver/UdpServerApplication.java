package org.scenesfromahat.udpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SomeProperties.class)
public class UdpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdpServerApplication.class, args);
	}
}
