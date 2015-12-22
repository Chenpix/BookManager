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

	private static final String[] checkOutHead = { "书号", "书名", "作者", "出版社",
		"馆藏数量", "  " };
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
	private int contentPanel;//指定当前的右面板

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
		this.setTitle("图书管理系统");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if (ac.equals("SEARCH")) {
			if (!replacePanel()) {//书目列表为空
				JOptionPane.showMessageDialog(this, "没有符合条件的书目，请重新输入搜索条件！",
						"查询失败", JOptionPane.YES_OPTION);
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
	 * 借书相关动作
	 */
	private void checkOut(Book book,Reader reader) {

		// 执行库存数量变化操作
		this.commonService.updateBookQuanForCheckOut(book);

		// 增加借书记录

		// 借书本数上限减一

		// 弹窗提示借书成功
	}

	private boolean replacePanel() {
		// TODO 替换查找界面为查找结果界面
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
	 * 将bookList转换为构造BookTableModel所用的数据
	 * 
	 * @param bookList
	 *            原始数据
	 * @return 转换后的二维Object数组
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
