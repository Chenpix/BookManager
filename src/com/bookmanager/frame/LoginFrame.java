package com.bookmanager.frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.User;

public class LoginFrame extends JFrame implements ActionListener{

	private String userName;//�û���¼��
	private String userPassword;//�û���¼����
	private JPanel namePanel,pwPanel,btPanel;//�����ֱ�Ϊ������顢������Ͱ�ť���
	private JButton cfButton,ccButton;
	private JTextField nameFiled;
	private JPasswordField pwFiled;
	
	public LoginFrame() {
		this.userName = null;
		this.userPassword = null;
		this.initControl();
		this.initFrame();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	/**
	 * ��ʼ���ؼ�����Ӳ��
	 */
	public void initControl() {
		//���Ƚ��пؼ��Ĵ���
		this.ccButton = new JButton("ȡ��");
		this.cfButton = new JButton("��¼");
		this.btPanel = new JPanel();
		this.pwPanel = new JPanel();
		this.namePanel = new JPanel();
		this.nameFiled = new JTextField(12);
		this.pwFiled = new JPasswordField(13);
		
		//����¼��ؼ����μ��ؼ���ȥ
		this.namePanel.add(new JLabel("�û�����"));
		this.namePanel.add(this.nameFiled);
		this.pwPanel.add(new JLabel("���룺"));
		this.pwPanel.add(this.pwFiled);
		this.btPanel.add(this.cfButton);
		this.btPanel.add(this.ccButton);
		
		//��Ӵμ��ؼ��������ؼ���ȥ
		JLabel text = new JLabel("  ��ӭʹ��ͼ�����ϵͳ��");
		text.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(text);
		this.add(this.namePanel);
		this.add(this.pwPanel);
		this.add(this.btPanel);
		
		//��Ӷ���������
		this.cfButton.setActionCommand("LOGIN");
		this.cfButton.addActionListener(this);
		this.ccButton.setActionCommand("EXIT");
		this.ccButton.addActionListener(this);
	}
	
	/**
	 * ���ô��ڵ�һϵ������
	 */
	public void initFrame() {
		this.setSize(300, 300);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(4,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("��¼");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if( ac.equals("LOGIN") ) {
			//��ʼ����Ϣ
			ODBCConnection oc = new ODBCConnection();
			User user = new User(nameFiled.getText(), pwFiled.getText());
			
			if(oc.isLegitimateUser(user)) {
				this.loginSuccessd(user);
			}
			else {
				this.loginFailed();
			}
		}
		else if( ac.equals("EXIT") ) {
			this.dispose();
		}
	}

	public void loginSuccessd(User user) {
		user.setPassword(null);
//		if(user.getType() == 0) {
//			//��ʾ����Ա����
//		}
//		else {
//			//��ʾ�û�����
//		}
		this.dispose();
		MainFrame mf = new MainFrame(user);
	}

	/**
	 * ��¼ʧ�ܣ�������������������ʾ����
	 */
	public void loginFailed() {
		JOptionPane.showMessageDialog(null, "�û���������������������룡", "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
		this.nameFiled.setText("");
		this.pwFiled.setText("");
	}
}
