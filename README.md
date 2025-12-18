### 🍿 项目简介

[![码云Gitee](https://gitee.com/vben/vben-java/badge/star.svg?theme=blue)](https://gitee.com/vben/vben-java)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-blue.svg)]()
[![JDK-17](https://img.shields.io/badge/JDK-17-green.svg)]()
[![JDK-21](https://img.shields.io/badge/JDK-21-green.svg)]()

vben-java 是一款基于SpringBoot3+Vue3的前后端分离快速开发框架。

后端基于 [RuoYi-Vue-Plus](https://gitee.com/dromara/RuoYi-Vue-Plus) 改造，集成业界一流技术栈，针对企业痛点，具有组件化、模块化、轻耦合、高扩展等特色。
应用层可自由选择ORM框架（Mybatis Plus，JPA）

前端基于 [Vben Admin](https://github.com/vbenjs/vue-vben-admin) 改造，使用最新前端技术栈，提供丰富的组件和模板以及N种偏好设置组合方案，
应用层可自由选择UI框架（Element Plus，Ant Design Vue，Native UI）

移动端基于 [cool-unix](https://gitee.com/cool-team-official/cool-unix) 改造，参照了unibest的方式，通过pnpm集成，
除了可继续使用HBuilderX，还可以使用VS Code，Webstorm 开发运行uni-app x 应用（WEB与小程序）。注：安卓、苹果、鸿蒙端的运行还是需要借助HBuilderX。

### 🍟 相关地址

* 前端WEB 体验地址 ：[http://8.153.168.178/](http://8.153.168.178/)

* 移动端APP 体验地址 ：[http://8.153.168.178/app/](http://8.153.168.178/app/)

* 文档地址 ：[http://8.153.168.178/java-doc](http://8.153.168.178/java-doc)

* 后端API 项目地址 https://gitee.com/vben/vben-java

* 前端WEB 项目地址 https://gitee.com/vben/vben-web

* 移动端APP 项目地址 https://gitee.com/vben/vben-app

* 同功能.net后端 项目地址 https://gitee.com/vben/vben-net

* 联系方式（加微信后可找小狐狸拉进交流群）：

![输入图片说明](https://gitee.com/vben/vben-app/raw/master/docs/wx.jpg "微信联系方式")

### ⚡ 快速启动

后端

* 准备工作：1. 准备java17及以上版本，修改pom.xml对应java.version 2. 根据application-dev.yml配置文件准备一个空的数据库，默认为vben-java的mysql数据库，账号root,密码123456 3. 开启redis 默认密码为空

* 数据库初始化：启动VbenSetup，程序会自动创建数据库表与初始化表数据，生成完成后VbenSet应用会自动关闭

* 启动后台API：启动VbenAdmin，也可启动VbenJpa或VbenMybatis查看相应DEMO示例

前端

* 准备工作：准备Node.js 20.15.0以上环境，全局安装pnpm：npm install -g pnpm 

* 启动前台WEB：1. 使用pnpm install安装依赖 2. 使用pnpm dev:ele运行项目 3.访问 http://localhost:5666/ 预览

### 🍄 主要特色

- 后端项目中抽离并拆分了公共功能，以插件化与扩展包的方式组织，结构解耦且易于扩展。
- 后端项目业务模块以多基础模块与多应用方式组织，可实现多个应用共用相同基础模块，方便实现基础模块共享
- 后端项目基础业务模块采用JdbcTemplate实现，上层应用可采用mybatis-plus或者jpa，选择留给用户
- 工作流模块不依赖其他工作流引擎，全部自行实现，易于扩展，实现复杂工作流
- 统一的命名风格，数据库表主键统一用ID命名，表字段采用SAP风格（后端手册里有详细介绍）

### 🏀 技术选型

后端框架基于RuoYi-Vue-Plus，因此包含了RuoYi-Vue-Plus框架的几乎所有技术栈，
采用Mybatis Plus方式可保留原有框架所有功能，采用JPA方式可实现更强大的ORM功能。
RuoYi-Vue-Plus框架对分布式支持比较好，vben-java框架在支持分布式的同时可更方便的实现单机模式

<table>
    <thead>
    <tr>
        <th width="150" align="center">功能模块</th>
        <th width="*">RuoYi-Vue-Plus</th>
        <th width="300" align="center">vben-java</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td align="center">Web容器</td>
        <td>采用 Undertow 基于 XNIO 的高性能容器</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">权限认证</td>
        <td>采用 Sa-Token、Jwt 静态使用功能齐全 低耦合 高扩展</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">权限注解</td>
        <td>采用 Sa-Token 支持注解 登录校验、角色校验、权限校验、二级认证校验、HttpBasic校验、忽略校验角色与权限校验支持多种条件
            如 AND OR 或 权限 OR 角色 等复杂表达式
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">三方鉴权</td>
        <td>采用 JustAuth 第三方登录组件 支持微信、钉钉等数十种三方认证</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">关系数据库支持</td>
        <td>原生支持 MySQL、Oracle、PostgreSQL、SQLServer可同时使用异构切换(支持其他 mybatis-plus 支持的所有数据库
            只需要增加jdbc依赖即可使用 达梦金仓等均有成功案例)
        </td>
        <td align="center">√ 基础与核心业务模块采用JdbcTemplate实现，上层应用可采用mybatis-plus或者jpa</td>
    </tr>
    <tr>
        <td align="center">缓存数据库</td>
        <td>支持 Redis 5-7 支持大部分新功能特性 如 分布式限流、分布式队列</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">Redis客户端</td>
        <td>采用 Redisson Redis官方推荐 基于Netty的客户端工具支持Redis 90%以上的命令 底层优化规避很多不正确的用法 例如:
            keys被转换为scan支持单机、哨兵、单主集群、多主集群等模式
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">缓存注解</td>
        <td>采用 Spring-Cache 注解 对其扩展了实现支持了更多功能 例如过期时间 最大空闲时间 组最大长度等
            只需一个注解即可完成数据自动缓存
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">ORM框架</td>
        <td>采用 Mybatis-Plus 基于对象几乎不用写SQL全java操作 功能强大插件众多 例如多租户插件 分页插件 乐观锁插件等等
        </td>
        <td align="center">√ JPA方式可实现功能更全的ORM功能，比如自动生成表结构</td>
    </tr>
    <tr>
        <td align="center">SQL监控</td>
        <td>采用 p6spy 可输出完整SQL与执行时间监控</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">数据分页</td>
        <td>采用 Mybatis-Plus 分页插件 框架对其进行了扩展 对象化分页对象 支持多种方式传参 支持前端多排序 复杂排序</td>
        <td align="center">√ JdbcTemplate Mybatis JPA 三种方式都可轻松实现数据分页</td>
    </tr>
    <tr>
        <td align="center">数据权限</td>
        <td>采用 Mybatis-Plus 插件 自行分析拼接SQL 无感式过滤只需为Mapper设置好注解条件 支持多种自定义 不限于部门角色
        </td>
        <td align="center">√ 可采用此方式，另外组织架构模块增加了群组模块，可实现更灵活的数据权限控制</td>
    </tr>
    <tr>
        <td align="center">数据脱敏</td>
        <td>采用 注解 + jackson 序列化期间脱敏 支持不同模块不同的脱敏条件支持多种策略 如身份证、手机号、地址、邮箱、银行卡等
            可自行扩展
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">数据加解密</td>
        <td>采用 注解 + mybatis 拦截器 对存取数据期间自动加解密支持多种策略 如BASE64、AES、RSA、SM2、SM4等</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">接口传输加密</td>
        <td>采用 动态 AES + RSA 加密请求 body 每一次请求秘钥都不同大幅度降低可破解性</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">数据翻译</td>
        <td>采用 注解 + jackson 序列化期间动态修改数据 数据进行翻译，支持多种模式: 映射翻译 直接翻译 其他扩展条件翻译
            接口化两步即可完成自定义扩展 内置多种翻译实现
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">多数据源框架</td>
        <td>采用 dynamic-datasource 支持市面大部分数据库，通过yml配置即可动态管理异构不同种类的数据库
            也可通过前端页面添加数据源，支持spel表达式从请求头参数等条件切换数据源
        </td>
        <td align="center">√ mybatis-plus方式支持动态数据源，<br/> × jpa方式不支持动态数据源，需要单独配置引入多数据源
        </td>
    </tr>
    <tr>
        <td align="center">多数据源事务</td>
        <td>采用 dynamic-datasource 支持多数据源不同种类的数据库事务回滚</td>
        <td align="center">√ mybatis-plus方式多数据源事务，<br/> × jpa方式不支多数据源事务</td>
    </tr>
    <tr>
        <td align="center">数据库连接池</td>
        <td>采用 HikariCP Spring官方内置连接池 配置简单 以性能与稳定性闻名天下</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">数据库主键</td>
        <td>采用 雪花ID 基于时间戳的 有序增长 唯一ID 再也不用为分库分表 数据合并主键冲突重复而发愁</td>
        <td align="center">√
            支持雪花ID，为了兼容性支持，特别是需要与其他业务系统关联的基础组织架构模块，采用的是32位字符串UUID方式
        </td>
    </tr>
    <tr>
        <td align="center">WebSocket协议</td>
        <td>基于 Spring 封装的 WebSocket 协议 扩展了Token鉴权与分布式会话同步 不再只是基于单机的废物</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">SSE推送</td>
        <td>采用 Spring SSE 实现 扩展了Token鉴权与分布式会话同步</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">序列化</td>
        <td>采用 Jackson Spring官方内置序列化 靠谱!!!</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">分布式幂等</td>
        <td>参考美团GTIS防重系统简化实现</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">分布式任务调度</td>
        <td>采用 SnailJob 天生支持分布式 统一的管理中心 支持多种数据库 支持分片重试DAG任务流等</td>
        <td align="center">√
            支持SnailJob分布式方式，另外项目也集成了Quartz，对于不需要分布式的项目可以更便捷地使用定时调度
        </td>
    </tr>
    <tr>
        <td align="center">文件存储</td>
        <td>采用 Minio 分布式文件存储 天生支持多机、多硬盘、多分片、多副本存储，支持权限管理 安全可靠 文件可加密存储</td>
        <td align="center">√ 支持Minio方式，另外项目也实现了本地文件存储方式供用户选择</td>
    </tr>
    <tr>
        <td align="center">云存储</td>
        <td>采用 AWS S3 协议客户端 支持 七牛、阿里、腾讯 等一切支持S3协议的厂家</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">短信</td>
        <td>采用 sms4j 短信融合包 支持数十种短信厂家 只需在yml配置好厂家密钥即可使用 可多厂家共用</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">邮件</td>
        <td>采用 mail-api 通用协议支持大部分邮件厂商</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">接口文档</td>
        <td>采用 SpringDoc、javadoc 无注解零入侵基于java注释只需把注释写好 无需再写一大堆的文档注解了</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">校验框架</td>
        <td>采用 Validation 支持注解与工具类校验 注解支持国际化</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">Excel框架</td>
        <td>采用 Alibaba EasyExcel 基于插件化，框架对其增加了很多功能 例如 自动合并相同内容 自动排列布局 字典翻译等</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">工具类框架</td>
        <td>采用 Hutool、Lombok 上百种工具覆盖90%的使用需求 基于注解自动生成 get set 等简化框架大量代码</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">监控框架</td>
        <td>采用 SpringBoot-Admin 基于SpringBoot官方 actuator 探针机制，实时监控服务状态
            框架还为其扩展了在线日志查看监控
        </td>
        <td align="center">√ 支持SpringBoot-Admin方式，另外项目也集成了oshi，可方便的实现单机服务器监控</td>
    </tr>
    <tr>
        <td align="center">链路追踪</td>
        <td>采用 Apache SkyWalking 还在为请求不知道去哪了
            到哪出了问题而烦恼吗，用了它即可实时查看请求经过的每一处每一个节点
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">代码生成器</td>
        <td>只需设计好表结构 一键生成所有crud代码与页面，降低80%的开发量 把精力都投入到业务设计上，框架为其适配MP、SpringDoc规范化代码
            同时支持动态多数据源代码生成
        </td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">部署方式</td>
        <td>支持 Docker 编排 一键搭建所有环境 让开发人员从此不再为搭建环境而烦恼</td>
        <td align="center">√</td>
    </tr>
    </tbody>
</table>


### 🍖 内置功能

后端框架基于RuoYi-Vue-Plus，表结构重新定义了，但是业务功能基本都有保留，比较遗憾的是放弃了对多租户的支持。

<table>
    <thead>
    <tr>
        <th width="150" align="center">业务模块</th>
        <th width="*">RuoYi-Vue-Plus</th>
        <th width="300" align="center">vben-java</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td align="center">租户管理</td>
        <td>系统内租户的管理 如:租户套餐、过期时间、用户数量、企业信息等</td>
        <td align="center">×</td>
    </tr>
    <tr>
        <td align="center">租户套餐管理</td>
        <td>系统内租户所能使用的套餐管理 如:套餐内所包含的菜单等</td>
        <td align="center">×</td>
    </tr>
    <tr>
        <td align="center">客户端管理</td>
        <td>系统内对接的所有客户端管理 如: pc端、小程序端等，支持动态授权登录方式 如: 短信登录、密码登录等 支持动态控制token时效</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">用户管理</td>
        <td>用户的管理配置 如:新增用户、分配用户所属部门、角色、岗位等</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">部门管理</td>
        <td>配置系统组织机构（公司、部门、小组） 树结构展现支持数据权限</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">岗位管理</td>
        <td>配置系统用户所属担任职务</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">菜单管理</td>
        <td>配置系统菜单、操作权限、按钮权限标识等</td>
        <td align="center">√ 菜单管理分离为菜单管理（对应原目录与菜单）与接口管理（对应原按钮）</td>
    </tr>
    <tr>
        <td align="center">角色管理</td>
        <td>角色菜单权限分配、设置角色按机构进行数据范围权限划分</td>
        <td align="center">√ 角色成员支持部门、用户、岗位、群组</td>
    </tr>
    <tr>
        <td align="center">字典管理</td>
        <td>对系统中经常使用的一些较为固定的数据进行维护</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">参数管理</td>
        <td>对系统动态配置常用参数</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">通知公告</td>
        <td>系统通知公告信息发布维护</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">操作日志</td>
        <td>系统正常操作日志记录和查询 系统异常信息日志记录和查询</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">登录日志</td>
        <td>系统登录日志记录查询包含登录异常</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">文件管理</td>
        <td>系统文件展示、上传、下载、删除等管理</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">文件配置管理</td>
        <td>系统文件上传、下载所需要的配置信息动态添加、修改、删除等管理</td>
        <td align="center">√ 在支持分布式的同时可更方便的实现单机模式</td>
    </tr>
    <tr>
        <td align="center">在线用户管理</td>
        <td>已登录系统的在线用户信息监控与强制踢出操作</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">定时任务</td>
        <td>运行报表、任务管理(添加、修改、删除)、日志管理、执行器管理等</td>
        <td align="center">√ 在支持分布式的同时可更方便的实现单机模式</td>
    </tr>
    <tr>
        <td align="center">代码生成</td>
        <td>多数据源前后端代码的生成（java、html、xml、sql）支持CRUD下载</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">系统接口</td>
        <td>根据业务代码自动生成相关的api接口文档</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">服务监控</td>
        <td>监视集群系统CPU、内存、磁盘、堆栈、在线日志、Spring相关配置等</td>
        <td align="center">√ 在支持分布式的同时可更方便的实现单机模式</td>
    </tr>
    <tr>
        <td align="center">缓存监控</td>
        <td>对系统的缓存信息查询，命令统计等</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">在线构建器</td>
        <td>拖动表单元素生成相应的HTML代码</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">使用案例</td>
        <td>系统的一些功能案例</td>
        <td align="center">√</td>
    </tr>
    <tr>
        <td align="center">工作流</td>
        <td>采用Warm-Flow工作流</td>
        <td align="center">√ 前端流程图采用BPMN.js，表单采用FromCreate（FormDesigner），后端自行实现流程引擎</td>
    </tr>
    </tbody>
</table>

### 💐 特别鸣谢
- 👉 原框架作者：[zsvg](https://gitee.com/zsvg)
- 👉 Vben-Admin：[https://github.com/vbenjs/vue-vben-admin](https://github.com/vbenjs/vue-vben-admin)
- 👉 RuoYi-Vue-Plus：[https://gitee.com/dromara/RuoYi-Vue-Plus](https://gitee.com/dromara/RuoYi-Vue-Plus)
- 👉 FormCreate：[https://www.form-create.com/](https://www.form-create.com/)

