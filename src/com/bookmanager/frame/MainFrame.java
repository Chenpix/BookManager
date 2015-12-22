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

public class MainFrame extends JFrame implements ActionListener {

	private static final String[] checkOutHead = { "���", "����", "����", "������",
		"�ݲ�����", "  " };
	private static final int SEARCHPANEL = 1000;
	private static final int PINFOPANEL = 1001;
	private static final int RETURNPANEL = 1002;
	private static final int LOSSPANEL = 1003;
	private static final int RECORDPANEL = 1004;
	
	private Reader reader;
	private UserSearchPanel userSearch;
	private UserButtonListPanel userButtonList;
	private CommonService commonService;
	private List<Book> bookList;
	private CommonBookListPanel userSearchResult;
	private JPanel rightPanel;
	private int contentPanel;//ָ����ǰ�������

	public MainFrame() {
	}

	public MainFrame(Reader reader) {
		this.reader = reader;
		this.contentPanel = MainFrame.SEARCHPANEL;
		this.initAll();
	}

	public void initAll() {
		this.initFrame();
		this.initContent();
		this.commonService = CommonService.getCommonService();
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
		String ac = e.getActionCommand();
		if (ac.equals("SEARCH")) {
			if (!replacePanel()) {//��Ŀ�б�Ϊ��
				JOptionPane.showMessageDialog(this, "û�з�����������Ŀ����������������������",
						"��ѯʧ��", JOptionPane.YES_OPTION);
				this.userSearch.resetAllField();
			}
		} else if (ac.equals("CHECKOUT")) {
			this.checkOut(bookList.get(0),reader);
		} else if (ac.equals("LEND")) {
			System.out.println("LEND");
			this.loadRightPanel(MainFrame.SEARCHPANEL);
		}
	}

	private void loadRightPanel(int choice) {
		this.rightPanel.removeAll();
		switch(choice) {
		
		case MainFrame.SEARCHPANEL:
			this.rightPanel.add(userSearch);
			this.bookList.clear();
			break;
			
		default:
			break;
		}
		this.rightPanel.updateUI();
	}

	/**
	 * ������ض���
	 */
	private void checkOut(Book book,Reader reader) {

		// ִ�п�������仯����
		this.commonService.updateBookQuanForCheckOut(book);

		// ���ӽ����¼

		// ���鱾�����޼�һ

		// ������ʾ����ɹ�
	}

	private boolean replacePanel() {
		// TODO �滻���ҽ���Ϊ���ҽ������
		if ((bookList = commonService.getBookList(this.userSearch
				.getBookInfor())) == null) {
			return false;
		}

		this.rightPanel.remove(this.userSearch);
		this.userSearchResult = new CommonBookListPanel(
				formatUserBookList(this.bookList), MainFrame.checkOutHead);
		this.userSearchResult.getRender().getButton()
				.setActionCommand("CHECKOUT");
		this.userSearchResult.getRender().getButton().addActionListener(this);
		this.rightPanel.add(userSearchResult);
		this.rightPanel.updateUI();
		return true;
	}

	/**
	 * ��bookListת��Ϊ����BookTableModel���õ�����
	 * 
	 * @param bookList
	 *            ԭʼ����
	 * @return ת����Ķ�άObject����
	 */
	private Object[][] formatUserBookList(List<Book> bookList) {
		Object[][] list = new Object[bookList.size()][6];
		int i = 0;
		for (Book book : bookList) {
			list[i][0] = book.getBookId();
			list[i][1] = book.getBookName();
			list[i][2] = book.getAuthor();
			list[i][3] = book.getPublishing();
			list[i++][4] = book.getQuanIn();
		}
		return list;
	}
}
