package top.jnaw.jee.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.Department;
import top.jnaw.jee.platform.model.Team;
import top.jnaw.jee.platform.model.UserTeam;
import top.jnaw.jee.platform.model.Users;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Shiros;
import top.jnaw.jee.utils.nim.Teams;


public class TeamController extends Controller {

  /**
   * 新建群组
   *
   * @tname 群组名
   * @username 群主
   * @membersS 成员 aaa,bbb
   * @msg 邀请语
   * @magree 是否需要对方同意 0不需要1需要
   * @joinmode 0不用验证，1需要验证,2不允许任何人加入 默认0
   */
//  @Before(POST.class)
  @RequiresPermissions({"team:add"})
  public void add() {
    final String tname = getPara("tname", "");
    String username = Shiros.getUser();
    final String owner = getPara("username", username);
    final String departmentIds = getPara("departmentIds", "");
    final String membersS = getPara("usernames", owner);
    final String msg = getPara("msg", "你好");
    final String magree = getPara("magree", "0");
    final String joinmode = getPara("joinmode", "0");
    boolean res = false;
    String membersSS = membersS+","+owner;
    JSONArray aa = new JSONArray();
    if (StrKit.notBlank(tname, owner, msg, magree, joinmode)) {

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
      UsersString.append(membersSS);
      String members = getStingS(UsersString.toString());

      //请求网易云信
      String data = Teams.createTeam(tname, owner, members, msg, magree, joinmode);

      JSONObject jsonObject = JSONObject.parseObject(data);
      if (jsonObject.getInteger("code") == 200) {
        Team team = new Team();
        String tid = jsonObject.getString("tid");
        team.setNo(tid);
        team.setName(tname);
        team.setOwner(owner);
        //存储到本地，群
        res = team.save();
        //用户列表
        if (res) {

          if (jsonObject.toString().indexOf("faccid") != -1) {
            aa = jsonObject.getJSONObject("faccid").getJSONArray("accid");
          }
          //添加用户到本地
          res = res && teamAddUser(aa, members, tid);
        }
      }
    }
    if (res) {
      renderJson(Https.success("创建成功", aa));
    } else {
      renderJson(Https.failure("创建失败", ""));
    }
  }

  /**
   * 群组的用户添加到本地
   *
   * @param a 响应回来的未加入群的用户
   * @param members 人员列表 ['a','b','c']
   * @param tno 群组名称
   */
  private boolean teamAddUser(JSONArray a, String members, String tno) {
    JSONArray json = JSONArray.parseArray(members);
    //将没有的账号删除
    if (a.size() > 0) {
      for (int i = 0; i < a.size(); i++) {
        json.remove(a.get(i));
      }
    }
    if (json.size() > 0) {
      for (int i = 0; i < json.size(); i++) {
        Users users = Users.getUserId(json.get(i).toString());
        if (users != null) {
          int userId = users.getId();
          //群组成员录入数据库
          UserTeam.dao.add(userId,tno);
        }
      }
    }
    return true;
  }

  /**
   * 解散群组，连带将本地去群组表人员删除
   *
   * @tid 群号
   * @owner 群主 username
   */
//  @Before(POST.class)
  @RequiresPermissions({"team:del"})
  public void del() {
    final String tid = getPara("tid", "");
    String owner = "";
    Team team = Team.dao.findByON(tid);
    if (team != null) {
      owner = team.getOwner();
    }
    final String owner1 = getPara("owner", owner);
    boolean res = false;
    if (StrKit.notBlank(tid, owner1)) {
      res = Teams.removeTeam(tid, owner1);
      res = res & Team.dao.deleteByON(tid);
    }

    if (res) {
      renderJson(Https.success("解散成功", ""));
    } else {
      renderJson(Https.failure("解散失败", ""));
    }
  }

  /**
   * 拉人进群
   * tid 群组no
   * owner 群主账号
   * membersS 带加入人员 a,,b,c,d
   * msg 邀请语
   * magree 是否需要对方同意
   */
  @Before(POST.class)
  @RequiresAuthentication
  public void addUsers() {
    final String tid = getPara("tid", "");
    String owner = "";
    Team team = Team.dao.findByON(tid);
    if (team != null) {
      owner = team.getOwner();
    }
    final String departmentIds = getPara("departmentIds", "");
    final String membersS = getPara("usernames", "");
    final String msg = getPara("msg", "你好");
    final String magree = getPara("magree", "0");

    JSONArray aa = new JSONArray();
    boolean res = false;
    if (StrKit.notBlank(tid, owner, membersS, msg, magree)) {
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
      //网易云操作
      String data = Teams.addUsers(tid, owner, members, msg, magree);

      JSONObject jsonObject = JSONObject.parseObject(data);
      if (jsonObject.getInteger("code") == 200) {
        //用户列表
        res = true;
        if (jsonObject.toString().indexOf("faccid") != -1) {
          aa = jsonObject.getJSONObject("faccid").getJSONArray("accid");
        }
        //添加用户到本地
        res = res && teamAddUser(aa, members, tid);

      }
    }

    if (res) {
      renderJson(Https.success("添加成功", aa));
    } else {
      renderJson(Https.failure("添加失败", ""));
    }
  }

  /**
   * 删除群中指定用户
   *
   * @tid 群组no
   * @username
   * @member
   */
  @Before(POST.class)
  @RequiresPermissions({"team:delUser"})
  public void delUser() {
    final String tid = getPara("tid", "");

    final String member = getPara("userb", "");
    //TODO tid 查username（完成）
    String owner = "";
    Team team = Team.dao.findByON(tid);
    if (team != null) {
      owner = team.getOwner();
    }

    boolean res = false;
    if (StrKit.notBlank(tid, owner, member)) {
      Users users = Users.getUserId(member);
      if (users != null) {
        int uid = users.getId();
        res = Teams.delUser(tid, owner, member);
        res = res & UserTeam.dao.delUserBytUidAndTno(uid, tid);
      }
    }

    if (res) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 更新群组信息
   *
   * @tid 群号no
   * @tname 新群名 可选
   * @username 群主账号
   * @announcement: 群公告 可选
   * @intro： 群描述，最大长度512字符 可选
   * @joinmode: 群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。
   */
  @Before(POST.class)
  @RequiresPermissions({"team:set"})
  public void set() {
    final String tid = getPara("tid", "");
    final String tname = getPara("tname", "");

    String owner = "";
    Team team = Team.dao.findByON(tid);
    if (team != null) {
      owner = team.getOwner();
    }

    final String announcement = getPara("announcement", "");
    final String intro = getPara("intro", "");
    final String joinmode = getPara("joinmode", "0");
    boolean res = false;

    if (StrKit.notBlank(owner, tid)) {
      res = Teams.set(tid, tname, owner, announcement, intro, joinmode);
      team.setName(tname);
      //本地更新
      res = res & team.update();
      //TODO bendishujv （完成）
    }

    if (res) {
      renderJson(Https.success("编辑成功", ""));
    } else {
      renderJson(Https.failure("编辑失败", ""));
    }
  }

  /**
   * 修改群消息提醒
   *
   * @tid 群唯一标识 对应no
   * @username 要操作的群成员accid
   * @ope:1 关闭消息提醒，2：打开消息提醒
   */
  @Before(POST.class)
  @RequiresPermissions({"team:muteTeam"})
  public void muteTeam() {
    final String tid = getPara("tid", "");
    final String username = getPara("username", "");
    final int ope = getParaToInt("ope", 2);
    boolean res = false;
    if (StrKit.notBlank(tid, username)) {
      res = Teams.muteTeam(tid, username, ope);

    }
    if (res) {
      if (ope == 1) {
        renderJson(Https.success("关闭成功", ""));
      } else {
        renderJson(Https.success("开启成功", ""));
      }
    } else {
      renderJson(Https.failure("编辑失败", ""));
    }
  }

  /**
   * 主动退出群组
   *
   * @tid 群组no，
   * @username ：退群人员
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"team:leave"})
  public void leave() {
    final String tid = getPara("tid", "");
    String username1 = Shiros.getUser();//默认当前登录人
    final String username = getPara("username", username1);
    boolean res = false;
    if (StrKit.notBlank(tid, username)) {
      res = Teams.leave(tid, username);
    }
    if (res) {
      renderJson(Https.success("退出成功", ""));
    } else {
      renderJson(Https.failure("退出失败", ""));
    }
  }

  /**
   * 查询单个群组信息
   * tid 群组账号no
   */
  @RequiresPermissions({"team:get"})
  public void getTeamInfo() {
    final String tid = getPara("tid", "");
    Team team = null;
    if (StrKit.notBlank(tid)) {
      team = Team.dao.findByON(tid);
    }

    if (team != null) {
      renderJson(Https.success("查询成功", team));
    } else {
      renderJson(Https.failure("查询失败", ""));

    }
  }
  /**
   * 查询群组列表
   * all  是否查询所有 -1 按分页，，1其他，直接查询全部
   */
  @RequiresPermissions({"team:get"})
  public void list() {
    final int all = getParaToInt("all", -1);
    final int page = getParaToInt("page", 1);
    final int size = getParaToInt("size", PropKit.getInt("paginate_size", 10));
    if (all == -1) {
      Page<Team> list = Team.dao.getTeams( page, size );

      renderJson(Https.success("", list));
    }else {
      List<Team> listAll = Team.dao.getTeamsAll();
      renderJson(Https.success("", listAll));
    }
  }

  //TODO 还未写的方法1、个人加的群列表（完成）；2群组的单独信息（完成）；
  //TODO 群主的问题   我创建的群 （完成），

  /**
   * 获取用户所加入的群组信息，以及自己创建的群
   * username 用户名
   * type 是否查询自己创建的群   默认0不查，其他查
   */
//  @Before(GET.class)
  @RequiresAuthentication
  @RequiresPermissions({"team:get"})
  public void getUserTeam() {
    String username1 = Shiros.getUser();//默认当前登录人
    final String username = getPara("username", username1);
    final int type = getParaToInt("type", 0);

    if (StrKit.notBlank(username)) {
      String json = Teams.joinTeams(username);
      JSONArray jsonArray1 = new JSONArray();
      if (json != null) {
        JSONObject jb = (JSONObject) JSONObject.parse(json);

        if (jb.getInteger("count") > 0) {
          JSONArray jsonArray = jb.getJSONArray("infos");
          //判断是否查自己创建的群0 不查
          if (type == 0) {
            jsonArray1 = jsonArray;

          } else {
            for (int i = 0; i < jsonArray.size(); i++) {
              JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json

              if (job.getString("owner").equals(username)) {
                jsonArray1.add(job);
              }
            }
          }

        }
      }
      renderJson(Https.success("", jsonArray1));
    }


  }


  /**
   * 将规律的字符串，变数组型字符串
   */
  private String getStingS(String s) {
    String[] b = s.split(",");
    StringBuilder m = new StringBuilder();
    m.append("[");
    for (int a = 0; a < b.length; a++) {
      if (a < b.length - 1) {
        m.append("'" + b[a] + "',");
      } else {
        m.append("'" + b[a] + "']");
      }
    }
    return m.toString();
  }
}
