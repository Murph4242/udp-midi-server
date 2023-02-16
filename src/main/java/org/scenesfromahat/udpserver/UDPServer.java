package org.scenesfromahat.udpserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UDPServer {

	private final MidiStuff midiStuff;

	private final TaskExecutor taskExecutor;

	private final Sequencer sequencer = new Sequencer(midiStuff);

	public void handleMessage(Message<Integer> message) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException, JsonProcessingException {
		Integer id = message.getPayload();
		Chase chase = midiStuff.getChasesStore().get(id);
		if (chase == null) {
			log.warn("No chase found for ID[{}]", id);
			return;
		}
		log.debug("CHASE ID: {} - {}", id, chase.getName());
		sequencer.stop();
		sequencer.changeChase(chase);
		taskExecutor.execute(sequencer);
	}
}
