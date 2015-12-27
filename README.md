# BookManager
图书管理系统（包含管理员界面和用户界面）


2015/12/25 更新

	前几天代码敲得太快，主要是之前有一阵子都在忙6级，这个项目落下不少时间，于是想补回来，
终于今天稳了一稳，把这几天的代码总体的看了一下，发现有很多地方冗余，并且照我之前那个写法，有
很大的问题，于是乎优化整合了一下，记录如下：

文件变动：
	UserSearchPanel改名为CommonSearchPanel；
	UserBookListPanel改名为CommonTabelPanel；
	删除UserCheckOutRecordPanel文件；
	MyRender从UserBookListPanel中独立出来，加入到common包中；
	增加AdminButtonListPanel文件；
	增加AdminService文件；
	增加借阅成功图标success.png。

代码、功能变动：
	更改MyRender构造函数，使之通用性提高，也提高代码复用率；
	大改CommonTabelPanel，使之能根据传入的参数不同生成不同适配的表格；
	更改MainFrame的初始化函数，现在需要根据用户类型进行初始化动作以节省资源；
	更改getBookList函数，现在空输入默认为搜索所有书籍；
	更改getCheckOutRecordSQL函数，现在会根据当前用户身份选择查询范围;
	增加管理员查询用户功能在AdminService和Sentence中相关函数。


2015/12/26 更新
	今天把查询读者和书籍的详细信息功能都完成了，也仔细的测试了一遍，bug都梳理了一遍。
这两个功能写得很快，因为列表什么的都不用自己去重写，昨天写的CommonTabelPanel类让我受益匪浅。
再一个就是更新了图书入库功能，这个功能实现之后办理借书证的功能应该很快就能实现了，因为在写的
过程中我还是比较注意代码的复用率的问题的，尽量实现模块化，多造轮子。整体记录如下：

文件变动：
	增加AdminSearchUsesrPanel文件；
	增加AdminLayUpBookPanel文件；

代码、功能变动：
	更改UserService，提供图书入库和显示详细信息的数据库读写操作相关函数；
	更改MainFrame,提供了相关的接口函数；
	完成并测试了查看书本详细信息功能；
	完成并测试了查看读者详细信息功能；
	完成并测试了图书入库功能。

今天脖子好酸，以上。


2015/12/27 更新
	今天写的太累了，一直没停，提交时间都过了...现在就剩三个功能了，一个是登出，一个是
修改密码，一个是管理员方面的挂失用户。赶紧提交睡觉了，脖子快断了。