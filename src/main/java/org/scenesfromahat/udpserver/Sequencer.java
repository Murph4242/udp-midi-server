package org.scenesfromahat.udpserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sequencer implements Runnable {

	private static final int BLACKOUT_NOTE = 126;
	private final MidiStuff midiStuff;
	private Chase chase;
	private boolean stop = false;
	private boolean blackout = false;

	public Sequencer(MidiStuff midiStuff) {
		this.midiStuff = midiStuff;
	}

	@SneakyThrows
	@Override
	public void run() {
		if (chase == null) {
			this.stop = true;
			return;
		}
		if (blackout) {
			midiStuff.triggerScene(BLACKOUT_NOTE); // end blackout
			blackout = false;
		}
		if (chase.isSingleNote()) {
			log.debug("Triggering single scene Note[{}] [{}]", chase.getNotes().get(0), chase.getName());
			midiStuff.triggerScene(chase.getNotes().get(0));
			if (chase.getNotes().get(0) == BLACKOUT_NOTE) {
				blackout = true;
			}
		} else if (chase.isOneShot()) {
			log.debug("Triggering single chase Notes{} [{}]", chase.getNotes(), chase.getName());
			playChase(chase);
		} else if (chase.isTimed()) {
			log.debug("Triggering timed chase Notes{} {}]", chase.getNotes(), chase.getName());
			long stopMilliseconds = System.currentTimeMillis() + chase.getDuration() * 1000;
			while (stopMilliseconds >= System.currentTimeMillis()) {
				playChase(chase);
			}
		} else if (chase.isContinuous()) {
			log.debug("Triggering continuous chase Notes{} [{}]", chase.getNotes(), chase.getName());
			while (!stop) {
				playChase(chase);
			}
		}
	}

	public void stop() {
		this.stop = true;
	}

	public void changeChase(Chase chase) {
		this.chase = chase;
	}

	@SneakyThrows
	private void playChase(Chase chase) {
		for (Integer scene : chase.getNotes()) {
			midiStuff.triggerScene(scene);
			Thread.sleep(chase.getDelay());
		}
	}
}

