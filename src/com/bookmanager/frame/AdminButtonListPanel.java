package com.bookmanager.frame;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class AdminButtonListPanel extends JPanel {
	
	private JButton searchBookButton;
	private JButton searchUserButton;
	private JButton layUpBookButton;
	private JButton applyButton;
	private JButton returnBookButton;
	private JButton overDueButton;
	private JButton noteLossButton;
	
	public JButton getSearchBookButton() {
		return searchBookButton;
	}

	public JButton getSearchUserButton() {
		return searchUserButton;
	}

	public JButton getLayUpBookButton() {
		return layUpBookButton;
	}

	public JButton getApplyButton() {
		return applyButton;
	}

	public JButton getReturnBookButton() {
		return returnBookButton;
	}

	public JButton getOverDueButton() {
		return overDueButton;
	}

	public JButton getNoteLossButton() {
		return noteLossButton;
	}
	
	public AdminButtonListPanel() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, new Color(128, 128, 128), null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{6, 111, 0, 0};
		gridBagLayout.rowHeights = new int[]{20, 50, 10, 50, 10, 50, 10, 50, 10, 50, 10, 50, 10, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		searchBookButton = new JButton("ËÑË÷ÊéÄ¿");
		GridBagConstraints gbc_searchBookButton = new GridBagConstraints();
		gbc_searchBookButton.fill = GridBagConstraints.BOTH;
		gbc_searchBookButton.insets = new Insets(0, 0, 5, 5);
		gbc_searchBookButton.gridx = 1;
		gbc_searchBookButton.gridy = 1;
		add(searchBookButton, gbc_searchBookButton);
		
		searchUserButton = new JButton("\u67E5\u8BE2\u7528\u6237");
		GridBagConstraints gbc_searchUserButton = new GridBagConstraints();
		gbc_searchUserButton.fill = GridBagConstraints.BOTH;
		gbc_searchUserButton.insets = new Insets(0, 0, 5, 5);
		gbc_searchUserButton.gridx = 1;
		gbc_searchUserButton.gridy = 3;
		add(searchUserButton, gbc_searchUserButton);
		
		layUpBookButton = new JButton("\u56FE\u4E66\u5165\u5E93");
		GridBagConstraints gbc_insertBookButton = new GridBagConstraints();
		gbc_insertBookButton.fill = GridBagConstraints.BOTH;
		gbc_insertBookButton.insets = new Insets(0, 0, 5, 5);
		gbc_insertBookButton.gridx = 1;
		gbc_insertBookButton.gridy = 5;
		add(layUpBookButton, gbc_insertBookButton);
		
		applyButton = new JButton("\u8BFB\u8005\u767B\u8BB0");
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.fill = GridBagConstraints.BOTH;
		gbc_applyButton.insets = new Insets(0, 0, 5, 5);
		gbc_applyButton.gridx = 1;
		gbc_applyButton.gridy = 7;
		add(applyButton, gbc_applyButton);
		
		returnBookButton = new JButton("\u56FE\u4E66\u5F52\u8FD8");
		GridBagConstraints gbc_returnBookButton = new GridBagConstraints();
		gbc_returnBookButton.fill = GridBagConstraints.BOTH;
		gbc_returnBookButton.insets = new Insets(0, 0, 5, 5);
		gbc_returnBookButton.gridx = 1;
		gbc_returnBookButton.gridy = 9;
		add(returnBookButton, gbc_returnBookButton);
		
		overDueButton = new JButton("\u903E\u671F\u67E5\u8BE2");
		GridBagConstraints gbc_overDueButton = new GridBagConstraints();
		gbc_overDueButton.fill = GridBagConstraints.BOTH;
		gbc_overDueButton.insets = new Insets(0, 0, 5, 5);
		gbc_overDueButton.gridx = 1;
		gbc_overDueButton.gridy = 11;
		add(overDueButton, gbc_overDueButton);
		
		noteLossButton = new JButton("\u6302\u5931\u5904\u7406");
		GridBagConstraints gbc_noteLossButton = new GridBagConstraints();
		gbc_noteLossButton.insets = new Insets(0, 0, 0, 5);
		gbc_noteLossButton.fill = GridBagConstraints.BOTH;
		gbc_noteLossButton.gridx = 1;
		gbc_noteLossButton.gridy = 13;
		add(noteLossButton, gbc_noteLossButton);
	}
}
