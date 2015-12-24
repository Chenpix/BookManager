package com.bookmanager.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bookmanager.model.Book;
import com.bookmanager.model.CheckOutRecord;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.common.Sentence;
import com.bookmanager.sql.user.UserService;

public class MainFrame extends JFrame implements ActionListener {

	private Reader reader;
	private List<Book> bookList;
	private int contentPanel;// 指定当前的右面板

	private CommonService commonService;
	private UserService userService;

	private UserSearchPanel userSearch;
	private UserButtonListPanel userButtonList;
	private CommonBookListPanel userSearchResult;
	private UserCheckOutRecordPanel userCheckOutRecord;
	private JPanel rightPanel;

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.contentPanel = UserService.SEARCHPANEL;
		this.initAll();
	}

	private void initAll() {
		this.initFrame();
		this.initContent();
		this.initService();
	}

	private void initService() {
		this.commonService = CommonService.getCommonServiceInstance();
		this.userService = UserService.getUserServiceInstance();
	}

	public void initContent() {
		if (this.reader.getLevel().equals("ADMIN")) {

		} else {
			initUserFrame();
		}
	}

	private void initButtonList() {
		this.userButtonList.getLendButton().setActionCommand("LEND");
		this.userButtonList.getLendButton().addActionListener(this);
		this.userSearch.getSearchButton().setActionCommand("SEARCH");
		this.userSearch.getSearchButton().addActionListener(this);
		this.userButtonList.getRecordButton().setActionCommand("RECORD");
		this.userButtonList.getRecordButton().addActionListener(this);
	}

	private void initUserFrame() {
		// TODO Auto-generated method stub
		this.userButtonList = new UserButtonListPanel();
		this.add(this.userButtonList, BorderLayout.WEST);
		this.userSearch = new UserSearchPanel();
		this.rightPanel = new JPanel(new GridLayout(1, 1));
		this.rightPanel.add(userSearch);
		this.add(this.rightPanel, BorderLayout.CENTER);
		initButtonList();
	}

	public void initFrame() {
		this.setSize(800, 500);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("图书管理系统");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "SEARCH":
			if (!genSearchResultPanel()) {
				// 书目列表为空
				JOptionPane.showMessageDialog(this, "没有符合条件的书目，请重新输入搜索条件！",
						"查询失败", JOptionPane.YES_OPTION);
				this.userSearch.resetAllField();
			}
			break;
		case "LEND":
			this.loadRightPanel(UserService.SEARCHPANEL);
			break;

		case "RECORD":
			if (!genCheckOutRecordPanel()) {
				// 借阅记录列表为空
				JOptionPane.showMessageDialog(this, "暂时没有符合条件的借阅记录！", "查询失败",
						JOptionPane.YES_OPTION);
			}
			break;
		}
	}

	/**
	 * 根据参数选择右面板加载内容
	 * 
	 * @param choice
	 */
	private void loadRightPanel(int choice) {
		this.rightPanel.removeAll();
		switch (choice) {

		case UserService.SEARCHPANEL:
			this.rightPanel.add(userSearch);
			this.bookList.clear();
			break;

		case UserService.RESULTPANEL:
			this.rightPanel.add(userSearchResult);
			break;

		case UserService.RECORDPANEL:
			this.rightPanel.add(userCheckOutRecord);

		default:
			break;
		}
		this.contentPanel = choice;
		this.rightPanel.updateUI();
	}

	/**
	 * 替换查找界面为查找结果界面
	 * 
	 * @return 替换成功
	 */
	private boolean genSearchResultPanel() {
		if ((bookList = commonService.getBookList(this.userSearch
				.getBookInfor())) == null) {
			return false;
		}
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList));
		loadRightPanel(UserService.RESULTPANEL);
		return true;
	}

	/**
	 * 更新查找结果界面
	 */
	private void updateSearchResultPanel() {
		// TODO
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList));
		loadRightPanel(UserService.RESULTPANEL);
	}

	/**
	 * 生成借阅记录面板
	 * 
	 * @return
	 */
	private boolean genCheckOutRecordPanel() {
		Object[] possibleValues = { "半年内", "一年内" };
		Object selectedValue = JOptionPane.showInputDialog(null,
				"请选择您要查询的记录时间", "借阅记录", JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
		List<CheckOutRecord> recordList = this.userService.getBorrowRecordList(
				reader, (String) selectedValue);
		if (recordList != null) {
			this.userCheckOutRecord = new UserCheckOutRecordPanel(
					this.userService.formatUserCheckOutRecord(recordList));
			loadRightPanel(UserService.RECORDPANEL);
			return true;
		}
		return false;
	}

	/**
	 * 用户借书相关动作
	 */
	public void checkOutBook(int orderNumber) {
		// 检测借书数量是否达到上限
		if (this.userService.isReachBookCeiling(reader)) {
			JOptionPane.showMessageDialog(this, "借阅失败，已达当前会员等级借阅最大数量！请还书后再借阅！",
					"提示", JOptionPane.YES_OPTION);
			return;
		}

		// 执行库存数量变化操作
		this.userService.updateBookQuanForCheckOut(bookList.get(orderNumber));

		// 增加借书记录
		this.userService
				.insertCheckOutRecord(bookList.get(orderNumber), reader);

		// 借书本数加一
		this.userService.updateBorrowNumber(reader);

		// 弹窗提示借书成功
		JOptionPane.showMessageDialog(this, "借阅成功！记得按时归还哦~", "提示",
				JOptionPane.YES_OPTION);

		// 更新面板
		this.updateSearchResultPanel();
	}

}
