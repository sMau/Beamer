package de.netprojectev.client.gui.main;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.misc.LoggerBuilder;

public class AllMediaTableModel implements TableModel {

	private static final Logger log = LoggerBuilder.createLogger(AllMediaTableModel.class);

	private final MediaModelClient mediaModel;
	private final String[] columns = { "Name", "Priority", "Type" };

	public AllMediaTableModel(MediaModelClient mediaModel) {
		this.mediaModel = mediaModel;
	}

	@Override
	public int getRowCount() {
		return mediaModel.getAllMedia().size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientMediaFile media = mediaModel.getValueAt(rowIndex);
		if (media == null) {
			return null;
		} else {
			switch (columnIndex) {
			case 0:
				return media.getName();
			case 1:
				return media.getPriority().toString();
			case 2:
				return media.getType();
			default:
				return "error loading data";
			}

		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
