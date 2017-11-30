## 通知

### 通知信息

#### 增

```
POST /notice/add

-> {title, content, type, departmentIds、userIds、comment}

<- {code：0/1，message：“”，data：""}
```

- title：通知标题
- content：通知内容
- type：通知类型
- departmentIds：部门id，样式：1,2,3 
- userIds：被通知人id ，样式：31,32,33
- comment：评论 (0：不允许评论，1：允许评论)默认1
- code：操作是否成功（0：失败，1：成功）
- message：返回信息
- data：返回数据
> 注：`departmentIds` 和 `userIds` 必须有一个

#### 删

```
POST /notice/del

-> {id}

<- {code：0/1，message：“”，data：""}
```
- id：通知编号
- code：操作是否成功（0：失败，1：成功）
- message：返回信息
- data：返回数据

#### 撤回/发布

```
POST /notice/setRepeal

-> {id}

<- {code：0/1，message：“”，data：""}
```
- id：通知id

#### 修改通知已读

```
POST /notice/setStatus

-> {nid}

<- {code：0/1，message：“”，data：""}
```

- nid：通知id

#### 查询通知列表

```
GET /notice/getNotices
-> {~~page~~, ~~pageSize~~, ~~title~~, ~~start_time~~,~~end_time~~, ~~type~~, ~~code~~,}

<- {code：0/1，message：“”，data： {total, page, pageSize, data:[{id, title, content, type_name, create_time}]}}
```

- page: 分页页码，注意：数值从 `1` 开始，默认为 `1`
- pageSize: 分页页宽，默认为 `10`
- - title：通知标题
- start_time：开始时间
- end_time：结束时间
- type：通知类型
- code：为类型 mine查询我创建的、me查询与我相关、为null查询与我相关
- total: 页数
- id：通知id
- content：通知内容
- type_name：类型名称

#### 查询被通知人列表

```
GET /notice/getNoticePeoples
-> {~~page~~, ~~pageSize~~, nid}

<- {code：0/1，message：“”，data：{total, page, size, data:[{id, name, status}]}}
```
- page： 分页页码，注意：数值从 `1` 开始，默认为 `1`
- pageSize：分页页宽，默认为 `10`
- nid：通知id
- total： 页数
- id：被通知人id
- name：被通知人名称
- status：是否已读（0：未读，1：已读）

#### 根据通知id获取通知的基本信息

```
GET /notice/getNotice
-> {id}

<- {code：0/1，message：“”，data：{id, title, content, create_people, create_time, type_name}}
```
- id：通知编号
- title：通知标题
- content：通知内容
- create_people：创建人名称
- create_time：创建时间
- type_name：通知类型名称

### 通知类型

#### 增

```
POST /notice_type/add

-> {name}

<- {code：0/1，message：“”，data：""}
```
- name：类型名称

#### 删

```
POST /notice_type/del

-> {id}

<- {code：0/1，message：“”，data：""}
```

- id：类型编号

#### 改

```
POST /notice_type/set

-> {id,name}

<- {code：0/1，message：“”，data：""}
```
- id：类型编号
- name：类型名称

#### 查(列表分页)

```
GET /notice_type/getNoticeTypes

-> {~~page~~, ~~pageSize~~}

<- {code：0/1，message：“”，data： {total, page, pageSize, data:[{id, name}]}}
```

- page: 分页页码，注意：数值从 `1` 开始，默认为 `1`
- pageSize: 分页页宽，默认为 `10`
- total: 页数
- id：类型id
- name：类型名称

#### 查询评论列表（不分页）
```
GET /notice_type/getNoticeTypesNotPage

-> {}

<- {code：0/1，message：“”，data： {total, page, pageSize, data:[{id, name}]}}
```
- 输出举例
```
{
    "code": 1,
    "data": [
        {
            "create_id": 1,
            "create_time": "2017-11-27-16-23-56",
            "name": "系统名称",
            "id": 2,
            "remove": 1
        }
    ],
    "message": "查询成功"
}
```

#### 根据类型id查询类型信息

```
GET /notice_type/getNoticeType

-> {id}

<- {code：0/1，message：“”，data： {id, name}}
```
- id：类型id
- name：类型名称

### 评论

#### 评论列表

```
GET /comment/getComments

-> {nid，~~page~~,~~pageSize~~}

<- {code：0/1，message：“”，data：{}}
```

- nid：通知id
- content：评论内容

- 输出举例

···
{
    "code": 1,
    "data": {
        "totalRow": 1,
        "pageNumber": 1,
        "lastPage": true,
        "firstPage": true,
        "totalPage": 1,
        "pageSize": 10,
        "list": [
            {
                "uid": 33,
                "create_time": "2017-11-27-17-12-34",
                "nid": 4,
                "id": 1,
                "content": "哈",
                "remove": 1
            }
        ]
    },
    "message": "查询成功"
}
···

#### 添加评论

```
POST /comment/add

-> {nid, content}

<- {code：0/1，message：“”，data：""}
```

- nid：通知id
- content：评论内容


#### 修改评论

```
POST /comment/setComment

-> {id,content}

<- {code：0/1，message：""，data：""}
```

- id：评论id
- content：评论内容

#### 删除评论

```
POST /comment/del

-> {id}

<- {code：0/1，message：“”，data：""}
```

- id：评论id






