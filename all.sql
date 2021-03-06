# 电子书表
drop table if exists `wiki_ebook`;
create table `wiki_ebook` (
  `c_oid` bigint not null comment 'c_oid',
  `c_name` varchar(50) comment '名称',
  `c_category1_id` bigint comment '分类1',
  `c_category2_id` bigint comment '分类2',
  `c_description` varchar(200) comment '描述',
  `c_cover` varchar(200) comment '封面',
  `c_doc_count` int not null default 0 comment '文档数',
  `c_view_count` int not null default 0 comment '阅读数',
  `c_vote_count` int not null default 0 comment '点赞数',
  primary key (`c_oid`)
) engine=innodb default charset=utf8mb4 comment='电子书';

insert into `wiki_ebook` (c_oid, c_name, c_description) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架');
insert into `wiki_ebook` (c_oid, c_name, c_description) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架');
insert into `wiki_ebook` (c_oid, c_name, c_description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `wiki_ebook` (c_oid, c_name, c_description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `wiki_ebook` (c_oid, c_name, c_description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');

# 分类
drop table if exists `wiki_category`;
create table `wiki_category` (
  `c_oid` bigint not null comment 'c_oid',
  `c_parent` bigint not null default 0 comment '父id',
  `c_name` varchar(50) not null comment '名称',
  `c_sort` int comment '顺序',
  primary key (`c_oid`)
) engine=innodb default charset=utf8mb4 comment='分类';

insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (100, 000, '前端开发', 100);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (101, 100, 'Vue', 101);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (102, 100, 'HTML & CSS', 102);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (200, 000, 'Java', 200);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (201, 200, '基础应用', 201);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (202, 200, '框架应用', 202);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (300, 000, 'Python', 300);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (301, 300, '基础应用', 301);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (302, 300, '进阶方向应用', 302);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (400, 000, '数据库', 400);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (401, 400, 'MySQL', 401);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (500, 000, '其它', 500);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (501, 500, '服务器', 501);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (502, 500, '开发工具', 502);
insert into `wiki_category` (c_oid, c_parent, c_name, c_sort) values (503, 500, '热门服务端语言', 503);

-- 文档表
drop table if exists `wiki_doc`;
create table `wiki_doc` (
  `c_oid` bigint not null comment 'c_oid',
  `c_ebook_id` bigint not null default 0 comment '电子书id',
  `c_parent` bigint not null default 0 comment '父id',
  `c_name` varchar(50) not null comment '名称',
  `c_sort` int comment '顺序',
  `c_view_count` int default 0 comment '阅读数',
  `c_vote_count` int default 0 comment '点赞数',
  primary key (`c_oid`)
) engine=innodb default charset=utf8mb4 comment='文档';

insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (1, 1, 0, '文档1', 1, 0, 0);
insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (2, 1, 1, '文档1.1', 1, 0, 0);
insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (3, 1, 0, '文档2', 2, 0, 0);
insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (4, 1, 3, '文档2.1', 1, 0, 0);
insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (5, 1, 3, '文档2.2', 2, 0, 0);
insert into `wiki_doc` (c_oid, c_ebook_id, c_parent, c_name, c_sort, c_view_count, c_vote_count) values (6, 1, 5, '文档2.2.1', 1, 0, 0);

-- 文档内容
drop table if exists `wiki_content`;
create table `wiki_content` (
  `c_oid` bigint not null comment '文档id',
  `c_content` mediumtext not null comment '内容',
  primary key (`c_oid`)
) engine=innodb default charset=utf8mb4 comment='文档内容';

-- 用户表
drop table if exists `wiki_user`;
create table `wiki_user` (
  `c_oid` bigint not null comment 'ID',
  `c_login_name` varchar(50) not null comment '登陆名',
  `c_name` varchar(50) comment '昵称',
  `c_password` char(32) not null comment '密码',
  primary key (`c_oid`),
  unique key `login_name_unique` (`c_login_name`)
) engine=innodb default charset=utf8mb4 comment='用户';

insert into `wiki_user` (c_oid, `c_login_name`, `c_name`, `c_password`) values (1, 'test', '测试', 'e70e2222a9d67c4f2eae107533359aa4');

-- 电子书快照表
drop table if exists `wiki_ebook_snapshot`;
create table `wiki_ebook_snapshot` (
  `c_oid` bigint auto_increment not null comment 'c_oid',
  `c_ebook_id` bigint not null default 0 comment '电子书id',
  `c_date` date not null comment '快照日期',
  `c_view_count` int not null default 0 comment '阅读数',
  `c_vote_count` int not null default 0 comment '点赞数',
  `c_view_increase` int not null default 0 comment '阅读增长',
  `c_vote_increase` int not null default 0 comment '点赞增长',
  primary key (`c_oid`),
  unique key `ebook_id_date_unique` (`c_ebook_id`, `c_date`)
) engine=innodb default charset=utf8mb4 comment='电子书快照表';
