package com.bookmanager.frame;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.ImageIcon;

import com.bookmanager.model.Book;
import com.bookmanager.sql.common.CommonService;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Dimension;

public class UserSearchPanel extends JPanel implements ActionListener {

	private JTextField bookIdField;
	private JTextField publishingField;
	private JTextField bookNameField;
	private JTextField authorField;
	private CommonService commonService;
	private JPanel inforPanel;
	private JButton searchButton;

	public UserSearchPanel() {
		setBackground(Color.LIGHT_GRAY);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 47, 115, 124, 120, 35, 115, 0 };
		gridBagLayout.rowHeights = new int[] { 58, 328, 53, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		inforPanel = new JPanel();
		inforPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u67E5\u627E",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(105,
						105, 105)));
		inforPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_inforPanel = new GridBagConstraints();
		gbc_inforPanel.fill = GridBagConstraints.BOTH;
		gbc_inforPanel.insets = new Insets(0, 0, 5, 5);
		gbc_inforPanel.gridwidth = 3;
		gbc_inforPanel.gridx = 1;
		gbc_inforPanel.gridy = 1;
		add(inforPanel, gbc_inforPanel);
		inforPanel.setLayout(null);

		JLabel label = new JLabel("\u4E66\u53F7\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(26, 99, 81, 21);
		inforPanel.add(label);

		bookIdField = new JTextField();
		bookIdField.setColumns(10);
		bookIdField.setBounds(129, 96, 214, 28);
		inforPanel.add(bookIdField);

		JLabel label_1 = new JLabel("\u51FA\u7248\u793E\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(26, 162, 81, 21);
		inforPanel.add(label_1);

		publishingField = new JTextField();
		publishingField.setColumns(10);
		publishingField.setBounds(129, 159, 214, 28);
		inforPanel.add(publishingField);

		JLabel label_2 = new JLabel("\u4E66\u540D\uFF1A");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(26, 38, 81, 21);
		inforPanel.add(label_2);

		bookNameField = new JTextField();
		bookNameField.setColumns(10);
		bookNameField.setBounds(129, 35, 214, 28);
		inforPanel.add(bookNameField);

		JLabel label_3 = new JLabel("\u4F5C\u8005\uFF1A");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(26, 223, 81, 21);
		inforPanel.add(label_3);

		authorField = new JTextField();
		authorField.setColumns(10);
		authorField.setBounds(129, 220, 214, 28);
		inforPanel.add(authorField);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 5;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(1, 1, 0, 0));

		JLabel ImageLabel = new JLabel("");
		ImageLabel.setIcon(new ImageIcon(
				"C:\\Users\\Public\\Pictures\\Sample Pictures\\view.jpg"));
		panel_1.add(ImageLabel);

		searchButton = new JButton("\u67E5\u627E");
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.BOTH;
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridx = 1;
		gbc_searchButton.gridy = 2;
		add(searchButton, gbc_searchButton);
		this.commonService = CommonService.getCommonService();

		JButton resetButton = new JButton("\u91CD\u7F6E");
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.fill = GridBagConstraints.BOTH;
		gbc_resetButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetButton.gridx = 3;
		gbc_resetButton.gridy = 2;
		add(resetButton, gbc_resetButton);

		resetButton.setActionCommand("RESET");
		resetButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if (ac.equals("RESET")) {
			resetAllField();
		}
	}

	public Book getBookInfor() {
		return new Book(this.bookIdField.getText(),
				this.bookNameField.getText(), this.authorField.getText(),
				this.publishingField.getText());
	}

	/**
	 * 清空所有输入栏
	 */
	public void resetAllField() {
		this.authorField.setText("");
		this.bookIdField.setText("");
		this.bookNameField.setText("");
		this.publishingField.setText("");
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}

	/**
	 * 将当前panel所附着的控件全部去掉
	 */
	@Override
	public void removeAll() {
		// TODO Auto-generated method stub

	}
}
