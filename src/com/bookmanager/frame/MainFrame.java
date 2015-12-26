package com.bookmanager.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bookmanager.model.Book;
import com.bookmanager.model.CheckOutRecord;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.admin.AdminService;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.user.UserService;

public class MainFrame extends JFrame implements ActionListener {

	private int clickTimes;
	private String bookButtonType;
	private Reader reader;
	private List<Book> bookList;
	private List<Reader> readerList;

	private CommonService commonService;
	private UserService userService;
	private AdminService adminService;

	private CommonSearchPanel commonSearch;
	private UserButtonListPanel userButtonList;
	private CommonTablePanel searchBookResult;
	private CommonTablePanel searchUserResult;
	private CommonTablePanel userCheckOutRecord;
	private AdminLayUpBookPanel layUpBook;
	private AdminButtonListPanel adminButtonList;
	private AdminSearchUserPanel searchUser;
	private JPanel rightPanel;

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.bookList = new ArrayList<Book>();
		this.readerList = new ArrayList<Reader>();
		this.clickTimes = 0;
		this.initAll();
	}

	private void initAll() {
		this.initFrame();
		this.initContent(reader.getLevel());
	}

	private void initUserService() {
		this.commonService = CommonService.getCommonServiceInstance();
		this.userService = UserService.getUserServiceInstance();
	}

	private void initAdminService() {
		this.commonService = CommonService.getCommonServiceInstance();
		this.adminService = AdminService.getAdminServiceInstance();
	}

	private void initContent(String level) {

		this.commonSearch = new CommonSearchPanel();
		this.rightPanel = new JPanel(new GridLayout(1, 1));
		this.rightPanel.add(commonSearch);
		this.add(this.rightPanel, BorderLayout.CENTER);

		if (level.equals("ADMIN")) {
			this.adminButtonList = new AdminButtonListPanel();
			this.add(this.adminButtonList, BorderLayout.WEST);
			initAdminButtonListener();
			initAdminService();
			bookButtonType = CommonTablePanel.BINFO;
		} else {
			this.userButtonList = new UserButtonListPanel();
			this.add(this.userButtonList, BorderLayout.WEST);
			initUserButtonListener();
			initUserService();
			bookButtonType = CommonTablePanel.CHECK;
		}
		initCommonButtonListener();
	}

	private void initUserButtonListener() {
		this.userButtonList.getLendButton().setActionCommand("LEND");
		this.userButtonList.getLendButton().addActionListener(this);
		this.userButtonList.getRecordButton().setActionCommand("RECORD");
		this.userButtonList.getRecordButton().addActionListener(this);
		this.userButtonList.getInfoButton().setActionCommand("INFO");
		this.userButtonList.getInfoButton().addActionListener(this);
	}

	private void initAdminButtonListener() {
		this.adminButtonList.getSearchBookButton().setActionCommand("LEND");
		this.adminButtonList.getSearchBookButton().addActionListener(this);
		this.adminButtonList.getSearchUserButton().setActionCommand("READER");
		this.adminButtonList.getSearchUserButton().addActionListener(this);
		this.adminButtonList.getLayUpBookButton().setActionCommand("LAYUP");
		this.adminButtonList.getLayUpBookButton().addActionListener(this);
	}

	private void initCommonButtonListener() {
		this.commonSearch.getSearchButton().setActionCommand("SEARCH");
		this.commonSearch.getSearchButton().addActionListener(this);
	}

	private void initFrame() {
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
				this.commonSearch.resetAllField();
			}
			break;
		case "LEND":
			this.loadRightPanel(CommonService.SEARCHPANEL);
			break;

		case "RECORD":
			if (!genCheckOutRecordPanel()) {
				// 借阅记录列表为空
				JOptionPane.showMessageDialog(this, "暂时没有符合条件的借阅记录！", "查询失败",
						JOptionPane.YES_OPTION);
			}
			break;
			
		case "READER":
			this.readerList.clear();
			this.loadRightPanel(AdminService.READER);
			break;
			
		case "LAYUP":
			this.loadRightPanel(AdminService.LAYUP);
			break;
		}
		if (clickTimes++ == 3) {
			System.gc();
			clickTimes = 0;
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

		case CommonService.SEARCHPANEL:
			this.rightPanel.add(commonSearch);
			this.bookList.clear();
			break;

		case UserService.RESULTPANEL:
			this.rightPanel.add(searchBookResult);
			break;

		case UserService.RECORDPANEL:
			this.rightPanel.add(userCheckOutRecord);

		case AdminService.READER:
			if (this.searchUser == null) {
				searchUser = new AdminSearchUserPanel();
			}
			this.rightPanel.add(searchUser);
			break;

		case AdminService.URESULT:
			this.rightPanel.add(searchUserResult);
			break;

		case AdminService.LAYUP:
			if (this.layUpBook == null) {
				layUpBook = new AdminLayUpBookPanel(adminService.getAllCategory());
			}
			this.rightPanel.add(layUpBook);
			break;
			
		default:
			break;
		}
		this.rightPanel.updateUI();
	}

	/**
	 * 替换查找界面为查找结果界面
	 * 
	 * @return 替换成功
	 */
	private boolean genSearchResultPanel() {
		if ((bookList = commonService.getBookList(this.commonSearch
				.getBookInfor())) == null) {
			return false;
		}

		this.searchBookResult = new CommonTablePanel(
				UserService.formatBookList(bookList),
				commonService.getBookTableHead(reader.getLevel()),
				commonService.getBookTableWidth(), CommonService.HEIGHT, true,
				bookButtonType);
		loadRightPanel(UserService.RESULTPANEL);
		return true;
	}

	/**
	 * 更新查找结果界面
	 */
	private void updateSearchResultPanel() {
		// TODO
		this.searchBookResult = new CommonTablePanel(
				UserService.formatBookList(bookList),
				commonService.getBookTableHead(reader.getLevel()),
				commonService.getBookTableWidth(), CommonService.HEIGHT, true,
				bookButtonType);
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
			userCheckOutRecord = new CommonTablePanel(
					UserService.formatUserCheckOutRecord(recordList),
					commonService.getRecordTableHead(),
					commonService.getRecordTableWidth(), CommonService.HEIGHT,
					false, null);
			loadRightPanel(UserService.RECORDPANEL);
			return true;
		}
		return false;
	}

	/**
	 * 对外接口——用户借书动作
	 * 
	 * @param index
	 *            选中行号
	 */
	public void checkOutBook(int index) {

		// 检测当前用户是否被挂失

		// 检测借书数量是否达到上限
		if (commonService.isReachBookCeiling(reader)) {
			JOptionPane.showMessageDialog(this, "借阅失败，已达当前会员等级借阅最大数量！请还书后再借阅！",
					"提示", JOptionPane.YES_OPTION);
			return;
		}

		// 执行库存数量变化操作
		this.userService.updateBookQuanForCheckOut(bookList.get(index));

		// 增加借书记录
		this.userService.insertCheckOutRecord(bookList.get(index), reader);

		// 借书本数加一
		this.userService.updateBorrowNumber(reader);

		// 弹窗提示借书成功
		JOptionPane.showMessageDialog(null, "借阅成功！记得按时归还哦~", "提示",
				JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));

		// 更新面板
		this.updateSearchResultPanel();
	}

	/**
	 * 对外接口——管理员查询用户功能
	 * 
	 * @param name
	 *            用户姓名（支持模糊）
	 * @param id
	 *            用户编号
	 * @return
	 */
	public boolean searchUser(String name, String id) {
		if ((readerList = adminService.getReaderList(name, id)) == null) {
			return false;
		}
		this.searchUserResult = new CommonTablePanel(
				adminService.formatUserList(readerList),
				adminService.getReaderTableHead(),
				adminService.getReaderTableWidth(), CommonService.HEIGHT, true,
				CommonTablePanel.UINFO);
		loadRightPanel(AdminService.URESULT);
		return true;
	}

	/**
	 * 对外接口——对话框显示读者信息细节
	 * 
	 * @param index
	 *            选中行号
	 */
	public void showReaderDetail(int index) {
		commonService.showReaderInfor(readerList.get(index));
	}

	/**
	 * 对外接口——对话框显示书本信息细节
	 * 
	 * @param index
	 *            选中行号
	 */
	public void showBookDetail(int index) {
		adminService.showBookDetail(bookList.get(index));
	}

	/**
	 * 对外接口——书籍存入数据库
	 * 
	 * @param book 待存储书
	 */
	public void layUpBookToDB(Book book) {
		book.setBookId(adminService.genBookID("b", adminService.getLastBookID()));
		adminService.layUpBook(book);
	}
}
