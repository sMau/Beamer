/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI;

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

public class FileManagerModel extends AbstractTableModel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 122422256133966805L;
	private MediaHandler mediaHandler;
	private LinkedList<MediaFile> mediaFiles;
	private String[] columnNames = {"", "", "Name", "Priority", "Type", "Show At"};
	
    public FileManagerModel() {
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
	    	if(rowObject.getStatus().getWasShowed()) {
	    		return "#";	
	    	} else {
	    		return "";	
	    	}
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
 
    public void updateModel() {
    	fireTableDataChanged();
    }
    
}
