package org.scenesfromahat.udpserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Data
@ConfigurationProperties(prefix = "app")
public class SomeProperties {
	private Resource file;

}