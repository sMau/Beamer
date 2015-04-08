package de.netprojectev.desktop.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import de.netprojectev.common.utils.LoggerBuilder;

public class TableButtonRenderer extends JButton implements TableCellRenderer {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(TableButtonRenderer.class);

	/**
	 *
	 */
	private static final long serialVersionUID = -2922120099859842011L;
	private final boolean add;

	public TableButtonRenderer(boolean add) {
		setOpaque(true);
		this.add = add;
		if (this.add) {
			ImageIcon iconPlus = new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/client/gfx/plus_2.png"));
			try {
				iconPlus = Misc.getScaledImageIcon(iconPlus, 12);
			} catch (FileNotFoundException e) {
				log.log(Level.WARNING, "could not load add image icon.", e);
			} catch (IOException e) {
				log.log(Level.WARNING, "could not load add image icon.", e);
			}
			setIcon(iconPlus);
		} else {
			ImageIcon iconDelete = new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/client/gfx/delete_2.png"));
			try {
				iconDelete = Misc.getScaledImageIcon(iconDelete, 12);
			} catch (FileNotFoundException e) {
				log.log(Level.WARNING, "could not load delete image icon.", e);
			} catch (IOException e) {
				log.log(Level.WARNING, "could not load delete image icon.", e);
			}
			setIcon(iconDelete);
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {

	/**
	 *
	 */
	private static final long serialVersionUID = 5485754352878058031L;

	public interface TableButtonActionListener {
		public void buttonClicked(int row);
	}

	private final TableButtonActionListener l;
	protected JButton button;
	private int row;
	private String label;

	private boolean isPushed;

	public ButtonEditor(JCheckBox checkBox, TableButtonActionListener l) {
		super(checkBox);
		this.l = l;
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.row = row;
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			l.buttonClicked(row);
		}
		isPushed = false;
		return new String(label);
	}

	@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}