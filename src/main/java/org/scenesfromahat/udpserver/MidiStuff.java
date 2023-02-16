package org.scenesfromahat.udpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.HashMap;
import java.util.Map;

import static com.rememberjava.midi.MidiUtils.filteredDeviceStream;
import static com.rememberjava.midi.MidiUtils.onClassnameEquals;

@Slf4j
@Getter
@Component
public class MidiStuff {

	private final MidiDevice lightController;

	private final Map<Integer, Chase> chasesStore = new HashMap<>();

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SomeProperties someProperties;

	@SneakyThrows
	@Autowired
	public MidiStuff(SomeProperties someProperties) {
		this.someProperties = someProperties;
		this.lightController = getMidiOutDevice();
		log.debug("Found MIDI device: {}", this.lightController.getDeviceInfo().toString());
		loadChaseStore();
	}

	@SneakyThrows
	public void triggerScene(int note) {
		lightController.open();
		Receiver receiver = lightController.getReceiver();
		ShortMessage shortMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, note, 127);
		receiver.send(shortMessage, -1);
		receiver.close();
	}

	@SneakyThrows
	private MidiDevice getMidiOutDevice() {
		return filteredDeviceStream("USB MIDI Interface")
				.filter(onClassnameEquals("MidiOutDevice"))
				.findFirst().get();
	}

	@SneakyThrows
	public synchronized void loadChaseStore() {
		chasesStore.clear();
		Chases chases = objectMapper.readValue(someProperties.getFile().getFile(), Chases.class);
		MapUtils.populateMap(chasesStore, chases.getChases(), Chase::getId);
	}
}
