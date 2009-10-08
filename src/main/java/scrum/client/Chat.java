package scrum.client;

import ilarkesto.gwt.client.AComponent;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import scrum.client.admin.User;
import scrum.client.collaboration.ChatMessage;
import scrum.client.collaboration.ChatWidget;
import scrum.client.project.Project;

public class Chat extends AComponent {

	// --- dependencies ---

	private Dao dao;
	private Auth auth;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	// --- ---

	private LinkedList<ChatMessage> chatMessages = new LinkedList<ChatMessage>();

	public LinkedList<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public ChatMessage postSystemMessage(String text, boolean distribute) {
		return postMessage(null, text, distribute);
	}

	public ChatMessage postMessage(String text) {
		return postMessage(auth.getUser(), text, true);
	}

	private ChatMessage postMessage(User author, String text, boolean distribute) {
		ChatMessage msg = new ChatMessage(ScrumGwtApplication.get().getProject(), author, text);
		addChatMessage(msg);
		if (distribute) dao.createChatMessage(msg);
		return msg;
	}

	void addChatMessage(ChatMessage msg) {
		Project project = ScrumGwtApplication.get().getProject();
		if (project == null || !msg.isProject(project)) return;
		if (chatMessages.contains(msg)) return;
		chatMessages.add(msg);
		cleanupChatMessages();
		ChatWidget.get().update();
	}

	private void cleanupChatMessages() {
		for (Iterator<ChatMessage> it = chatMessages.iterator(); it.hasNext();) {
			ChatMessage message = it.next();
			if (message.isOld()) it.remove();
		}
		while (chatMessages.size() > 50)
			chatMessages.remove(0);
		Collections.sort(chatMessages);
	}

}