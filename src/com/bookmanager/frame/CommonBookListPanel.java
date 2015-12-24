package com.bookmanager.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class CommonBookListPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final int[] width = { 50, 100, 50, 150, 80, 50 };
	private final int height = 30;
	private final String[] checkOutHead = { "书号", "书名", "作者", "出版社",
		"馆藏数量", "  " };
	
	private JTable table = null;
	private DefaultTableModel model = null;

	public CommonBookListPanel(Object[][] data) {

		model = new DefaultTableModel(data, checkOutHead);
		table = new JTable(model);
		MyRender myRender = new MyRender(getRemainNumber(data));
		table.getColumnModel().getColumn(checkOutHead.length - 1)
				.setCellEditor(myRender);// 设置编辑器
		table.getColumnModel().getColumn(checkOutHead.length - 1)
				.setCellRenderer(myRender);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setTableColumnWidthAndHeight(this.table, this.width,
				this.height);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 1*1格子布局，方便填满
		this.setLayout(new GridLayout(1, 1));
		// 加边界留白
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(scrollPane);
		this.setBackground(Color.LIGHT_GRAY);
		
		this.setAllButtonListener(myRender, data.length);
	}
	
	private void setAllButtonListener(MyRender myRender, int length) {
		for (int i = 0; i < length; i++) {
			if(myRender.getButton(i) != null) {
				myRender.getButton(i).setActionCommand(String.valueOf(i));
				myRender.getButton(i).addActionListener(this);
			}
		}
	}

	private int[] getRemainNumber(Object[][] data) {
		int[] remainNumber = new int[data.length];
		for(int i = 0 ; i < data.length ; i++) {
			remainNumber[i] = (int) data[i][4];
		}
		return remainNumber;
	}

	private TableColumnModel setTableColumnWidthAndHeight(JTable table, int[] width,
			int height) {
		// TODO Auto-generated method stub
		TableColumnModel columns = table.getColumnModel();  
	    for (int i = 0; i < width.length; i++) {  
	        TableColumn column = columns.getColumn(i);  
	        column.setPreferredWidth(width[i]);  
	    }  
	    table.setRowHeight(height);
	    return columns;  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainFrame mf = (MainFrame) JOptionPane.getFrameForComponent(this);
		mf.checkOutBook(Integer.parseInt(e.getActionCommand()));
	}
}

/**
 * 渲染器编辑器
 * 
 * @author Chenpix
 *
 */
class MyRender extends AbstractCellEditor implements TableCellRenderer,
		TableCellEditor{

	private static final long serialVersionUID = 1L;
	private JButton[] button;

	/**
	 * 根据传入的书本剩余数量构造Render
	 * @param remainNumber 书本剩余数量
	 */
	public MyRender(int[] remainNumber) {
		this.button = new JButton[remainNumber.length];
		for(int i = 0 ; i < remainNumber.length ; i++) {
			if(remainNumber[i] <= 0) {
				button[i] = null;
			}
			else {
				button[i] = new JButton("借阅");
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