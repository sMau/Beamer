package de.netprojectev.GUI.Main;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.LiveTicker.TickerTextElement;

public class TickerManagerTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1436775879502873621L;
	private LiveTicker liveTicker;
	private LinkedList<TickerTextElement> textElements;
	private String[] columnNames = {"Show", "Text"};
	
	public TickerManagerTableModel() {
		super();
		this.liveTicker = LiveTicker.getInstance();
		this.textElements = liveTicker.getTextElements();
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
	
	
	public void updateModel() {
    	java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                fireTableDataChanged();
            }
        });
    	
    }
	
}
