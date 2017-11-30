# 平台后台
## API

### 网易云通信用户

#### 更新并获取新token （必须）

```
POST user/refreshToken
->{accid}

<-{"code":1,message:"",data:{"token":"xxx","accid":"xx" } }
```
- accid:云id等于username
- props:json属性，第三方可选填，
- token:修改的云密码

#### 封禁网易云通信ID（必须）
```
POST /user/block
->{accid,~~needkick~~}

<-{code:0/1,message:"",data:""}
```
- needkick：是否踢掉被禁用户，true或false，默认false  （该参数别传）


#### 解禁网易云通信ID （必须）
```
POST /user/unblock
->{accid}

<-{code:0/1,message:"",data:""}
```

#### 更新用户名片 （必须）
```
POST/user/updateUinfo
->{accid,name,mobile}

<-{code:0/1,message:"",data:""}
```
- accid：云id
- name：用户名-name
- mobile：手机号

#### 设置桌面端在线时，移动端是否需要推送 （必须）
```
POST /user/setDonnop
->{accid,donnopOpen}

<-{code:0/1,message:"",data:""}
```
- accid：云id
- donnopOpen：桌面端在线时，移动端是否不推送：true:移动端不需要推送，false:移动端需要推送

### 好友管理
#### 加好友 （必须）
```
POST /friend/add
->{accid,faccid,type}

<-{code:0/1,message:"",data:""}
```
- accid：云id
- faccid：加好友接收者accid
- type=1 默认写死

#### 删除好友 （必须）
```
POST /friend/del
->{accid,faccid}

<-{code:0/1,message:"",data:""}
```
- accid：云id
- faccid：好友accid

### 消息
#### 批量发送点对点普通消息 （必须）
```
POST /msg/sendBatchMsg
->{fromAccid,toAccids,type,body}

<-{code:0/1,message:"",data:""}
```
- fromAccid：发送者accid
- toAccids：被通知人["aaa","bbb"]
- type：0 表示文本消息,1 表示图片，2 表示语音，3 表示视频，4 表示地理位置信息，6 表示文件，100 自定义消息类型
- body：消息字段json格式 参考下面的body格式

```json
{
    "type":0,//文本消息类型
    "body":{
        "msg":"哈哈哈"//消息内容
    }
}
```
```json
{
	"type":1,  //图片类型消息
    "body":{
        "name":"图片发送于2015-05-07 13:59",   //图片name
        "md5":"9894907e4ad9de4678091277509361f7",    //图片文件md5
        "url":"http://nimtest.nos.netease.com/cbc500e8-e19c-4b0f-834b-c32d4dc1075e",    //生成的url
                "ext":"jpg",    //图片后缀
        "w":6814,    //宽
        "h":2332,    //高
        "size":388245    //图片大小
    }
}
```
```json
{	
	"type":2,   //语音类型消息
    "body":{
        "dur":4551,        //语音持续时长ms
        "md5":"87b94a090dec5c58f242b7132a530a01",    //语音文件的md5值
        "url":"http://nimtest.nos.netease.com/a2583322-413d-4653-9a70-9cabdfc7f5f9",    //生成的url
        "ext":"aac",        //语音消息格式，只能是aac格式
        "size":16420        //语音文件大小
    }
}
```
```json
{
    "type":3,   //视频类型消息
    "body":{
        "dur":8003,        //视频持续时长ms
        "md5":"da2cef3e5663ee9c3547ef5d127f7e3e",    //视频文件的md5值
        "url":"http://nimtest.nos.netease.com/21f34447-e9ac-4871-91ad-d9f03af20412",    //生成的url
        "w":360,    //宽
        "h":480,    //高
        "ext":"mp4",    //视频格式
        "size":16420    //视频文件大小
    }
}
```
```json
{
    "type":4,   //地理位置类型消息
    "body":{
        "title":"中国 浙江省 杭州市 网商路 599号",    //地理位置title
        "lng":120.1908686708565,        // 经度
        "lat":30.18704515647036            // 纬度
    }
}
```
```json
{
    "type":6,   //文件消息
    "body":{
        "name":"BlizzardReg.ttf",    //文件名
        "md5":"79d62a35fa3d34c367b20c66afc2a500", //文件MD5
        "url":"http://nimtest.nos.netease.com/08c9859d-183f-4daa-9904-d6cacb51c95b", //文件URL
        "ext":"ttf",    //文件后缀类型
        "size":91680    //大小
    }
}
```
```json
{
    "type":100,   //第三方自定义消息
    //第三方定义的body体，json串
    "body":{
        "myKey":""
    }
}
```


### 群组

#### 创建群 （必须）
```
POST /team/create
->{tname,owner,members,~~announcement~~,~~intro~~,msg,magree,joinmode}

<-{code:0/1,message:"",data:"{"tid":"11001","faccid":{"accid":["a","b","c"],"msg":"team count exceed"}"}
```
- tname:群名称
- owner:群主用户帐号
- members:初始创建群是邀请的群成员["aaa","bbb"]
- announcement:群公告 最大长度1024字符  可选
- intro	:群描述，最大长度512字符 可选
- msg:邀请发送的文字 最大长度150字符
- magree:管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
- joinmode:群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。

- tid:群唯一标识
- 如果创建时邀请的成员中存在加群数量超过限制的情况，会返回faccid

#### 拉人入群（必须）
```
POST /team/addUsers
->{tid,owner,members,msg,magree}

<-{code:0/1,message:"",data:"{"faccid":{"accid":["a","b","c"],"msg":"team count exceed"}"}
```
- tid:群唯一标识
- owner:群主用户帐号
- members:邀请的成员["aaa","bbb"]
- msg:邀请发送的文字 最大长度150字符
- magree:管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群


#### 踢人出群（必须）
```
POST /team/kick
->{tid,owner,member}

<-{code:0/1,message:"",data:""}
}
```
- tid:群唯一标识
- owner:群主用户帐号
- member:被移除人的accid，用户账号

#### 解散群（必须）
```
POST /team/remove
->{tid,owner}

<-{code:0/1,message:"",data:""}
}
```
- tid:群唯一标识
- owner:群主用户帐号


#### 编辑群资料 （必须）
```
POST /team/update
->{tid,~~tname~~,owner,~~announcement~~,~~intro~~,~~joinmode~~}

<-{code:0/1,message:"",data:""}
}
```
- tid: 群唯一标识
- tname:群名称
- owner:群主用户帐号
- announcement:群公告 最大长度1024字符  可选
- intro	:群描述，最大长度512字符 可选
- joinmode:群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。


#### 修改消息提醒开关 （必须）

```
POST /team/muteTeam
->{tid,accid,ope}

<-{code:0/1,message:"",data:""}

```
- tid:群唯一标识
- accid:要操作的群成员accid
- ope:1：关闭消息提醒，2：打开消息提醒


#### 主动退群  （必须）

```
POST /team/leave
->{tid,accid}

<-{code:0/1,message:"",data:""}

```
- tid:群唯一标识
- accid:要操作的群成员accid

