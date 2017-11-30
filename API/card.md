## API

### 卡片


#### 获取卡片列表

```
GET /card/getCards

->  {~~type~~,~~title~~,~~strtTime~~,~~endTime~~,~~create_id~~,~~page~~,~~pageSize~~}

<- {code,data:[ totalRow,pageNumber,firstPage,lastPage,totalPage,pageSzie,
list:{id,title,context,type_name,create_name,create_time]} 

```
- type                        为类型  mine查询我所创建的  me 与我相关的卡片信息 all 全部
- title                         为卡片的标题
- startTime               开始时间
- endTime                  结束时间
- create_id               要查询人的id
- page                       页码
- pageSize                页显示量的大小
- code                       1 查询成功 0 查询失败
- type与 create_id 必须填一个

- 输出举例

```json
{
    "code": 1,
    "message": "查询成功",
    "data": {
        "totalRow": 3,
        "pageNumber": 1,
        "firstPage": true,
        "lastPage": true,
        "totalPage": 1,
        "pageSize": 10,
        "list": [
            {
                "id": 1,
                "title": "开会",
                "context": "开会的内容",
                "create_name": " 鹏,",
                "create_time": "2017-02-03",
                "type_name": "类型1"
            },
            {
                "id": 1,
                "title": "开会",
                "context": "开会的内容",
                "create_name": " 鹏,",
                "create_time": "2017-02-03",
                "type_name": "类型1"
            }
        ]
    }
}

```

#### 获取卡片的详细信息


```
GET /card/getCard

->  {id}

<- {code,data:{id,title,context,create_name,create_time]} 

```
- id                         卡片的id
- code                    返回的结果
- id                         卡片的id
- title                      卡片的标题
- context                卡片的内容
- create_name       创建者的姓名
- create_time         创建时间
- code                    1 查询成功 0 查询失败

- 输出举例

```json
{
    "code": 1,
    "message": "查询成功",
    "data": {
                "id": 1,
                "title": "开会",
                "context": "开会的内容",
                "create_name": " 鹏,",
                "create_time": "2017-02-03",
               }
}

```


#### 查看卡片已读未读信息

```
GET /card/getCardPeoples

->  {id,page,pageSzie}

<-  {code,data:[{id,name,uid,cid,status},]}


```

- id                卡片的id
- name          人的名称
- uid              人的id
- cid              卡片的id
- status         状态
- code           1 查询成功 0 查询失败

- 输出举例

```json

{
    "code": 1,
    "message": "查询成功",
    "data": {
        "totalRow": 3,
        "pageNumber": 1,
        "firstPage": true,
        "lastPage": true,
        "totalPage": 1,
        "pageSize": 10,
        "list" : [{
            "id": 1,
            "name": "鹏",
            "uid": 1,
            "cid": 2,
            "status": 0
        },
        {
            "id": 1,
            "name": "鹏",
            "uid": 1,
            "cid": 2,
            "status": 0
        }]
    }
}

``` 



####  增加

```
POST /card/add

->  {title,create_id，context，type_id,important,~~lookers~~，~~groupIds~~}

<-  {code,message,data}

```
- title :标题
- create_id :发送者id
- context :内容
- type_id:类型id
- important ：0 or  1    0正常 1不重要
- lookers ：被发送者得id  例如 1,2
- groupIds ： 群组的id    例如 1
- code: 1增加成功 0增加失败


- 输出举例

```json

{
    "code": 1,
    "message": "添加成功",
    "data":“”
}

``` 



#### 删除 

```
POST /card/del

->  {id}

<-  {code,message,data}

```

- id 为卡片id的数组
- code 1为  删除成功 0 为 删除失败

- 输出举例

```json

{
    "code": 1,
    "message": "删除成功",
    "data":“”
}

``` 
-            

#### 修改

```
POST /card/set

->  {id,title,context,looker,important,type_id}

<-  {code,message,data}

```

-  id ：       卡片的id
-  title :       卡片的标题
-  context : 卡片的内容 
-  type_id   卡片的类型
-  code       1 已查看 0 修改失败

- 输出举例

```json

{
    "code": 1,
    "message": "修改成功",
    "data":“”
}

``` 


#### 改变状态 

```
POST /card/setStatus

->  {id,uid}

<-  {code,message,data}

```
-  id            当前卡片的id
-  uid          被发送者的id
-  code       1 修改成功 0 修改失败


- 输出举例

```json

{
    "code": 1,
    "message": "修改成功",
    "data":“”
}

``` 



### 卡片类型


####  修改

```
POST /cardtype/set

->  {id,name}

<-  {code,message,data}

```

-  id :     卡片类型的id
-  name :  卡片的名称
-  code  1 修改成功 0 修改失败

- 输出举例

```json

{
    "code": 1,
    "message": "修改成功",
    "data":“”
}

``` 



#### 删除

```
POST /cardtype/del

->  {id}

<-  {code,message,data}

``` 

-   id :     卡片类型的id
-   code   1 删除成功 0 删除失败


- 输出举例

```json

{
    "code": 1,
    "message": "删除成功",
    "data":“”
}

``` 

#### 查看

```
GET /cardtype/getCardTypes

->  {~~name~~,page,pageSize，type}
 
<-  {code,data:[{id,name}]}

``` 
- name:   卡片类型的名称 ，可选
- id：      卡片类型的Id
- code :   1 查询成功  0查询失败
- type : 默认为不分页模式  参数值为 fenye  是 分页模式

- 输出举例

```json
{
    "code": 1,
    "message": "查询成功",
            "totalRow": 3,
        "pageNumber": 1,
        "firstPage": true,
        "lastPage": true,
        "totalPage": 1,
        "pageSize": 10,
    "list": [
        {
            "id": 1,
            "name": "类型1"
        },
        {
            "id": 1,
            "name": "类型1"
        }
    ]
}

``` 



####  增加

```
POST /cardtype/add

->  {name,create_id}

<-  {code,message,data}

``` 
- name: 卡片的名称
- code:   1增加成功 0增加失败


- 输出举例

```json

{
    "code": 1,
    "message": "增加成功",
    "data":“”
}
