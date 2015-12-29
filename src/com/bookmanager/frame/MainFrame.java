package com.bookmanager.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		
		initMenuBar();
		
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

	private void initMenuBar() {
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		JMenu smenu = new JMenu("ϵͳ");
		JMenu imenu = new JMenu("���");
		mb.add(smenu);
		mb.add(imenu);
		JMenuItem logout = new JMenuItem("�ǳ�");
		JMenuItem info = new JMenuItem("�����Ϣ");
		smenu.add(logout);
		imenu.add(info);
		logout.setActionCommand("LOGOUT");
		logout.addActionListener(this);
		info.setActionCommand("ABOUT");
		info.addActionListener(this);
		if(!reader.getLevel().equals("ADMIN")) {
			JMenuItem changePassword = new JMenuItem("�޸�����");
			smenu.add(changePassword);
			changePassword.setActionCommand("CPASS");
			changePassword.addActionListener(this);
		}
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
		this.adminButtonList.getNoteLossButton().setActionCommand("LOSS");
		this.adminButtonList.getNoteLossButton().addActionListener(this);
	}

	private void initCommonButtonListener() {
		this.commonSearch.getSearchButton().setActionCommand("SEARCH");
		this.commonSearch.getSearchButton().addActionListener(this);
	}

	private void initFrame() {
		this.setSize(800, 520);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ͼ�����ϵͳ");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			this.recordList.clear();
			if (!genCheckOutRecordPanel()) {
				// ���ļ�¼�б�Ϊ��
				JOptionPane.showMessageDialog(this, "��ѯʧ�ܣ�", "����",
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
				JOptionPane.showMessageDialog(this, "��ʱû������δ���ĵ�ͼ�飡", "��ѯʧ��",
						JOptionPane.YES_OPTION);
			}
			break;

		case "RETURN":
			recordList.clear();
			if (!genReturnBookPanel()) {
				JOptionPane.showMessageDialog(this, "��ʱû�д����ĵ�ͼ�飡", "��ѯʧ��",
						JOptionPane.YES_OPTION);
			}
			break;
		
		case "LOGOUT":
			this.dispose();
			System.gc();
			LoginFrame lf = new LoginFrame();
			break;
			
		case "LOSS":
			noteLossReader();
			break;
		
		case "CPASS":
			changePassWord();
			break;
			
		case "ABOUT":
			showAboutInfo();
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
	 * �滻���ҽ���Ϊ���ҽ������
	 * 
	 * @return �滻�ɹ�
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
	 * ���²��ҽ������
	 */
	private void updateSearchResultPanel() {
		String[] head = commonService.getBookTableHead(reader.getLevel());
		this.searchBookResult = new CommonTablePanel(
				UserService.formatBookList(bookList, head.length), head,
				commonService.getBookTableWidth(bookButtonType),
				CommonService.HEIGHT, true, bookButtonType);
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
	 * �������ڼ�¼���
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
			JOptionPane.showMessageDialog(this, "�������ڼ�¼��������ҳ�棡", "ϵͳ��Ϣ",
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
	 * ����ͼ��黹�б�
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
			JOptionPane.showMessageDialog(this, "���޴������¼��������ҳ�棡", "ϵͳ��Ϣ",
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
	 * ��ʧ����
	 */
	private void noteLossReader() {
		String readerID = JOptionPane.showInputDialog(this, "��������Ҫ��ʧ���û�ID:\n", 
				"��ʧ����", JOptionPane.QUESTION_MESSAGE);
		int loss = -1;
		if(readerID == null) {
			return;
		}
		else if(!readerID.equals("")) {
			if( (loss = adminService.confirmLossReader(readerID)) == 0 ) {
				adminService.signLossReader(readerID);
				JOptionPane.showMessageDialog(null, "��ʧ�ɹ���", "��ʾ",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));
				return;
			}else if(loss == -1) {
				readerID = "�û������ڣ����������룡";
			}else if(loss == 1) {
				readerID = "�û��ѹ�ʧ�������ظ�����!";
			}
		}
		else {
			readerID = "�û�������Ϊ�գ����������룡";
		}
		JOptionPane.showMessageDialog(this, readerID, 
				"����", JOptionPane.ERROR_MESSAGE);
		if(loss != 1) {
			noteLossReader();
		}
	}
	
	private void changePassWord() {
		String now = JOptionPane.showInputDialog(this, "������������������:\n", 
				"�޸�����", JOptionPane.QUESTION_MESSAGE);
		if(now == null) {
			return;
		}
		else if(now.equals("")) {
			now = "���벻��Ϊ�գ�����������!";
		}
		else if ( !commonService.isLegitimateUser(new Reader(this.reader.getId(),now))) {
			now = "���벻��ȷ������������!";
		}
		else {
			String newPW1 = JOptionPane.showInputDialog(this, "����������������:\n", 
					"�޸�����", JOptionPane.QUESTION_MESSAGE);
			String newPW2 = JOptionPane.showInputDialog(this, "���ٴ�����������ȷ��:\n", 
					"�޸�����", JOptionPane.QUESTION_MESSAGE);
			
			if(newPW1 == null ) {
				return;
			}
			else if(newPW1.equals("")) {
				now = "�����벻��Ϊ�գ�";
			}
			else if(newPW2 == null) {
				return;
			}
			else if( !newPW1.equals(newPW2) ) {
				now = "�������벻һ��!";
			}
			else if( newPW1.equals(newPW2) ) {
				Reader reader = new Reader(this.reader.getId(), newPW1);
				userService.updatePassword(reader);
				JOptionPane.showMessageDialog(null, "�޸ĳɹ���", "��ʾ",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));
				return;
			}
		}
		JOptionPane.showMessageDialog(this, now, 
				"����", JOptionPane.ERROR_MESSAGE);
		changePassWord();
	}
	

	private void showAboutInfo() {
		JOptionPane.showMessageDialog(this, commonService.getAuthorInfo(), 
				"�����Ϣ", JOptionPane.INFORMATION_MESSAGE, new ImageIcon());
	}
	
	/**
	 * ����ӿڡ����û����鶯��
	 * 
	 * @param index
	 *            ѡ���к�
	 */
	public void checkOutBook(int index) {

		// ��⵱ǰ�û��Ƿ񱻹�ʧ
		if(commonService.confirmLossReader(reader.getId()) == 1) {
			JOptionPane.showMessageDialog(this, "����ʧ�ܣ������˺��ѱ���ʧ��",
					"��ʾ", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// �����������Ƿ�ﵽ����
		if (commonService.isReachBookCeiling(reader)) {
			JOptionPane.showMessageDialog(this, "����ʧ�ܣ��Ѵﵱǰ��Ա�ȼ���������������뻹����ٽ��ģ�",
					"��ʾ", JOptionPane.ERROR_MESSAGE);
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
	 * @param book
	 *            ���洢��
	 */
	public void layUpBookToDB(Book book) {
		book.setBookId(adminService.genBookID("b",
				adminService.getLastID(AdminService.LAYUP)));
		adminService.layUpBook(book);
	}

	/**
	 * ����ӿڡ�����ʧ
	 * 
	 * @param index
	 */
	public void signBookLoss(int index) {
		if (adminService.signLossRecord(recordList.get(index))) {
			JOptionPane.showMessageDialog(this, "��ʧ�ɹ���", "ϵͳ��Ϣ",
					JOptionPane.YES_OPTION, new ImageIcon("image/success.png"));
			recordList.remove(index);
			updateOverDueTable();
		} else {
			JOptionPane.showMessageDialog(this, "��ʧʧ�ܣ�", "ϵͳ��Ϣ",
					JOptionPane.YES_OPTION);
		}
	}

	/**
	 * ����ӿڡ�������
	 * 
	 * @param index
	 */
	public void returnBook(int index) {
		if (adminService.updateReturnBook(recordList.get(index))) {
			JOptionPane.showMessageDialog(this, "����ɹ���", "ϵͳ��Ϣ",
					JOptionPane.YES_OPTION, new ImageIcon("image/success.png"));
			recordList.remove(index);
			updateReturnTable();
		}
		else {
			JOptionPane.showMessageDialog(this, "����ʧ�ܣ�", "ϵͳ��Ϣ",
					JOptionPane.YES_OPTION);
		}
	}

	/**
	 * ����ӿڡ����û���Ϣ�������ݿ�
	 * 
	 * @param reader
	 *            ���洢�û�
	 */
	public void signUpReaderToDB(Reader reader) {
		reader.setId(adminService.genBookID("r",
				adminService.getLastID(AdminService.SIGNUP)));
		adminService.signUpReader(reader);
		JOptionPane.showMessageDialog(this, "ע��ɹ����ö��ߵı��Ϊ" + reader.getId() 
				+ "���뱣����Լ���ID�ţ�", "ϵͳ��Ϣ",
				JOptionPane.YES_OPTION, new ImageIcon("image/success.png"));
	}
}
