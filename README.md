# 平台后台

## 说明

- 项目名称：`platform`
- 模块名称：`backend`
- 容器名称：

    - Tomcat: `platform_web_1`
    - Redis: `platform_cache_1`
    - MySQL: `platform_db_1`

        - 数据库：`backend`
        - 用户名：`backend`
        - 密码：`backend.docker`

- 配置文件：

    - 开发环境配置文件：`developer.properties`
    - 部署环境配置文件：`product.properties`

### 开发最佳实践

1. 使用 `intellij-java-google-style.xml` 作为 Java 编码风格，并设置 80 个字符换行
1. 在 `MainConfig.java` 使用 `developer.properties` 作为配置文件
1. 确定数据库和缓存服务器 IP 地址，填入本地操作系统的 `hosts` 文件，域名设置为 `docker`
1. 修改数据库表结构后，通过 `MainConfigTest.java` 的测试用例可自动生成 Model 实体类
1. 在 `top.jnaw.jee.platform.model` 包中编写与 Model 相关的方法，不在 Controller 中编写 SQL
1. 在 `top.jnaw.jee.platform.routes` 包中按照业务分类添加路由，路由添加对应控制器

## API
### 卡片

- 接口文档地址：http://172.16.248.4/platform/backend/src/master/API/card.md

### 通知

- 接口文档地址：http://172.16.248.4/platform/backend/src/master/API/notice.md

### 网易云信相关用户
- 接口文档地址：http://172.16.248.4/platform/backend/src/master/API/user.md

### 群组
- 接口文档地址：http://172.16.248.4/platform/backend/src/master/API/team.md

### 消息
- 接口文档地址：http://172.16.248.4/platform/backend/src/master/API/msg.md

### 部门

#### 增

```
GET /department/add

-> {name, pid}

<- {result}
```

- pid: 隶属部门编号，可通过查询接口获得，`0` 表示组织名称
- result：操作结果，**注意**：`0` 表示成功，`1` 表示失败

#### 删

```
GET /department/rm

-> {id}

<- {result}
```

#### 改

```
GET /department/set

-> {id, ~~name~~, ~~pid~~}

<- {result}
```

> 注：`name` 和 `pid` 为可选修改项

#### 查

```
GET /department/list

-> {~~page~~, ~~size~~}

<- {total, page, size, data:[{id, name, pid}]}
```

- page: 分页页码，注意：数值从 `1` 开始，默认为 `1`
- size: 分页页宽，参考配置文件 `paginate_size` 选项，默认为 `10`
- total: 页数

#### 获取当前节点的父节点

```
GET /department/top

-> {id}

<- {[{name,id,child:{name,id}}]}

```
- name: 部门名称
- id: 部门id
- child: 部门的子部门

#### 获取信息

```
GET /department/get

-> {id}
-> {type}

<- [{ id, name, children:[{id, name, ~children~}] }]
```

输入举例

```json
{
    "id": 1,
    "type": "mine"
}
```

- id: 部门编号，与 `type` 参数 **同时存在时将被忽略**
- type: 获取类型，支持范围如下

    - all: 全部，获取所有组织机构信息
    - mine: 当前用户所属部门信息，支持用户与部门多对多的关系

输出举例

```json
[
    {
        "id": 1,
        "name": "R&D",
        "children": [
            {
                "id": 2,
                "name": "team"
            }
        ]
    }
]
```

### 用户

#### 注册、新增

```
GET /user/add

-> {username, password, name, nickname}

<- {result}
```

- username: 登录名
- password：密码
- name: 姓名
- nickname: 昵称
- result：操作结果，**注意**：`0` 表示成功，`1` 表示失败

#### 删

```
GET /user/rm

-> {username}

<- {result}
```

- username: 登录名

#### 改

```
GET /user/set

-> {~~password~~, ~~name~~, ~~nickname~~}

<- {result}
```

> 注：`password`、`name`、`nickname`为可选修改项


#### 根据部门获取用户列表

```
GET /user/list

-> {~~did~~, ~~page~~, ~~size~~}

<- {total, page, size, data:[{id,username,name,nickname}]}
```

- did: 部门id
- page: 分页页码，注意：数值从 `1` 开始，默认为 `1`
- size: 分页页宽，参考配置文件 `paginate_size` 选项，默认为 `10`
- total: 页数

#### 登录

```
GET /user/login

-> {username,password}

<- {result}
```

#### 登录加获取用户信息

```
GET /user/getUserInfo

-> {username,password}

<- {id,username,name,nickname,token} / {result}
```

#### 获取用户基本信息

```
GET /user/get

-> {~~username~~}

<- {id,username,name,nickname,token}

```

#### 登出

```
GET /user/logout

-> {}

<- {result}
```
