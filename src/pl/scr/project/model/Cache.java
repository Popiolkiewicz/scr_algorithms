package pl.scr.project.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Cache {

	private static Cache cache;

	private Cache() {
	}

	public static final Cache get() {
		return cache != null ? cache : (cache = new Cache());
	}

	private BooleanProperty interruptAfterDeadline = new SimpleBooleanProperty(false);

	public final BooleanProperty interruptAfterDeadline() {
		return interruptAfterDeadline;
	}

	public boolean getInterruptAfterDeadline() {
		return interruptAfterDeadline.get();
	}

}
