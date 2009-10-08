package scrum.client.tasks;

import scrum.client.Components;
import scrum.client.dnd.BlockListDropAction;
import scrum.client.project.Requirement;
import scrum.client.sprint.Task;

public class UnclaimTaskDropAction implements BlockListDropAction<Task> {

	private Requirement requirement;

	public UnclaimTaskDropAction(Requirement requirement) {
		this.requirement = requirement;
	}

	public boolean execute(Task task) {
		task.setRequirement(this.requirement);
		task.setUnOwned();
		Components.get().getUi().getWorkspace().update();
		return true;
	}
}
