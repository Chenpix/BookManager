package com.bookmanager.frame;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UserButtonListPanel extends JPanel {
	
	private JButton lendButton;
	private JButton returnButton;
	private JButton infoButton;
	private JButton lossButton;
	private JButton recordButton;
	
	public JButton getLendButton() {
		return lendButton;
	}

	public JButton getReturnButton() {
		return returnButton;
	}

	public JButton getInfoButton() {
		return infoButton;
	}

	public JButton getLossButton() {
		return lossButton;
	}

	public JButton getRecordButton() {
		return recordButton;
	}
	
	public UserButtonListPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{18, 114, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{31, 56, 30, 56, 30, 56, 30, 56, 30, 56, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lendButton = new JButton("我要借书");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		add(lendButton, gbc_btnNewButton);
		
		returnButton = new JButton("我要还书");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 3;
		add(returnButton, gbc_button);
		
		infoButton = new JButton("个人信息");
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.BOTH;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 1;
		gbc_button_3.gridy = 5;
		add(infoButton, gbc_button_3);
		
		lossButton = new JButton("我要挂失");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.BOTH;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 7;
		add(lossButton, gbc_button_1);
		
		recordButton = new JButton("历史借阅");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.fill = GridBagConstraints.BOTH;
		gbc_button_2.gridx = 1;
		gbc_button_2.gridy = 9;
		add(recordButton, gbc_button_2);
	}
	
}
