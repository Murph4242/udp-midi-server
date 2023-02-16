package org.scenesfromahat.udpserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/udpserver")
public class Controller {

	private final MidiStuff midiStuff;

	@GetMapping(value = "/reload-chases")
	public String reload() {
		midiStuff.loadChaseStore();
		return "Reloaded at " + LocalTime.now();
	}
}
