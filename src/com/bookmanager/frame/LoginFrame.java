package com.bookmanager.frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;

import java.awt.Color;

public class LoginFrame extends JFrame implements ActionListener, KeyListener {

	private String userName;// �û���¼��
	private String userPassword;// �û���¼����
	private JPanel namePanel, pwPanel, btPanel;// �����ֱ�Ϊ������顢������Ͱ�ť���
	private JButton cfButton, ccButton;
	private JTextField nameFiled;
	private JPasswordField pwFiled;
	private CommonService cs;

	public LoginFrame() {
		setBackground(Color.LIGHT_GRAY);
		this.userName = null;
		this.userPassword = null;
		this.initControl();
		this.initFrame();
		
		this.cs = CommonService.getCommonService();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * ��ʼ���ؼ�����Ӳ��
	 */
	public void initControl() {
		// ���Ƚ��пؼ��Ĵ���
		this.ccButton = new JButton("ȡ��");
		this.cfButton = new JButton("��¼");
		this.btPanel = new JPanel();
		this.pwPanel = new JPanel();
		this.namePanel = new JPanel();
		this.nameFiled = new JTextField(12);
		this.pwFiled = new JPasswordField(13);

		// ��ӿؼ���������ȥ
		this.namePanel.add(new JLabel("�û�����"));
		this.namePanel.add(this.nameFiled);
		this.pwPanel.add(new JLabel("���룺"));
		this.pwPanel.add(this.pwFiled);
		this.btPanel.add(this.cfButton);
		this.btPanel.add(this.ccButton);

		// ��Ӵμ�����������������ȥ
		JLabel text = new JLabel("  ��ӭʹ��ͼ�����ϵͳ��");
		text.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(text);
		getContentPane().add(this.namePanel);
		getContentPane().add(this.pwPanel);
		getContentPane().add(this.btPanel);

		// ��Ӱ�ť����������
		this.cfButton.setActionCommand("LOGIN");
		this.cfButton.addActionListener(this);
		this.ccButton.setActionCommand("EXIT");
		this.ccButton.addActionListener(this);
		
		//����������̼�����
		this.nameFiled.addKeyListener(this);
		this.pwFiled.addKeyListener(this);
	}

	/**
	 * ���ô��ڵ�һϵ������
	 */
	public void initFrame() {
		this.setSize(300, 300);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(4, 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("�û���¼");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if (ac.equals("LOGIN")) {
			// ��ʼ����Ϣ
			Reader reader = new Reader(nameFiled.getText(),
					String.valueOf(pwFiled.getPassword()));

			if (this.cs.isLegitimateUser(reader)) {
				this.loginSuccessd(reader);
			} else {
				this.loginFailed();
			}
		} else if (ac.equals("EXIT")) {
			this.dispose();
		}
	}

	public void loginSuccessd(Reader reader) {
		reader.setPassword(null);
		// if(user.getType() == 0) {
		// //��ʾ����Ա����
		// }
		// else {
		// //��ʾ�û�����
		// }
		this.dispose();
		MainFrame mf = new MainFrame(reader);
	}

	/**
	 * ��¼ʧ�ܣ�������������������ʾ����
	 */
	public void loginFailed() {
		JOptionPane.showMessageDialog(null, "�û���������������������룡", "��¼ʧ��",
				JOptionPane.ERROR_MESSAGE);
		this.nameFiled.setText("");
		this.pwFiled.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ���̼�����ESC���͡��س�������
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 10) {
			this.cfButton.doClick();
		}
		else if(e.getKeyCode() == 27) {
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
