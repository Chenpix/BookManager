package com.bookmanager.frame;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class UserButtonListPanel extends JPanel {
	
	private JButton lendButton;
	private JButton infoButton;
	private JButton recordButton;
	
	public JButton getLendButton() {
		return lendButton;
	}

	public JButton getInfoButton() {
		return infoButton;
	}

	public JButton getRecordButton() {
		return recordButton;
	}
	
	public UserButtonListPanel() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, Color.DARK_GRAY, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{6, 109, 0, 0};
		gridBagLayout.rowHeights = new int[]{25, 51, 32, 51, 35, 51, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lendButton = new JButton("我要借书");
		GridBagConstraints gbc_lendButton = new GridBagConstraints();
		gbc_lendButton.fill = GridBagConstraints.BOTH;
		gbc_lendButton.insets = new Insets(0, 0, 5, 5);
		gbc_lendButton.gridx = 1;
		gbc_lendButton.gridy = 1;
		add(lendButton, gbc_lendButton);
		
		recordButton = new JButton("历史借阅");
		GridBagConstraints gbc_recordButton = new GridBagConstraints();
		gbc_recordButton.fill = GridBagConstraints.BOTH;
		gbc_recordButton.insets = new Insets(0, 0, 5, 5);
		gbc_recordButton.gridx = 1;
		gbc_recordButton.gridy = 3;
		add(recordButton, gbc_recordButton);
		
		infoButton = new JButton("个人信息");
		GridBagConstraints gbc_infoButton = new GridBagConstraints();
		gbc_infoButton.insets = new Insets(0, 0, 0, 5);
		gbc_infoButton.fill = GridBagConstraints.BOTH;
		gbc_infoButton.gridx = 1;
		gbc_infoButton.gridy = 5;
		add(infoButton, gbc_infoButton);
	}
	
}
