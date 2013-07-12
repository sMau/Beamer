package de.netprojectev.client.gui.tablemodels;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.exceptions.MediaDoesNotExsistException;

public class CustomQueueTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2863260660288778361L;

	public interface UpdateCustomQueueDataListener {
		public void update();
	}
	
	private final MediaModelClient mediaModel;
	private final String[] columns = { "Name", "Priority", "Type" };
	
	public CustomQueueTableModel(MediaModelClient mediaModel) {
		this.mediaModel = mediaModel;
		this.mediaModel.setCustomQueueListener(new UpdateCustomQueueDataListener() {
			
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
		return mediaModel.getCustomQueue().size();
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
		ClientMediaFile media = null;
		try {
			media = mediaModel.getMediaFileById(mediaModel.getCustomQueue().get(rowIndex));
		} catch (MediaDoesNotExsistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
