# 平台后台
## API

### 网易云通信用户

#### 更新并获取新token （必须）

```
POST user/refreshToken
->{username}

<-{"code":1,message:"",data:{"token":"xxx","accid":"xx" } }
```
- username:云id等于accid
- token:修改的云密码

#### 封禁网易云通信ID（必须）
```
POST /user/block
->{username,~~needkick~~}

<-{code:0/1,message:"",data:""}
```
- username:云id等于accid
- needkick：是否踢掉被禁用户，true或false，默认false 先别使用


#### 解禁网易云通信ID （必须）
```
POST /user/unblock
->{username}

<-{code:0/1,message:"",data:""}
```
- username:云id等于accid

#### 更新用户名片 （必须）
```
POST/user/set
->{username,~~name~~,~~password~~,~~nickname~~,~~mobile~~}

<-{code:0/1,message:"",data:""}
```
- username：云id
- name：用户名-name
- mobile：手机号

#### 查询群组用户列表  （必须）

```
POST /user/getTeamUsers
->{tid,~~all~~,~~page~~,~~size~~}

<-{code:0/1,message:"",data:"list<user>"}

```
- tid:群唯一标识 对应表中no
- all:默认-1，分页查询，其他查询全部不分页

