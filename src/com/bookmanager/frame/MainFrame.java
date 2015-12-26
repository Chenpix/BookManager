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
		this.setTitle("ͼ�����ϵͳ");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "SEARCH":
			if (!genSearchResultPanel()) {
				// ��Ŀ�б�Ϊ��
				JOptionPane.showMessageDialog(this, "û�з�����������Ŀ����������������������",
						"��ѯʧ��", JOptionPane.YES_OPTION);
				this.commonSearch.resetAllField();
			}
			break;
		case "LEND":
			this.loadRightPanel(CommonService.SEARCHPANEL);
			break;

		case "RECORD":
			if (!genCheckOutRecordPanel()) {
				// ���ļ�¼�б�Ϊ��
				JOptionPane.showMessageDialog(this, "��ʱû�з��������Ľ��ļ�¼��", "��ѯʧ��",
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
	 * ���ݲ���ѡ��������������
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
	 * �滻���ҽ���Ϊ���ҽ������
	 * 
	 * @return �滻�ɹ�
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
	 * ���²��ҽ������
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
	 * ���ɽ��ļ�¼���
	 * 
	 * @return
	 */
	private boolean genCheckOutRecordPanel() {
		Object[] possibleValues = { "������", "һ����" };
		Object selectedValue = JOptionPane.showInputDialog(null,
				"��ѡ����Ҫ��ѯ�ļ�¼ʱ��", "���ļ�¼", JOptionPane.INFORMATION_MESSAGE, null,
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
	 * ����ӿڡ����û����鶯��
	 * 
	 * @param index
	 *            ѡ���к�
	 */
	public void checkOutBook(int index) {

		// ��⵱ǰ�û��Ƿ񱻹�ʧ

		// �����������Ƿ�ﵽ����
		if (commonService.isReachBookCeiling(reader)) {
			JOptionPane.showMessageDialog(this, "����ʧ�ܣ��Ѵﵱǰ��Ա�ȼ���������������뻹����ٽ��ģ�",
					"��ʾ", JOptionPane.YES_OPTION);
			return;
		}

		// ִ�п�������仯����
		this.userService.updateBookQuanForCheckOut(bookList.get(index));

		// ���ӽ����¼
		this.userService.insertCheckOutRecord(bookList.get(index), reader);

		// ���鱾����һ
		this.userService.updateBorrowNumber(reader);

		// ������ʾ����ɹ�
		JOptionPane.showMessageDialog(null, "���ĳɹ����ǵð�ʱ�黹Ŷ~", "��ʾ",
				JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));

		// �������
		this.updateSearchResultPanel();
	}

	/**
	 * ����ӿڡ�������Ա��ѯ�û�����
	 * 
	 * @param name
	 *            �û�������֧��ģ����
	 * @param id
	 *            �û����
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
	 * ����ӿڡ����Ի�����ʾ������Ϣϸ��
	 * 
	 * @param index
	 *            ѡ���к�
	 */
	public void showReaderDetail(int index) {
		commonService.showReaderInfor(readerList.get(index));
	}

	/**
	 * ����ӿڡ����Ի�����ʾ�鱾��Ϣϸ��
	 * 
	 * @param index
	 *            ѡ���к�
	 */
	public void showBookDetail(int index) {
		adminService.showBookDetail(bookList.get(index));
	}

	/**
	 * ����ӿڡ����鼮�������ݿ�
	 * 
	 * @param book ���洢��
	 */
	public void layUpBookToDB(Book book) {
		book.setBookId(adminService.genBookID("b", adminService.getLastBookID()));
		adminService.layUpBook(book);
	}
}
