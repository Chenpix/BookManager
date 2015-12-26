package com.bookmanager.frame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.bookmanager.model.Reader;

public class AdminSearchUserPanel extends JPanel implements ActionListener{
	
	private JTextField readerIDField;
	private JTextField readerNameField;
	
	public AdminSearchUserPanel() {
		setBackground(Color.LIGHT_GRAY);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{135, 80, 8, 118, 101, 0, 0};
		gridBagLayout.rowHeights = new int[]{46, 79, 67, 30, 31, 30, 89, 53, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel image = new JLabel("New label");
		GridBagConstraints gbc_image = new GridBagConstraints();
		gbc_image.gridwidth = 5;
		gbc_image.insets = new Insets(0, 0, 5, 5);
		gbc_image.gridx = 1;
		gbc_image.gridy = 1;
		add(image, gbc_image);
		
		JLabel title = new JLabel("<html>搜<br/>索<br/>用<br/>户</html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBackground(Color.WHITE);
		title.setForeground(Color.BLUE);
		title.setFont(new Font("华文彩云", Font.BOLD, 50));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.gridheight = 5;
		gbc_title.fill = GridBagConstraints.BOTH;
		gbc_title.insets = new Insets(0, 0, 5, 0);
		gbc_title.gridwidth = 4;
		gbc_title.gridx = 5;
		gbc_title.gridy = 2;
		add(title, gbc_title);
		
		JLabel id = new JLabel("\u8BFB\u8005\u7F16\u53F7\uFF1A");
		id.setFont(new Font("宋体", Font.BOLD, 15));
		GridBagConstraints gbc_id = new GridBagConstraints();
		gbc_id.anchor = GridBagConstraints.WEST;
		gbc_id.fill = GridBagConstraints.VERTICAL;
		gbc_id.insets = new Insets(0, 0, 5, 5);
		gbc_id.gridx = 1;
		gbc_id.gridy = 3;
		add(id, gbc_id);
		
		readerIDField = new JTextField();
		GridBagConstraints gbc_readerIDField = new GridBagConstraints();
		gbc_readerIDField.fill = GridBagConstraints.HORIZONTAL;
		gbc_readerIDField.insets = new Insets(0, 0, 5, 5);
		gbc_readerIDField.gridwidth = 3;
		gbc_readerIDField.gridx = 2;
		gbc_readerIDField.gridy = 3;
		add(readerIDField, gbc_readerIDField);
		readerIDField.setColumns(10);
		
		JLabel name = new JLabel("\u8BFB\u8005\u59D3\u540D\uFF1A");
		name.setFont(new Font("宋体", Font.BOLD, 15));
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.anchor = GridBagConstraints.WEST;
		gbc_name.fill = GridBagConstraints.VERTICAL;
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.gridx = 1;
		gbc_name.gridy = 5;
		add(name, gbc_name);
		
		readerNameField = new JTextField();
		readerNameField.setColumns(10);
		GridBagConstraints gbc_readerNameField = new GridBagConstraints();
		gbc_readerNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_readerNameField.insets = new Insets(0, 0, 5, 5);
		gbc_readerNameField.gridwidth = 3;
		gbc_readerNameField.gridx = 2;
		gbc_readerNameField.gridy = 5;
		add(readerNameField, gbc_readerNameField);
		
		JButton searchButton = new JButton("\u641C\u7D22");
		searchButton.setFont(new Font("华文行楷", Font.BOLD, 20));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.BOTH;
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridwidth = 2;
		gbc_searchButton.gridx = 1;
		gbc_searchButton.gridy = 7;
		add(searchButton, gbc_searchButton);
		
		JButton resetButton = new JButton("\u91CD\u7F6E");
		resetButton.setFont(new Font("华文行楷", Font.BOLD, 20));
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetButton.fill = GridBagConstraints.BOTH;
		gbc_resetButton.gridx = 4;
		gbc_resetButton.gridy = 7;
		add(resetButton, gbc_resetButton);
		
		addActionListener(searchButton, resetButton);
	}
	
	private void addActionListener(JButton search, JButton reset) {
		search.setActionCommand("SEARCH");
		search.addActionListener(this);
		reset.setActionCommand("RESET");
		reset.addActionListener(this);
	}

	private void resetAllField() {
		this.readerIDField.setText("");
		this.readerNameField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch( e.getActionCommand() ) {
		case "SEARCH":
			MainFrame mf = (MainFrame) JOptionPane.getFrameForComponent(this);
			if( !mf.searchUser(readerNameField.getText(), readerIDField.getText())) {
				JOptionPane.showMessageDialog(this, "查询失败，没有符合搜索条件的用户！",
						"提示", JOptionPane.YES_OPTION);
				resetAllField();
			}
			break;
		case "RESET":
			resetAllField();
			break;
		}
	}
}
