/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Main;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Themeslide;
import de.netprojectev.Media.VideoFile;
import de.netprojectev.MediaHandler.MediaHandler;

/**
 *
 * 
 */

//TODO Comlumns: Name and Prio, directly editable

public class FileManagerTableModel extends AbstractTableModel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 122422256133966805L;
	private MediaHandler mediaHandler;
	private LinkedList<MediaFile> mediaFiles;
	private String[] columnNames = {"", "", "Name", "Priority", "Type", "Show At"};
	
    public FileManagerTableModel() {
        super();
        mediaHandler = MediaHandler.getInstance();
        mediaFiles = mediaHandler.getMediaFiles();
                
    }

    @Override
    public int getRowCount() {
        return mediaFiles.size();
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
    	
    	MediaFile rowObject = mediaFiles.get(rowIndex);
    	
    	String type = "";
    	
    	if(rowObject instanceof VideoFile) {
    		type = "Video";
    	} else if(rowObject instanceof ImageFile) {
    		type = "Image";
    	} else if(rowObject instanceof Themeslide) {
    		type = "Themeslide";
    	} else {
    		type = "undefined";
    	}
    	
    	//TODO in case 0 und 1 sollten Icons gerendert werden anstatt Textzeichen
	    switch (columnIndex) {
	    case 0:
	    	if(rowObject.getStatus().getIsCurrent()) {
	    		return ">";
	    	} else {
	    		return "";
	    	}
	    case 1:
	    	return rowObject.getStatus().getWasShowed();
		case 2:
			return rowObject.getName();	
		case 3:
			return rowObject.getPriority().getName();
		case 4:
			return type;	
		case 5:
			return rowObject.getStatus().getShowAt();
		default:
			return "undefined";
		}
    	
    }
    
    @Override
    public void setValueAt( Object val, int row, int column ) {
    	
    }
    
    @Override
	public Class<?> getColumnClass(int columnIndex) {
    	switch (columnIndex) {
	    case 0:
	    	return String.class;
	    case 1:
	    	return Boolean.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		default:
			return Object.class;
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
