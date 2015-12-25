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
	private String BTBcommand;
	private Reader reader;
	private List<Book> bookList;

	private CommonService commonService;
	private UserService userService;
	private AdminService adminService;

	private CommonSearchPanel commonSearch;
	private UserButtonListPanel userButtonList;
	private CommonTablePanel userSearchResult;
	private CommonTablePanel userCheckOutRecord;
	private AdminButtonListPanel adminButtonList;
	private JPanel rightPanel;

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.bookList = new ArrayList<Book>();
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

	public void initContent(String level) {

		this.commonSearch = new CommonSearchPanel();
		this.rightPanel = new JPanel(new GridLayout(1, 1));
		this.rightPanel.add(commonSearch);
		this.add(this.rightPanel, BorderLayout.CENTER);

		if (level.equals("ADMIN")) {
			this.adminButtonList = new AdminButtonListPanel();
			this.add(this.adminButtonList, BorderLayout.WEST);
			initAdminButtonListener();
			initAdminService();
			BTBcommand = CommonTablePanel.CHECK;
		} else {
			this.userButtonList = new UserButtonListPanel();
			this.add(this.userButtonList, BorderLayout.WEST);
			initUserButtonListener();
			initUserService();
			BTBcommand = CommonTablePanel.INFO;
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
	}

	private void initCommonButtonListener() {
		this.commonSearch.getSearchButton().setActionCommand("SEARCH");
		this.commonSearch.getSearchButton().addActionListener(this);
	}
	
	public void initFrame() {
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
		}
		if( clickTimes++ == 3 ) {
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
			this.rightPanel.add(userSearchResult);
			break;

		case UserService.RECORDPANEL:
			this.rightPanel.add(userCheckOutRecord);

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

		this.userSearchResult = new CommonTablePanel(
				UserService.formatUserBookList(bookList),
				commonService.getBookTableHead(reader.getLevel()),
				commonService.getBookTableWidth(), CommonService.HEIGHT, true,
				BTBcommand);
		loadRightPanel(UserService.RESULTPANEL);
		return true;
	}

	/**
	 * ���²��ҽ������
	 */
	private void updateSearchResultPanel() {
		// TODO
		this.userSearchResult = new CommonTablePanel(
				UserService.formatUserBookList(bookList),
				commonService.getBookTableHead(reader.getLevel()),
				commonService.getBookTableWidth(), CommonService.HEIGHT, true,
				BTBcommand);
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
	 * �û�������ض���
	 */
	public void checkOutBook(int orderNumber) {

		// �����������Ƿ�ﵽ����
		if (this.userService.isReachBookCeiling(reader)) {
			JOptionPane.showMessageDialog(this, "����ʧ�ܣ��Ѵﵱǰ��Ա�ȼ���������������뻹����ٽ��ģ�",
					"��ʾ", JOptionPane.YES_OPTION);
			return;
		}

		// ִ�п�������仯����
		this.userService.updateBookQuanForCheckOut(bookList.get(orderNumber));

		// ���ӽ����¼
		this.userService
				.insertCheckOutRecord(bookList.get(orderNumber), reader);

		// ���鱾����һ
		this.userService.updateBorrowNumber(reader);

		// ������ʾ����ɹ�
		JOptionPane.showMessageDialog(null, "���ĳɹ����ǵð�ʱ�黹Ŷ~", "��ʾ",
				JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/success.png"));

		// �������
		this.updateSearchResultPanel();
	}

}
