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

	private String userName;//用户登录名
	private String userPassword;//用户登录密码
	private JPanel namePanel,pwPanel,btPanel;//三个分别为姓名板块、密码板块和按钮板块
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
	 * 初始化控件的添加层次
	 */
	public void initControl() {
		//首先进行控件的创建
		this.ccButton = new JButton("取消");
		this.cfButton = new JButton("登录");
		this.btPanel = new JPanel();
		this.pwPanel = new JPanel();
		this.namePanel = new JPanel();
		this.nameFiled = new JTextField(12);
		this.pwFiled = new JPasswordField(13);
		
		//添加下级控件到次级控件中去
		this.namePanel.add(new JLabel("用户名："));
		this.namePanel.add(this.nameFiled);
		this.pwPanel.add(new JLabel("密码："));
		this.pwPanel.add(this.pwFiled);
		this.btPanel.add(this.cfButton);
		this.btPanel.add(this.ccButton);
		
		//添加次级控件到顶级控件中去
		JLabel text = new JLabel("  欢迎使用图书管理系统：");
		text.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(text);
		this.add(this.namePanel);
		this.add(this.pwPanel);
		this.add(this.btPanel);
		
		//添加动作监听器
		this.cfButton.setActionCommand("LOGIN");
		this.cfButton.addActionListener(this);
		this.ccButton.setActionCommand("EXIT");
		this.ccButton.addActionListener(this);
	}
	
	/**
	 * 设置窗口的一系列属性
	 */
	public void initFrame() {
		this.setSize(300, 300);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(4,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("登录");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if( ac.equals("LOGIN") ) {
			//初始化信息
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
//			//显示管理员界面
//		}
//		else {
//			//显示用户界面
//		}
		this.dispose();
		MainFrame mf = new MainFrame(user);
	}

	/**
	 * 登录失败，重置输入栏，弹窗提示错误
	 */
	public void loginFailed() {
		JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新输入！", "登录失败", JOptionPane.ERROR_MESSAGE);
		this.nameFiled.setText("");
		this.pwFiled.setText("");
	}
}
