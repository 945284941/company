# 平台后台
## API


### 消息
#### 批量发送点对点普通消息 （必须）
```
POST /msg/sendBatchMsg
->{username,toAccids,type,body}

<-{code:,message:"",data:""}
```
- username：发送者username对应accid
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
	"type":1, //图片类型消息
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
	"type":2,  //语音类型消息
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
    "type":6,  //文件消息
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
    "type":100,  //第三方自定义消息
    //第三方定义的body体，json串
    "body":{
        "myKey":""
    }
}
```
