# SocketChat
🌍
基于`Socket` `Swing` `mysql`制作的聊天室

在[CSDN上的这位大佬]的代码基础上进行代码分离，修改部分逻辑添加功能。

只完成大体的逻辑和功能，细节（如各部分结果的判空）都仍要修改。

mysql数据库存储中文问题：[示例]

# 代码分离
- 使用MVC思想将GUI和逻辑代码进行分离（只进行简单的分离，可以利用设计模式再进行分离，学艺不精暂时做不了）
# 各模块功能
### V层：
- dispalyUI 各界面的GUI代码（只将client和server的GUI提取出来）
### C层：
- Controller 主界面入口，给界面按钮注册监听器；创建用户及服务器线程，同时控制发送和接受信息；
- operation 多线程操作，messageThread为客户端，其余为服务端。
### M层：
- DAO：DAO创建数据库连接。
- DBUtils：增删改查基本操作。
- vo：对应数据库表的字段，可以用来进行增删改查操作 ，也可以将结果机的数据封装为对应对象。

## 主要功能
- 基于TCP协议，有服务器客户端，多个客户端在线聊天。
- 当客户端登录或离线时，向其他客户端发送提示。
- 服务器可查看所有在线用户，客户端可查看其他在线用户。
- 客户端之前可以进行私聊。
- 客户端可以发送离线消息，上线后可查看。

## 实现思路
- 无论用户间还是用户与服务器间，都要通过<font color=green>服务器</font>转发消息或者通知操作。
- 私聊：由客户端向服务器发送私聊内容及对象，<font color=green>服务器</font>进行转发（没有展示在服务器上）给对应socket的用户。
- 登录验证：由客户端向服务器发送连接请求，在<font color=green>服务器</font>管理在线人员，名字重复（本地测试Ip相同）的就反馈给客户端失败。


## TODO
- 优化关闭客户端或服务器时异常问题
- 将所有聊天记录全都保存在数据库中，通过数据库操作来操作历史记录。

>- ~~离线消息。~~
>- ~~加入mysql数据库保存离线消息（目前仅保存离线消息）。~~

>- ~~登录验证。~~
>- ~~登录验证逻辑问题，验证重复登录后的客户端无法重新使用~~

>- ~~客户端之间的私聊信息字体变色。~~
>- ~~将JTextArea替换成JTextPane对字体颜色进行管理。~~

[CSDN上的这位大佬]:https://blog.csdn.net/Haomua/article/details/103596402?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
[示例]:https://blog.csdn.net/qq_36237569/article/details/82285599