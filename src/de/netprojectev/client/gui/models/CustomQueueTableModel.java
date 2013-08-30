package de.netprojectev.client.gui.models;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.gui.main.MainClientGUIWindow;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
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
	private final String[] columns = { " ", " ", "Name", "Priority", "Type" };
	
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
		SwingUtilities.invokeLater(new Runnable() {

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
		if(columnIndex == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientMediaFile media = null;
		try {
			media = mediaModel.getMediaFileById(mediaModel.getCustomQueue().get(rowIndex));
		} catch (MediaDoesNotExsistException e) {
			ClientMessageProxy.errorRequestFullSync(mediaModel.getProxy(), e);
			MainClientGUIWindow.errorRequestingFullsyncDialog(new JFrame());
		}
		if (media == null) {
			return null;
		} else {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				try {
					return mediaModel.timeUntilShow(rowIndex);
				} catch (MediaDoesNotExsistException e) {
					ClientMessageProxy.errorRequestFullSync(mediaModel.getProxy(), e);
					MainClientGUIWindow.errorRequestingFullsyncDialog(new JFrame());
					return 0;
				}
			case 2:
				return media.getName();
			case 3:
				return media.getPriority().toString();
			case 4:
				return media.getType();
			default:
				return "error loading data";
			}

		}
	}

}
