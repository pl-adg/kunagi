package scrum.server.project;

import ilarkesto.base.time.Date;
import ilarkesto.core.logging.Log;

import java.util.Collection;

import scrum.server.admin.User;
import scrum.server.admin.UserDao;

public class ProjectDao extends GProjectDao {

	private static final Log LOG = Log.get(ProjectDao.class);

	// --- dependencies ---

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// --- ---

	public Project postProject(User admin) {
		Project project = newEntityInstance();
		project.setLabel("New Project");
		project.addAdmin(admin);
		saveEntity(project);
		return project;
	}

	public void scanFiles() {
		LOG.info("Scanning file repositories of all projects");
		for (Project project : getEntities()) {
			project.scanFiles();
		}
	}

	// --- test data ---

	public void createTestProject() {
		User po;
		User sm;

		po = userDao.getTestUser("duke");
		sm = userDao.getTestUser("spinne");

		Collection<User> team = userDao.getEntities();
		team.remove(po);
		team.remove(sm);

		Project project = postProject(userDao.getUserByName("admin"));
		project.setLabel("Demo");
		project.setBegin(Date.today().addMonths(-2));
		project.setEnd(Date.today().addMonths(5));
		project.addParticipants(userDao.getEntities());
		project.addAdmins(userDao.getEntities());
		project.addTeamMembers(team);
		project.addProductOwner(po);
		project.addScrumMaster(sm);
		project.addTestImpediments();
		project.addTestSprints();
		project.addTestRequirements();
		project.addTestRisks();
		project.addTestQualitys();
		project.addTestIssues();
		project.addTestEvents();
		project.addTestSimpleEvents();

		po.setCurrentProject(project);
	}

}
