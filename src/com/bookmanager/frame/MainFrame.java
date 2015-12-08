package com.bookmanager.frame;

import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.bookmanager.model.User;

public class MainFrame extends JFrame {
	
	private User user;
	
	public MainFrame() {
	}
	
	public MainFrame(User user) {
		this.user = user;
		this.initAll();
	}

	public void initAll() {
		this.initFrame();
		this.initContent();
	}
	
	public void initContent() {
		if( user.getType() == 0) {
			this.add(new JLabel("这是管理员界面"));
		}
		else if( user.getType() == 1 ) {
			this.add(new JLabel("这是用户界面"));
		}
	}
	
	public void initFrame() {
		this.setSize(800, 500);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("主界面");
	}
}
