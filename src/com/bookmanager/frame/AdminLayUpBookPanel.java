package com.bookmanager.frame;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.bookmanager.model.Book;

public class AdminLayUpBookPanel extends JPanel implements ActionListener {

	private JTextField bookNameField;
	private JTextField authorField;
	private JTextField publishingField;
	private JTextField priceField;
	private JTextField quanField;
	private JComboBox categoryBox;

	public AdminLayUpBookPanel(String[] combo) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JLabel label = new JLabel("\u4E66\u540D\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(61, 58, 81, 21);
		add(label);

		bookNameField = new JTextField();
		bookNameField.setColumns(10);
		bookNameField.setBounds(164, 55, 214, 28);
		add(bookNameField);

		JLabel label_1 = new JLabel("\u4F5C\u8005\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(61, 109, 81, 21);
		add(label_1);

		authorField = new JTextField();
		authorField.setColumns(10);
		authorField.setBounds(164, 106, 214, 28);
		add(authorField);

		JLabel label_2 = new JLabel("\u51FA\u7248\u793E\uFF1A");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(61, 162, 81, 21);
		add(label_2);

		publishingField = new JTextField();
		publishingField.setColumns(10);
		publishingField.setBounds(164, 159, 214, 28);
		add(publishingField);

		JLabel label_3 = new JLabel("\u7C7B\u522B\uFF1A");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(61, 219, 81, 21);
		add(label_3);

		JLabel label_4 = new JLabel("\u4EF7\u683C\uFF1A");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(61, 271, 81, 21);
		add(label_4);

		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(164, 268, 214, 28);
		add(priceField);

		JLabel label_5 = new JLabel("\u6570\u91CF\uFF1A");
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(61, 323, 81, 21);
		add(label_5);

		quanField = new JTextField();
		quanField.setColumns(10);
		quanField.setBounds(164, 320, 214, 28);
		add(quanField);

		categoryBox = new JComboBox(combo);
		categoryBox.setBounds(164, 215, 214, 28);
		add(categoryBox);

		JLabel lblNewLabel = new JLabel("<html>图<br/>书<br/>入<br/>库</html>");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 50));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(446, 58, 110, 359);
		add(lblNewLabel);

		JButton addButton = new JButton("\u6DFB\u52A0\u56FE\u4E66");
		addButton.setFont(new Font("楷体", Font.PLAIN, 20));
		addButton.setForeground(Color.RED);
		addButton.setBounds(61, 364, 127, 53);
		add(addButton);

		JButton resetButton = new JButton("\u91CD\u7F6E\u6240\u6709");
		resetButton.setFont(new Font("楷体", Font.PLAIN, 20));
		resetButton.setForeground(Color.BLACK);
		resetButton.setBounds(251, 364, 127, 53);
		add(resetButton);

		setAllButtonListener(addButton, resetButton);
	}

	private void setAllButtonListener(JButton addButton, JButton resetButton) {
		addButton.setActionCommand("ADD");
		addButton.addActionListener(this);
		resetButton.setActionCommand("RESET");
		resetButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "ADD":
			String tmp;
			MainFrame mf = (MainFrame) JOptionPane.getFrameForComponent(this);
			if ((tmp = detectionInput()) != null) {
				JOptionPane.showMessageDialog(mf, tmp, "提醒",
						JOptionPane.YES_OPTION);
				break;
			}
			mf.layUpBookToDB(getCurrentBook());
			JOptionPane.showMessageDialog(mf, "书本录入成功!", "恭喜",
					JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));
			resetAll();
			break;
		case "RESET":
			resetAll();
			break;
		}
	}

	private void resetAll() {
		bookNameField.setText("");
		authorField.setText("");
		publishingField.setText("");
		categoryBox.setSelectedIndex(0);
		priceField.setText("");
		quanField.setText("");
	}

	private String detectionInput() {
		if (bookNameField.getText().equals("")) {
			return "姓名不可为空，请重新输入！";
		}
		if (authorField.getText().equals("")) {
			return "作者不可为空，请重新输入！";
		}
		if (publishingField.getText().equals("")) {
			return "出版社不可为空，请重新输入！";
		}
		if (priceField.getText().equals("")) {
			return "价格不可为空，请重新输入！";
		}
		if (quanField.getText().equals("")) {
			return "入库数量不可为空，请重新输入！";
		}
		try {
			Double.parseDouble(priceField.getText());
		} catch (NumberFormatException e) {
			return "图书价格格式不正确，请重新输入！";
		}
		try {
			Integer.parseInt(quanField.getText());
		} catch (NumberFormatException e) {
			return "图书入库数量格式不正确，请重新输入！";
		}
		
		return null;
	}

	private Book getCurrentBook() {
		Book book = new Book();
		book.setBookName(bookNameField.getText());
		book.setAuthor(authorField.getText());
		book.setPublishing(publishingField.getText());
		book.setCategoryid("ca0" + String.valueOf(categoryBox.getSelectedIndex()+1));
		book.setPrice(Double.parseDouble(priceField.getText()));
		book.setQuanIn(Integer.parseInt(quanField.getText()));
		return book;
	}

}
