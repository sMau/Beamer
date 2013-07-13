package de.netprojectev.client.gui.tablemodels;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.model.TickerModelClient;

public class TickerTableModel extends AbstractTableModel {

	public interface UpdateTickerDataListener {
		public void update();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1213494137268165971L;
	private final TickerModelClient tickerModel; 
	private final String[] columns = { "Show", "Text" };
	
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
		java.awt.EventQueue.invokeLater(new Runnable() {

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
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
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