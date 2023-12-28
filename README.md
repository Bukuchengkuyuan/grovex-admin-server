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
- `feat`: 添加新功能。
- `fix`: 修复bug或问题。
- `docs`: 更新文档，如README文件、注释或文档页面。
- `style`: 对代码样式进行修改，如格式化代码、调整缩进等，不涉及功能修改。
- `refactor`: 对代码进行重构，既不修复bug也不添加新功能。
- `test`: 添加或修改测试代码。
- `chore`: 更新构建过程、工具配置或其他辅助工具/库。
- `revert`: 恢复上一次提交。
- `merge`: 合并分支，如合并开发分支到主分支。