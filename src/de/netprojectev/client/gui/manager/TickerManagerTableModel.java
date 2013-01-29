package de.netprojectev.client.gui.manager;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import de.netprojectev.server.datastructures.liveticker.TickerTextElement;

/**
 * This is the model for the LiveTicker table in the ManagerFrame.
 * It connects the LiveTicker to the JTable.
 * @author samu
 *
 */
public class TickerManagerTableModel extends AbstractTableModel {

	//TODO on changing the text inside the table directly -> add a autoupdate to the live ticker
	
	private static final long serialVersionUID = -1436775879502873621L;
	private LinkedList<TickerTextElement> textElements;
	private String[] columnNames = {"Show", "Text"};
	
	public TickerManagerTableModel() {
		super();
		// TODO client structure for ticker
		//this.liveTicker = LiveTicker.getInstance();
		// this.textElements = liveTicker.getTextElements();
	}

	@Override
	public int getRowCount() {
		return textElements.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0) {
			return textElements.get(rowIndex).getToShow();
		} else {
			return textElements.get(rowIndex).getText();
		}
			
	}
	
	@Override
    public void setValueAt(Object val, int row, int column) {
	
		if(column == 0) {			
			textElements.get(row).setToShow(((Boolean)val).booleanValue());
		}
		if(column == 1) {
			textElements.get(row).setText(val.toString());
		}
    }
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0) {
        	return Boolean.class;
        } else {
        	return String.class;
        }
    }

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || columnIndex == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Updating the table View to the current Data Changes on the Model.
     * Uses invokeLater for clean and thread-save event handling.
     */
	public void updateModel() {
    	java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                fireTableDataChanged();
            }
        });
    	
    }
	
}
