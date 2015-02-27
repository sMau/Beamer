package de.netprojectev.client.gui.models;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.MediaModelClient.UpdateAllMediaDataListener;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class AllMediaTableModel extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1263313149575878071L;

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(AllMediaTableModel.class);

	private final MediaModelClient mediaModel;
	private final String[] columns = { " ", " ", " ", "Name", "Priority", "Type" };

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

		SwingUtilities.invokeLater(new Runnable() {

			@Override
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
		switch (columnIndex) {
		case 1:
			return Boolean.class;
		case 2:
			return Integer.class;
		default:
			return Object.class;
		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientMediaFile media = mediaModel.getValueAt(rowIndex);
		if (media == null) {
			return null;
		} else {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return media.isCurrent();
			case 2:
				return media.getShowCount();
			case 3:
				return media.getName();
			case 4:
				try {
					if (media.getType().equals(MediaType.Countdown)) {
						return "Countdown"; // TODO make it possible to show countdown duration
					} else {
						return mediaModel.getProxy().getPrefs().getPriorityByID(media.getPriorityID()).toString();
					}

				} catch (PriorityDoesNotExistException e) {
					log.log(Level.WARNING, "Priority could not be found", e);
				}
			case 5:
				return media.getType();
			default:
				return "error loading data";
			}

		}

	}

}
