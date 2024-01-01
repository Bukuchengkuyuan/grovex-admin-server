项目说明：
+ 后台管理项目模板，用于快速搭建后台管理项目

项目特点：
+ 引入 Swagger 生成接口文档
+ 引入 MyBatis-plus 进行持久层操作
+ 引入 JSR 303 进行数据校验
+ 自定义国际化处理操作


代码结构：
```javascript
back
|--- src
|   |--- main         保存源代码
|   |   |--- java                     代码目录
|   |   |   |--- common               保存公共操作
|   |   |   |   |--- config           保存配置类
|   |   |   |   |--- exception        保存异常处理操作
|   |   |   |   |--- utils            保存工具类
|   |   |   |   |--- validator        保存 JSR303 校验相关操作
|   |   |   |--- controller          保存控制层代码
|   |   |   |--- entity              保存实体类代码
|   |   |   |--- handler             保存数据处理相关操作
|   |   |   |--- mapper              保存 sql 相关映射操作
|   |   |   |--- service             保存业务层代码
|
|   |   |--- resources                 资源目录
|   |   |   |--- static                用于保存项目静态文件
|   |   |   |--- application.yml       用于保存项目的配置信息
|   |   |   |--- logback-spring.xml    用于保存日志的配置信息
|
|   |--- test         保存测试代码
|   |   |--- java
|
|--- pom.xml    用于保存项目依赖信息
```

git提交规范
- 参考 [vue](https://github.com/vuejs/vue/blob/dev/.github/COMMIT_CONVENTION.md) 规范 ([Angular](https://github.com/conventional-changelog/conventional-changelog/tree/master/packages/conventional-changelog-angular))

    - `feat` 增加新功能
    - `fix` 修复问题/BUG
    - `style` 代码风格相关无影响运行结果的
    - `perf` 优化/性能提升
    - `refactor` 重构
    - `revert` 撤销修改
    - `test` 测试相关
    - `docs` 文档/注释
    - `chore` 依赖更新/脚手架配置修改等
    - `workflow` 工作流改进
    - `ci` 持续集成
    - `types` 类型定义文件更改
    - `wip` 开发中