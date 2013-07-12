package de.netprojectev.client.gui.tablemodels;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.misc.LoggerBuilder;

public class AllMediaTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1263313149575878071L;

	public interface UpdateAllMediaDataListener {
		public void update();
	}

	private static final Logger log = LoggerBuilder.createLogger(AllMediaTableModel.class);

	private final MediaModelClient mediaModel;
	private final String[] columns = { "Name", "Priority", "Type" };

	public AllMediaTableModel(MediaModelClient mediaModel) {
		this.mediaModel = mediaModel;
		this.mediaModel.setListener(new UpdateAllMediaDataListener() {

			@Override
			public void update() {
				updateTableData();

			}

		});
	}

	/**
	 * Updating the table View to the current Data Changes on the Model. Uses
	 * invokeLater for clean and thread-save event handling.
	 */
	private void updateTableData() {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				fireTableDataChanged();
			}
		});

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

}
