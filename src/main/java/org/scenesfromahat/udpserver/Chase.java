package org.scenesfromahat.udpserver;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class Chase {

	private Integer id;

	private String name;

	private List<Integer> notes;

	private Integer duration;


	private Integer delay;


	public boolean isSingleNote() {
		return CollectionUtils.size(notes) == 1;
	}

	public boolean isOneShot() {
		return CollectionUtils.size(notes) > 1 && duration == null;
	}

	public boolean isTimed() {
		return CollectionUtils.size(notes) > 1 && duration != null && duration > 0;
	}

	public boolean isContinuous() {
		return CollectionUtils.size(notes) > 1 && duration != null && duration == 0;
	}
}
