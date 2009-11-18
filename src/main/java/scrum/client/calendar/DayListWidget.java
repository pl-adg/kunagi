package scrum.client.calendar;

import ilarkesto.gwt.client.Date;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DayListWidget extends AScrumWidget {

	private Date begin;
	private Date end;
	private FlexTable table;

	public DayListWidget(Date begin, Date end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	@Override
	protected Widget onInitialization() {
		table = new FlexTable();
		table.setCellPadding(2);
		int row = 0;
		Date date = begin;
		int week = 0;
		while (date.compareTo(end) <= 0) {
			int w = date.getWeek();
			if (w != week) {
				week = w;
				table.setWidget(row, 0, createWeek(week));
				// table.getCellFormatter().getElement(row, 0).setAttribute("rowspan", "7");
			} else {
				table.setWidget(row, 0, new Label("."));
			}
			table.setWidget(row, 1, createDate(date));
			table.setWidget(row, 2, createEventList(date));
			date = date.nextDay();
			row++;
		}
		return table;
	}

	private Widget createWeek(int week) {
		Label l = new Label("" + week);
		l.setStyleName("DayListWidget-week");
		return l;
	}

	private Widget createDate(Date date) {
		Label l = new Label(date.getWeekdayLabel() + ", " + date.getDay() + ".");
		l.setStyleName("DayListWidget-date");
		return l;
	}

	private Widget createEventList(Date date) {
		return new Label("09:00 Daily Scrum");
	}

}
