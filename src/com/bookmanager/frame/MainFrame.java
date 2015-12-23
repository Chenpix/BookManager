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

	private static final String[] checkOutHead = { "���", "����", "����", "������",
			"�ݲ�����", "  " };
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
	private int contentPanel;// ָ����ǰ�������

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
			this.loadRightPanel(MainFrame.SEARCHPANEL);
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
		// TODO �滻���ҽ���Ϊ���ҽ������
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
		// TODO ���²��ҽ������
		this.userSearchResult = new CommonBookListPanel(
				this.userService.formatUserBookList(this.bookList),
				MainFrame.checkOutHead);
		loadRightPanel(RESULTPANEL);
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
