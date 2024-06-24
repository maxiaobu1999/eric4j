创建account表
```$xslt
DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
    `userId` int(11) NOT NULL auto_increment COMMENT '用户ID',
	`phoneNum` TEXT default NULL COMMENT '手机号码',
	`userName` TEXT default NULL COMMENT '用户名',
	`password` TEXT default NULL COMMENT '密码',
	`email` TEXT default NULL COMMENT '邮箱',
	`avatar` TEXT default NULL COMMENT '头像',
	`gender` TEXT default NULL COMMENT '性别',
	`nickname` TEXT default NULL COMMENT '昵称',
	`identifier` TEXT default NULL COMMENT '设备标示id',
  PRIMARY KEY  (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```
创建token表
```$xslt
DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
    `id` int(11) NOT NULL auto_increment COMMENT '编号',
	`userId` TEXT default NULL COMMENT '用户ID',
	`token` TEXT default NULL COMMENT 'token',
	`loginTime` TEXT default NULL COMMENT '登录时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

创建好看表
```$xslt
DROP TABLE IF EXISTS `haokan`;

CREATE TABLE `haokan` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
	`vid` TEXT default NULL COMMENT '视频id',
	`title` TEXT default NULL COMMENT '视频title',
	`image` TEXT default NULL COMMENT '视频封面',
	`videoUrl` TEXT default NULL COMMENT '小视频videourl',
	`duration` TEXT default NULL COMMENT '视频时长',
	`videoSize` TEXT default NULL COMMENT '小视频大小',
	`playcntText` TEXT default NULL COMMENT '播放次数',
	`likeNum` TEXT default NULL COMMENT '小视频点赞',
	`commentNum` TEXT default NULL COMMENT '视频评论数量',
	`authorIcon` TEXT default NULL COMMENT '作者头像地址',
	`authorName` TEXT default NULL COMMENT '作者名称',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

创建feed tab表
```$xslt
DROP TABLE IF EXISTS `feedtab`;

CREATE TABLE `feedtab` (
    `id` int(11) NOT NULL auto_increment COMMENT '主键',
	`name` TEXT default NULL COMMENT 'tab名',
	`canDelete` int(11) default 0 COMMENT '0不可以删除1可以删除',
	`added` int(11) default 1 COMMENT '0未添加1已添加',
	`normalColor` TEXT default NULL COMMENT '频道非选中颜色',
	`selectColor` TEXT default NULL COMMENT '频道选中颜色',
	`indicatorColor` TEXT default NULL COMMENT '频道指示器颜色',
	`newTip` int(11) default 0 COMMENT '是否飘新0否1是',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

