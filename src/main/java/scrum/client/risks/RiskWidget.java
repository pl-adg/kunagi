package scrum.client.risks;

import ilarkesto.gwt.client.ADropdownViewEditWidget;
import ilarkesto.gwt.client.ARichtextViewEditWidget;
import ilarkesto.gwt.client.ATextViewEditWidget;
import ilarkesto.gwt.client.ATextWidget;
import ilarkesto.gwt.client.ToolbarWidget;
import scrum.client.ScrumGwtApplication;
import scrum.client.common.AExtensibleBlockWidget;
import scrum.client.common.FieldsWidget;
import scrum.client.img.Img;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class RiskWidget extends AExtensibleBlockWidget {

	private Risk risk;

	private FieldsWidget fields;
	private Label summary;
	private ToolbarWidget toolbar;

	public RiskWidget(Risk risk) {
		this.risk = risk;
	}

	@Override
	protected void onCollapsedInitialization() {
		summary = new Label();
	}

	@Override
	protected void onCollapsedUpdate() {
		setBlockTitle(risk.getLabel());
		setIcon(Img.bundle.risk32());
		summary.setText(risk.getSummary());
		setContent(summary);
		setToolbar(null);
	}

	@Override
	protected void onExtendedInitialization() {
		fields = new FieldsWidget();
		fields.setAutoUpdateWidget(this);

		fields.add("Label", new ATextViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(risk.getLabel());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorText(risk.getLabel());
			}

			@Override
			protected void onEditorSubmit() {
				risk.setLabel(getEditorText());
			}

		});
		fields.add("Description", new ARichtextViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(risk.getDescription());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorText(risk.getDescription());
			}

			@Override
			protected void onEditorSubmit() {
				risk.setDescription(getEditorText());
			}
		});
		fields.add("Impact", new ADropdownViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(risk.getImpactLabel());
			}

			@Override
			protected void onEditorUpdate() {
				setOptions("20", "40", "60", "80", "100");
				setSelectedOption(String.valueOf(risk.getImpact()));
			}

			@Override
			protected void onEditorSubmit() {
				risk.setImpact(Integer.parseInt(getSelectedOption()));
			}
		});
		fields.add("Probability", new ADropdownViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(risk.getProbabilityLabel());
			}

			@Override
			protected void onEditorUpdate() {
				setOptions("20", "40", "60", "80", "100");
				setSelectedOption(String.valueOf(risk.getProbability()));
			}

			@Override
			protected void onEditorSubmit() {
				risk.setProbability(Integer.parseInt(getSelectedOption()));
			}
		});
		fields.add("Priority", new ATextWidget() {

			@Override
			protected void onUpdate() {
				setText(risk.getPriorityLabel());
			}
		});

	}

	@Override
	protected void onExtendedUpdate() {
		setBlockTitle(risk.getLabel());
		setIcon(Img.bundle.risk32());
		fields.update();
		setContent(fields);
		setToolbar(getToolbar());
	}

	@Override
	public AbstractImagePrototype getIcon16() {
		return Img.bundle.risk16();
	}

	protected Widget getToolbar() {
		if (toolbar == null) {

			toolbar = new ToolbarWidget();

			toolbar.addButton(Img.bundle.delete16().createImage(), "Delete").addClickListener(new ClickListener() {

				public void onClick(Widget sender) {
					ScrumGwtApplication.get().getProject().deleteRisk(risk);
					getList().removeSelectedRow();
				}
			});

		}
		return toolbar;
	}

	@Override
	public void delete() {
		ScrumGwtApplication.get().getProject().deleteRisk(risk);
		getList().remove(this);
	}
}
