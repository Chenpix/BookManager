package com.bookmanager.sql.common;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * ��Ⱦ���༭��
 * 
 * @author Chenpix
 *
 */
public class MyRender extends AbstractCellEditor implements TableCellRenderer,
		TableCellEditor{

	private static final long serialVersionUID = 1L;
	private JButton[] button;

	/**
	 * ���ݸ����Ĳ���������Ⱦ���༭��
	 * @param isNull ��Ӧ���Ƿ�Ӧ��button
	 * @param buttonName 
	 */
	public MyRender(boolean[] isNull, String buttonName) {
		button = new JButton[isNull.length];
		for (int i = 0; i < isNull.length; i++) {
			if( !isNull[i] ) {
				button[i] = new JButton(buttonName);
			}
		}
		
	}

	public JButton getButton(int i) {
		return button[i];
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return button[row];
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return button[row];
	}

}
