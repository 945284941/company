# 平台后台
## API


### 群组

#### 创建群 （必须）
```
POST /team/add
->{tname,username,departmentIds,usernames,msg,magree,joinmode}

<-{code:0/1,message:"",data:["a","b","c"]}
```
- tname:群名称
- username:群主用户帐号
- departmentIds:初始创建群是邀请的群成员的部门id   1,2,3
- usernames:初始创建群是邀请的群成员的用户名 aaa,bbb
- msg:邀请发送的文字 最大长度150字符
- magree:管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
- joinmode:群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。

- tid:群唯一标识 对应表中no
- 如果创建时邀请的成员中存在加群数量超过限制的情况，会返回无法加群的人员

#### 拉人入群（必须）
```
POST /team/addUsers
->{tid,username,departmentIds,usernames,msg,magree}

<-{code:0/1,message:"",data:["a","b","c"]}
```
- tid:群唯一标识  对应表中no
- username:群主用户帐号
- departmentIds:初始创建群是邀请的群成员的部门id   1,2,3
- usernames:初始创建群是邀请的群成员的用户名 aaa,bbb
- msg:邀请发送的文字 最大长度150字符
- magree:管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群


#### 踢人出群（必须）
```
POST /team/delUser
->{tid,username,userb}

<-{code:0/1,message:"",data:""}
}
```
- tid:群唯一标识 对应no
- username:群主用户帐号
- userb:被移除人的accid，用户账号

#### 解散群（必须）
```
POST /team/del
->{tid,username}

<-{code:0/1,message:"",data:""}
}
```
- tid:群唯一标识对应 tno
- username:群主用户帐号


#### 编辑群资料 （必须）
```
POST /team/set
->{tid,~~tname~~,username,~~announcement~~,~~intro~~,~~joinmode~~}

<-{code:0/1,message:"",data:""}
}
```
- tid: 群唯一标识 no
- tname:群名称
- username:群主用户帐号
- announcement:群公告 最大长度1024字符  可选
- intro	:群描述，最大长度512字符 可选
- joinmode:群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。


#### 修改消息提醒开关 （必须）

```
POST /team/muteTeam
->{tid,username,ope}

<-{code:0/1,message:"",data:""}

```
- tid:群唯一标识 对应no
- username:要操作的群成员accid
- ope:1：关闭消息提醒，2：打开消息提醒


#### 主动退群  （必须）

```
POST /team/leave
->{tid}

<-{code:0/1,message:"",data:""}

```
- tid:群唯一标识 对应表中no

#### 获取我创建的群，或者我加入的群列表  （必须）

```
POST /team/getUserTeam
->{username,~~type~~}

<-{code:0/1,message:"",data:"json"}

```
- username:我的用户名
- type:是否查询自己的是自己创建的群   默认0不查，其他查


#### 查询单个群组信息

```
POST /team/getTeamInfo
->{tid}

<-{code:0/1,message:"",data:"json"}

```
- tid:群组no


#### 查询群组列表 

```
POST /team/list
->{~~all~~,~~page~~,~~size~~}

<-{code:0/1,message:"",data:"json"}

```
- all:不传默认为-1 分页查询列表，1或者其他，直接返回所有列表
