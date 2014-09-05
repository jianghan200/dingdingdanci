
##丁丁桌面单词
By：`Han Jiang` (`暨林瀚`)

软件开发与 2014年8月

20140820 当前版本完全使用文件操作，未使用数据库

当前实现功能：
读取原始文件，转换为标准JSON文件



###软件操作
* `空格` 显示中文释义
* `->` 显示下一个单词
* `->` 显示上一个单词
* `下` 显示下一个随机单词
* `M` 标记单词的掌握程度加一
* `9`标记单词为完全掌握
* `ESC` 退出程序并保存

对单词进行基本背诵

```
-src
	-han.ding
		-RecordManager.java 核心文件
	-han.ding.dao			数据库
	-han.ding.filer			文件读取与保存
	-han.ding.filer.texter	读取txt文件
	-han.ding.panel			界面
	-han.ding.pojo			数据模型
	-han.ding.test
	-han.ding.util
	log4j.properties		调试记录配置
	
-lib
	common-logging-1.2.jar	日志接口库
	log4j-api-2.0.jar		日志实现库
	log4j-core-2.0.jar		日志实现库
	gson-2.24.jar			gson库
	
-res
	-db——initialize			数据库初始化
	-json	   				存储json格式文件
		-allword.json		格式化后的所有单词
	-phonetic_font   		音标资源文件
	-record	 				存储背诵记录
	-word_book 				存储原始的单词文件
```
