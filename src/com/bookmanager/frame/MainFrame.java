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
	private int contentPanel;// ָ����ǰ�������

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
				this.userSearch.resetAllField();
			}
			break;
		case "LEND":
			this.loadRightPanel(UserService.SEARCHPANEL);
			break;

		case "RECORD":
			if (!genCheckOutRecordPanel()) {
				// ���ļ�¼�б�Ϊ��
				JOptionPane.showMessageDialog(this, "��ʱû�з��������Ľ��ļ�¼��", "��ѯʧ��",
						JOptionPane.YES_OPTION);
			}
			break;
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
	 * �滻���ҽ���Ϊ���ҽ������
	 * 
	 * @return �滻�ɹ�
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
	 * ���²��ҽ������
	 */
	private void updateSearchResultPanel() {
		// TODO
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList));
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
			this.userCheckOutRecord = new UserCheckOutRecordPanel(
					this.userService.formatUserCheckOutRecord(recordList));
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
		JOptionPane.showMessageDialog(this, "���ĳɹ����ǵð�ʱ�黹Ŷ~", "��ʾ",
				JOptionPane.YES_OPTION);

		// �������
		this.updateSearchResultPanel();
	}

}
