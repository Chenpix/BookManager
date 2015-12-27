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
	private List<CheckOutRecord> recordList;

	private CommonService commonService;
	private UserService userService;
	private AdminService adminService;

	private CommonSearchPanel commonSearch;
	private UserButtonListPanel userButtonList;
	private CommonTablePanel searchBookResult;
	private CommonTablePanel searchUserResult;
	private CommonTablePanel userCheckOutRecord;
	private CommonTablePanel overDueRecord;
	private CommonTablePanel returnBook;
	private AdminLayUpBookPanel layUpBook;
	private AdminButtonListPanel adminButtonList;
	private AdminSearchUserPanel searchUser;
	private AdminSignUpReaderPanel signUp;
	private JPanel rightPanel;

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.bookList = new ArrayList<Book>();
		this.readerList = new ArrayList<Reader>();
		this.recordList = new ArrayList<CheckOutRecord>();
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
		this.adminButtonList.getSignUpButton().setActionCommand("SIGNUP");
		this.adminButtonList.getSignUpButton().addActionListener(this);
		this.adminButtonList.getOverDueButton().setActionCommand("OVERDUE");
		this.adminButtonList.getOverDueButton().addActionListener(this);
		this.adminButtonList.getReturnBookButton().setActionCommand("RETURN");
		this.adminButtonList.getReturnBookButton().addActionListener(this);
	}

	private void initCommonButtonListener() {
		this.commonSearch.getSearchButton().setActionCommand("SEARCH");
		this.commonSearch.getSearchButton().addActionListener(this);
	}

	private void initFrame() {
		this.setSize(800, 500);
		this.setResizable(false);
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
			this.recordList.clear();
			if (!genCheckOutRecordPanel()) {
				// 借阅记录列表为空
				JOptionPane.showMessageDialog(this, "查询失败！", "提醒",
						JOptionPane.YES_OPTION);
			}
			break;

		case "INFO":
			commonService.showReaderInfor(reader);
			break;
			
		case "READER":
			this.readerList.clear();
			this.loadRightPanel(AdminService.READER);
			break;

		case "LAYUP":
			this.loadRightPanel(AdminService.LAYUP);
			break;

		case "SIGNUP":
			this.loadRightPanel(AdminService.SIGNUP);
			break;

		case "OVERDUE":
			recordList.clear();
			if (!genOverDueRecordPanel()) {
				JOptionPane.showMessageDialog(this, "暂时没有逾期未还的的图书！", "查询失败",
						JOptionPane.YES_OPTION);
			}
			break;

		case "RETURN":
			recordList.clear();
			if (!genReturnBookPanel()) {
				JOptionPane.showMessageDialog(this, "暂时没有待还的的图书！", "查询失败",
						JOptionPane.YES_OPTION);
			}
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
			break;

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
				layUpBook = new AdminLayUpBookPanel(
						adminService.getAllCategoryOrLevel(AdminService.LAYUP));
			}
			this.rightPanel.add(layUpBook);
			break;

		case AdminService.SIGNUP:
			if (this.signUp == null) {
				signUp = new AdminSignUpReaderPanel(adminService.getCardType(),
						adminService.getAllCategoryOrLevel(AdminService.SIGNUP));
			}
			this.rightPanel.add(signUp);
			break;

		case AdminService.ODRECORD:
			this.rightPanel.add(overDueRecord);
			break;
			
		case AdminService.RLIST:
			this.rightPanel.add(returnBook);
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

		updateSearchResultPanel();
		return true;
	}

	/**
	 * 更新查找结果界面
	 */
	private void updateSearchResultPanel() {
		// TODO
		String[] head = commonService.getBookTableHead(reader.getLevel());
		this.searchBookResult = new CommonTablePanel(
				UserService.formatBookList(bookList, head.length), head,
				commonService.getBookTableWidth(bookButtonType),
				CommonService.HEIGHT, true, bookButtonType);
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
		if(selectedValue == null) {
			return false;
		}
		
		if (userService.getBorrowRecordList(recordList, reader,
				(String) selectedValue)) {
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
	 * 生成逾期记录面板
	 * 
	 * @return
	 */
	private boolean genOverDueRecordPanel() {
		if (adminService.getOverDueRecordList(recordList)) {
			overDueRecord = new CommonTablePanel(
					adminService.formatOverDueRecord(recordList),
					adminService.getOverDueRecordTableHead(),
					adminService.getOverDueRecordTableWidth(),
					CommonService.HEIGHT, true, CommonTablePanel.ODUE);
			loadRightPanel(AdminService.ODRECORD);
			return true;
		}
		return false;
	}

	private boolean updateOverDueTable() {
		if(recordList.size() == 0) {
			JOptionPane.showMessageDialog(this, "已无逾期记录，返回主页面！", "系统信息",
					JOptionPane.YES_OPTION);
			loadRightPanel(CommonService.SEARCHPANEL);
			return false;
		}
		overDueRecord = new CommonTablePanel(
				adminService.formatOverDueRecord(recordList),
				adminService.getOverDueRecordTableHead(),
				adminService.getOverDueRecordTableWidth(),
				CommonService.HEIGHT, true, CommonTablePanel.ODUE);
		loadRightPanel(AdminService.ODRECORD);
		return true;
	}

	/**
	 * 生成图书归还列表
	 * 
	 * @return
	 */
	private boolean genReturnBookPanel() {
		if (adminService.getReturnList(recordList)) {
			returnBook = new CommonTablePanel(
					adminService.formatReturnList(recordList),
					adminService.getReturnTableHead(),
					adminService.getReturnTableWidth(), commonService.HEIGHT,
					true, CommonTablePanel.RBOOK);
			loadRightPanel(AdminService.RLIST);
			return true;
		}
		return false;
	}

	private boolean updateReturnTable() {
		if(recordList.size() == 0) {
			JOptionPane.showMessageDialog(this, "已无待还书记录，返回主页面！", "系统信息",
					JOptionPane.YES_OPTION);
			loadRightPanel(CommonService.SEARCHPANEL);
			return false;
		}
		returnBook = new CommonTablePanel(
				adminService.formatReturnList(recordList),
				adminService.getReturnTableHead(),
				adminService.getReturnTableWidth(), commonService.HEIGHT,
				true, CommonTablePanel.RBOOK);
		loadRightPanel(AdminService.RLIST);
		return true;
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
		if ( !adminService.getReaderList(readerList, name, id)) {
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
	 * @param book
	 *            待存储书
	 */
	public void layUpBookToDB(Book book) {
		book.setBookId(adminService.genBookID("b",
				adminService.getLastID(AdminService.LAYUP)));
		adminService.layUpBook(book);
	}

	/**
	 * 对外接口——挂失
	 * 
	 * @param index
	 */
	public void signBookLoss(int index) {
		if (adminService.signLossRecord(recordList.get(index))) {
			JOptionPane.showMessageDialog(this, "挂失成功！", "系统信息",
					JOptionPane.YES_OPTION, new ImageIcon("image/success.png"));
			recordList.remove(index);
			updateOverDueTable();
		} else {
			JOptionPane.showMessageDialog(this, "挂失失败！", "系统信息",
					JOptionPane.YES_OPTION);
		}
	}

	/**
	 * 对外接口——还书
	 * 
	 * @param index
	 */
	public void returnBook(int index) {
		if (adminService.updateReturnBook(recordList.get(index))) {
			JOptionPane.showMessageDialog(this, "还书成功！", "系统信息",
					JOptionPane.YES_OPTION, new ImageIcon("image/success.png"));
			recordList.remove(index);
			updateReturnTable();
		}
		else {
			JOptionPane.showMessageDialog(this, "还书失败！", "系统信息",
					JOptionPane.YES_OPTION);
		}
	}

	/**
	 * 对外接口——用户信息存入数据库
	 * 
	 * @param reader
	 *            待存储用户
	 */
	public void signUpReaderToDB(Reader reader) {
		reader.setId(adminService.genBookID("r",
				adminService.getLastID(AdminService.SIGNUP)));
		adminService.signUpReader(reader);
		JOptionPane.showMessageDialog(this, "注册成功！该读者的编号为" + reader.getId() 
				+ "，请保存好自己的ID号！", "系统信息",
				JOptionPane.YES_OPTION);
	}
}
