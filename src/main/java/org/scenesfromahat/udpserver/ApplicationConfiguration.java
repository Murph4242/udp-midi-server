package org.scenesfromahat.udpserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public StandardIntegrationFlow processUniCastUdpMessage() {
		return IntegrationFlows
				.from(new UnicastReceivingChannelAdapter(11111))
				.handle("UDPServer", "handleMessage")
				.get();
	}
}
