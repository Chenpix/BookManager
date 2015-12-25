# BookManager
图书管理系统（包含管理员界面和用户界面）


2015/12/25 更新

	前几天代码敲得太快，主要是之前有一阵子都在忙6级，这个项目落下不少时间，于是想补回来，
终于今天稳了一稳，把这几天的代码总体的看了一下，发现有很多地方冗余，并且照我之前那个写法，有
很大的问题，于是乎优化整合了一下，记录如下：

文件变动：
	UserSearchPanel改名为CommonSearchPanel；
	UserBookListPanel改名为CommonTabelPanel；
	删除UserCheckOutRecordPanel；
	MyRender从UserBookListPanel中独立出来，加入到common包中；
	增加AdminButtonListPanel类；
	增加AdminService类；
	增加借阅成功图标success.png。

代码变动：
	更改MyRender构造函数，使之通用性提高，也提高代码复用率；
	大改CommonTabelPanel，使之能根据传入的参数不同生成不同适配的表格；
	更改MainFrame的初始化函数，现在需要根据用户类型进行初始化动作以节省资源；
	更改getBookList函数，现在空输入默认为搜索所有书籍；
	更改getCheckOutRecordSQL函数，现在会根据当前用户身份选择查询范围;
	增加管理员查询用户功能在AdminService和Sentence中相关函数。