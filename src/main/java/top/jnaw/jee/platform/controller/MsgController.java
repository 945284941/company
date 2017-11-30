package top.jnaw.jee.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import top.jnaw.jee.platform.model.Department;
import top.jnaw.jee.platform.model.Users;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.nim.Msg;

public class MsgController extends Controller {

  /**
   * 发送全体消息，非公告
   * - username：发送者username对应accid
   * - departmentIds：被通知人单位id aaa,bbb
   * - membersS：被通知人aaa,bbb
   * - type：0 表示文本消息,1 表示图片，2 表示语音，3 表示视频，4 表示地理位置信息，6 表示文件，100 自定义消息类型
   * - body：消息字段json格式 参考md文件的body格式
   */
  public void sendBatchMsg() {
    final String username = getPara("username", "");
    final String departmentIds = getPara("departmentIds", "");
    final String membersS = getPara("usernames", username);
    final String type = getPara("type", "0");
    final String body = getPara("body", "");

    JSONObject result = new JSONObject();

    if (StrKit.notBlank(username, membersS, type, body)) {
      //对dep下所有的自己depid操作，并且获取所有人员列表
      StringBuilder UsersString = new StringBuilder();
      //获取单位id的子级单位id
      if (StrKit.notBlank(departmentIds)) {
        String[] depids = departmentIds.split(",");
        int[] departmentIdArr = new int[depids.length];//存储前台传过来的部门id
        for (int i = 0; i < depids.length; i++) {
          departmentIdArr[i] = Integer.parseInt(depids[i]);
        }

        Map<String, String> departmentMap = Department.getDepartmentIds(departmentIdArr);

        StringBuilder depidString = new StringBuilder();
        for (String key : departmentMap.keySet()) {
          depidString.append(key + ",");
        }

        if (StrKit.notBlank(depidString.toString())) {
          String depidString2 = depidString.substring(0, depidString.length() - 1);
          //获取所有单位id内的所有人员
          List<Users> users = Department.getDeparmentPeople(depidString2);
          for (Users user : users) {
            UsersString.append(user.getUsername() + ",");
          }
        }
      }
      UsersString.append(membersS);
      String members = getStingS(UsersString.toString());
      //网易云通信操作
      result = Msg.sendBatchMsg(username, members, type, body);
    }

    if (result.getInteger("code") == 200) {
      renderJson(Https.success("发送成功", result.getString("unregister")));
    } else {
      renderJson(Https.failure("发送失败", ""));
    }
  }


  /**
   * 将规律的字符串，变数组型字符串
   */
  private String getStingS(String s) {
    String[] b = s.split(",");
    Set<String> set = new HashSet<>();
    for (int i = 0; i < b.length; i++) {
      set.add(b[i]);
    }
    String[] arrayResult = (String[]) set.toArray(new String[set.size()]);

    StringBuilder m = new StringBuilder();
    m.append("[");
    for (int a = 0; a < arrayResult.length; a++) {
      if (a < arrayResult.length - 1) {
        m.append("'" + arrayResult[a] + "',");
      } else {
        m.append("'" + arrayResult[a] + "']");
      }
    }
    return m.toString();
  }
}
