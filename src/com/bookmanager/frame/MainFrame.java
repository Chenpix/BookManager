package com.bookmanager.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bookmanager.model.Book;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.common.Sentence;
import com.bookmanager.sql.user.UserService;

public class MainFrame extends JFrame implements ActionListener {

	private static final String[] checkOutHead = { "书号", "书名", "作者", "出版社",
			"馆藏数量", "  " };
	private static final int SEARCHPANEL = 1000;
	private static final int PINFOPANEL = 1001;
	private static final int RETURNPANEL = 1002;
	private static final int LOSSPANEL = 1003;
	private static final int RECORDPANEL = 1004;
	private static final int RESULTPANEL = 1005;

	private Reader reader;
	private UserSearchPanel userSearch;
	private UserButtonListPanel userButtonList;
	private CommonService commonService;
	private UserService userService;
	private List<Book> bookList;
	private CommonBookListPanel userSearchResult;
	private JPanel rightPanel;
	private int contentPanel;// 指定当前的右面板

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.contentPanel = MainFrame.SEARCHPANEL;
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
			this.loadRightPanel(MainFrame.SEARCHPANEL);
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

		case MainFrame.SEARCHPANEL:
			this.rightPanel.add(userSearch);
			this.bookList.clear();
			break;

		case MainFrame.RESULTPANEL:
			this.rightPanel.add(userSearchResult);
		default:
			break;
		}
		this.contentPanel = choice;
		this.rightPanel.updateUI();
	}

	private boolean genSearchResultPanel() {
		// TODO 替换查找界面为查找结果界面
		if ((bookList = commonService.getBookList(this.userSearch
				.getBookInfor())) == null) {
			return false;
		}
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList),
				MainFrame.checkOutHead);
		loadRightPanel(RESULTPANEL);
		return true;
	}

	private void updateSearchResultPanel() {
		// TODO 更新查找结果界面
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList),
				MainFrame.checkOutHead);
		loadRightPanel(RESULTPANEL);
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
