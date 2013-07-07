/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old.de.netprojectev.client.gui.manager;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import old.de.netprojectev.Countdown;
import old.de.netprojectev.ImageFile;
import old.de.netprojectev.MediaHandlerOld;
import old.de.netprojectev.PreferencesModelOld;
import old.de.netprojectev.ServerMediaFile;
import old.de.netprojectev.Themeslide;
import old.de.netprojectev.VideoFile;

import de.netprojectev.misc.Constants;

/**
 * This is the model for the @see JTable in the Filemanager.
 * It connects the MediaHandler to the JTable.
 * @author samu
 *
 */
public class FileManagerTableModel extends AbstractTableModel {
    
	//TODO sometimes (i think after deserialiazation) the prios arent selectable in the table. after adding a new one the old ones were present again too
	
	private static final long serialVersionUID = 122422256133966805L;
	private MediaHandlerOld mediaHandler;
	private PreferencesModelOld preferencesHandler;
	private LinkedList<ServerMediaFile> mediaFiles;
	private String[] columnNames = {"", "", "Name", "Priority", "Type", "Show At"};
	private ManagerFrame managerFrame;
	
    public FileManagerTableModel(ManagerFrame managerFrame) {
        super();
        mediaHandler = MediaHandlerOld.getInstance();
        mediaFiles = mediaHandler.getMediaFiles();
        this.managerFrame = managerFrame;
        preferencesHandler = PreferencesModelOld.getInstance();
                
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
    	
    	ServerMediaFile rowObject = mediaFiles.get(rowIndex);
    	
    	String type = "";
    	
    	if(rowObject instanceof VideoFile) {
    		type = "Video";
    	} else if(rowObject instanceof ImageFile) {
    		type = "Image";
    	} else if(rowObject instanceof Themeslide) {
    		type = "Themeslide - " + ((Themeslide) rowObject).getTheme().getName();
    	} else if(rowObject instanceof Countdown){
    		type = "Countdown";
    	} else {
    		type = "undefined";
    	}
    	
    	
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
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 3) {
			return true;
		} else {
			return false;
		}
	}
    
    @Override
    public void setValueAt( Object val, int row, int column ) {
    	if(column == 2) {			
			mediaFiles.get(row).setName(val.toString());
		}
    	if(column == 3) {
    		if(!mediaFiles.get(row).getStatus().getIsCurrent()) {
    			if(preferencesHandler.searchForPriority(val.toString()) != null) {
        			mediaFiles.get(row).setPriority(preferencesHandler.searchForPriority(val.toString()));
        		} else {
        			mediaFiles.get(row).setPriority(Constants.DEFAULT_PRIORITY);
        		}
    		}
    	}
    	managerFrame.onSelectionChangeJtableFile();
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

	public MediaHandlerOld getMediaHandler() {
		return mediaHandler;
	}

	public PreferencesModelOld getPreferencesHandler() {
		return preferencesHandler;
	}
    
}
