package scrum.server.project;

import ilarkesto.fp.Predicate;

public class RequirementDao extends GRequirementDao {

	@Override
	public Requirement newEntityInstance() {
		Requirement requirement = super.newEntityInstance();
		requirement.setLabel("New Story");
		return requirement;
	}

	public Requirement getRequirementByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Requirement>() {

			public boolean test(Requirement r) {
				return r.isNumber(number) && r.isProject(project);
			}
		});
	}

	// --- test data ---

	public Requirement postRequirement(Project project, String label, Float estimation) {
		Requirement requirement = newEntityInstance();
		requirement.setProject(project);
		requirement.setLabel(label);
		requirement.setEstimatedWork(estimation);
		saveEntity(requirement);
		return requirement;
	}
}
