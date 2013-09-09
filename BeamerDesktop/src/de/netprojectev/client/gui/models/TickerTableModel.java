package de.netprojectev.client.gui.models;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.client.model.TickerModelClient.UpdateTickerDataListener;

public class TickerTableModel extends AbstractTableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1213494137268165971L;
	private final TickerModelClient tickerModel; 
	private final String[] columns = { " ", "Text" };
	
	public TickerTableModel(TickerModelClient tickerModel) {
		this.tickerModel = tickerModel;
		
		this.tickerModel.setTickerDateListener(new UpdateTickerDataListener() {
			
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
		return tickerModel.getElements().size();
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
		if(columnIndex == 0) {
			return Boolean.class;
		}
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientTickerElement e = tickerModel.getValueAt(rowIndex);
		switch(columnIndex) {
			case 0:
				return e.isShow();
			case 1: 
				return e.getText();
			default:
				return "Error loading data";
		
		}	
	}

}
