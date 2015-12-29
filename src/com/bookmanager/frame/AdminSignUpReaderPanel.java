package com.bookmanager.frame;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

import com.bookmanager.model.Reader;

public class AdminSignUpReaderPanel extends JPanel implements ActionListener{
	
	private JTextField nameField;
	private JTextField mobileField;
	private JTextField phoneField;
	private JTextField cardIDField;
	private JComboBox sexBox;
	private JComboBox cardTypeBox;
	private JComboBox yearBox;
	private JComboBox monthBox;
	private JComboBox dayBox;
	private JComboBox levelBox;
	
	public AdminSignUpReaderPanel(String[] cardType, String[] level) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(37, 24, 81, 21);
		add(label);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(140, 21, 110, 28);
		add(nameField);
		
		JLabel label_1 = new JLabel("\u6027\u522B\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(37, 63, 81, 21);
		add(label_1);
		
		JLabel label_2 = new JLabel("\u751F\u65E5\uFF1A");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(37, 100, 81, 21);
		add(label_2);
		
		JLabel label_3 = new JLabel("\u56FA\u5B9A\u7535\u8BDD\uFF1A");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(37, 140, 81, 21);
		add(label_3);
		
		JLabel label_4 = new JLabel("\u624B\u673A\u53F7\uFF1A");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(163, 201, 81, 21);
		add(label_4);
		
		mobileField = new JTextField();
		mobileField.setColumns(10);
		mobileField.setBounds(266, 198, 214, 28);
		add(mobileField);
		
		JLabel label_5 = new JLabel("\u8BC1\u4EF6\u7C7B\u578B\uFF1A");
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(163, 244, 81, 21);
		add(label_5);
		
		JLabel title = new JLabel("<html>\u8BFB<br/>\u8005<br/>\u767B<br/>\u8BB0</html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Color.MAGENTA);
		title.setFont(new Font("微软雅黑", Font.BOLD, 50));
		title.setBounds(10, 171, 110, 274);
		add(title);
		
		JButton signUp = new JButton("\u7528\u6237\u6CE8\u518C");
		signUp.setForeground(Color.GREEN);
		signUp.setFont(new Font("楷体", Font.PLAIN, 20));
		signUp.setActionCommand("ADD");
		signUp.setBounds(163, 382, 127, 53);
		add(signUp);
		
		JButton reset = new JButton("\u91CD\u7F6E\u6240\u6709");
		reset.setForeground(Color.BLACK);
		reset.setFont(new Font("楷体", Font.PLAIN, 20));
		reset.setActionCommand("RESET");
		reset.setBounds(353, 382, 127, 53);
		add(reset);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(140, 137, 161, 28);
		add(phoneField);
		
		cardTypeBox = new JComboBox(cardType);
		cardTypeBox.setBounds(266, 237, 160, 28);
		add(cardTypeBox);
		
		JLabel label_6 = new JLabel("\u5E74");
		label_6.setFont(new Font("宋体", Font.BOLD, 15));
		label_6.setBounds(203, 100, 16, 21);
		add(label_6);
		
		JLabel label_7 = new JLabel("\u6708");
		label_7.setFont(new Font("宋体", Font.BOLD, 15));
		label_7.setBounds(284, 100, 16, 21);
		add(label_7);
		
		JLabel label_8 = new JLabel("\u65E5");
		label_8.setFont(new Font("宋体", Font.BOLD, 15));
		label_8.setBounds(362, 100, 16, 21);
		add(label_8);
		
		JLabel label_9 = new JLabel("\u8BC1\u4EF6\u53F7\uFF1A");
		label_9.setFont(new Font("宋体", Font.BOLD, 15));
		label_9.setBounds(163, 278, 81, 21);
		add(label_9);
		
		cardIDField = new JTextField();
		cardIDField.setColumns(10);
		cardIDField.setBounds(266, 275, 214, 28);
		add(cardIDField);
		
		JLabel label_10 = new JLabel("\u4F1A\u5458\u7B49\u7EA7\uFF1A");
		label_10.setFont(new Font("宋体", Font.BOLD, 15));
		label_10.setBounds(163, 317, 81, 21);
		add(label_10);
		
		levelBox = new JComboBox(level);
		levelBox.setBounds(266, 313, 96, 28);
		add(levelBox);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("image/signUp.png"));
		lblNewLabel.setBounds(419, 10, 220, 425);
		add(lblNewLabel);
		
		initComboBox();
		setAllButtonActionListener(signUp, reset);
	}

	private void initComboBox() {
		yearBox = new JComboBox();
		setYearBox(yearBox);
		yearBox.setBounds(140, 96, 56, 28);
		add(yearBox);
		
		monthBox = new JComboBox();
		setMonthBox(monthBox);
		monthBox.setBounds(227, 95, 47, 28);
		add(monthBox);
		
		dayBox = new JComboBox();
		updateDayMothBox(dayBox, (int)yearBox.getSelectedItem(), (int)monthBox.getSelectedItem());
		dayBox.setBounds(307, 93, 47, 28);
		add(dayBox);
		
		sexBox = new JComboBox();
		sexBox.addItem("男");
		sexBox.addItem("女");
		sexBox.setBounds(140, 59, 79, 28);
		add(sexBox);
	}
	
	private void setYearBox(JComboBox year) {
		for(int i = 2015 ; i >= 1960 ; i--) {
			year.addItem(i);
		}
		year.setMaximumRowCount(5);
		year.setSelectedIndex(15);
	}
	
	private void setMonthBox(JComboBox month) {
		for(int i = 1 ; i < 13 ; i++) {
			month.addItem(i);
		}
		month.setMaximumRowCount(5);
		month.setSelectedIndex(0);
	}
	
	private void updateDayMothBox(JComboBox day, int year, int month) {
		day.removeAllItems();
		int max = getMaxday(year, month);
		for(int i = 1 ; i <= max ; i++) {
			day.addItem(i);
		}
		day.setMaximumRowCount(5);
	}
	
	private int getMaxday(int year, int month) {
		Calendar cal = Calendar.getInstance();
		if(month == 1) {
			month = 12;
			year -= 1;
		}
		else {
			month -= 1;
		}
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		return cal.getActualMaximum(Calendar.DATE);
	}

	private void setAllButtonActionListener(JButton sign, JButton reset) {
		sign.setActionCommand("SIGNUP");
		sign.addActionListener(this);
		reset.setActionCommand("RESET");
		reset.addActionListener(this);
		yearBox.setActionCommand("TIME");
		yearBox.addActionListener(this);
		monthBox.setActionCommand("TIME");
		monthBox.addActionListener(this);
	}
	
	private void resetAllField() {
		this.cardIDField.setText("");
		this.mobileField.setText("");
		this.nameField.setText("");
		this.phoneField.setText("");
		sexBox.setSelectedIndex(0);
		cardTypeBox.setSelectedIndex(0);
		yearBox.setSelectedIndex(15);
		monthBox.setSelectedIndex(0);
		updateDayMothBox(dayBox, (int)yearBox.getSelectedItem(), (int)monthBox.getSelectedItem());
		levelBox.setSelectedIndex(0);
	}
	
	private Reader getCurrentReader() {
		Reader reader = new Reader();
		reader.setName(nameField.getText());
		reader.setSex((String)sexBox.getSelectedItem());
		reader.setBirthday( (int)yearBox.getSelectedItem(), 
				(int)monthBox.getSelectedItem(), 
				(int)dayBox.getSelectedItem());
		if(!phoneField.getText().equals("")) {
			reader.setPhone(Integer.parseInt(phoneField.getText()));
		}
		reader.setMobile(mobileField.getText());
		reader.setCardName((String)cardTypeBox.getSelectedItem());
		reader.setCardId(cardIDField.getText());
		reader.setLevel((String)levelBox.getSelectedItem());
		reader.setPassword(reader.getCardId().substring(reader.getCardId().length()-6));
		reader.setSignUpTime(new Date());
		return reader;
	}
	
	private String detectionInput() {
		if (nameField.getText().equals("")) {
			return "姓名不可为空，请重新输入！";
		}
		if (mobileField.getText().equals("")) {
			return "手机号不可为空，请重新输入！";
		}
		if (cardIDField.getText().equals("")) {
			return "证件号不可为空，请重新输入！";
		}
		if(cardIDField.getText().length() < 6) {
			return "证件号长度不正确，请重新输入！";
		}
		try {
			Long.parseLong(mobileField.getText());
		} catch (NumberFormatException e) {
			return "手机号格式不正确，请重新输入！";
		}
		if (!phoneField.getText().equals("")) {
			try {
				Integer.parseInt(phoneField.getText());
			} catch (NumberFormatException e) {
				return "固定电话格式不正确，请重新输入！";
			}
		}
		try {
			Long.parseLong(mobileField.getText());
		} catch (NumberFormatException e) {
			return "手机号格式不正确，请重新输入！";
		}
		try {
			Long.parseLong(cardIDField.getText());
		} catch (NumberFormatException e) {
			return "证件号格式不正确，请重新输入！";
		}
		
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "SIGNUP":
			String tmp;
			MainFrame mf = (MainFrame) JOptionPane.getFrameForComponent(this);
			if ((tmp = detectionInput()) != null) {
				JOptionPane.showMessageDialog(mf, tmp, "提醒",
						JOptionPane.YES_OPTION);
				break;
			}
			mf.signUpReaderToDB(getCurrentReader());
			resetAllField();
			break;
			
		case "RESET":
			resetAllField();
			break;
		
		case "TIME":
			updateDayMothBox(dayBox, (int)yearBox.getSelectedItem(), 
					(int)monthBox.getSelectedItem());
			break;
		}
	}

	
	
}
