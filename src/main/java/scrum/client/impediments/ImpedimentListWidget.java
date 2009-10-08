package scrum.client.impediments;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.ToolbarWidget;
import scrum.client.Components;
import scrum.client.ScrumGwtApplication;
import scrum.client.common.BlockListWidget;
import scrum.client.common.GroupWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ImpedimentListWidget extends AWidget {

	public BlockListWidget<Impediment> list;
	private ToolbarWidget toolbar;

	@Override
	protected Widget onInitialization() {
		ScrumGwtApplication.get().callRequestImpediments();

		list = new BlockListWidget<Impediment>(ImpedimentBlock.FACTORY);
		list.setAutoSorter(Impediment.REVERSE_DATE_COMPARATOR);

		toolbar = new ToolbarWidget();
		toolbar.addButton(new CreateImpedimentAction());

		FlowPanel panel = new FlowPanel();
		panel.add(toolbar);
		panel.add(new HTML("<br>"));
		panel.add(list);

		return new GroupWidget("Impediment List", panel);
	}

	@Override
	protected void onUpdate() {
		toolbar.update();
		list.setObjects(ScrumGwtApplication.get().getProject().getImpediments());
	}

	public void showImpediment(Impediment impediment) {
		list.extendObject(impediment);
	}

	public static ImpedimentListWidget get() {
		return Components.get().getProjectContext().getImpedimentList();
	}

	public void select(Impediment impediment) {
		list.extendObject(impediment);
	}
}
