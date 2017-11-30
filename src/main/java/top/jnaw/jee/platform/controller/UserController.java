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
import top.jnaw.jee.utils.Models;
import top.jnaw.jee.utils.Shiros;
import top.jnaw.jee.utils.nim.Teams;
import top.jnaw.jee.utils.nim.User;

public class UserController extends Controller {

  /**
   * 登录
   * username 用户名
   * password 密码
   *
   */

  public void login() {
    final String username = getPara("username", "");
    final String password = getPara("password", "");
    boolean aa = Shiros.login(username, password);
    if(aa){
      renderJson(Https.success("登录成功",""));
    }else {
      renderJson(Https.failure("登录失败",""));
    }
  }

  /**
   * 登出
   */
  @RequiresAuthentication
  public void logout() {
    Shiros.logout();
    renderJson(Https.success("退出成功",""));
  }

  /**
   * 获取用户基本信息
   */
  @RequiresAuthentication
  @RequiresPermissions({"user:get"})
  public void get() {
    String username = getPara("username", "");
    if (StrKit.isBlank(username)) {
      username = Shiros.getUser();
      renderJson(Https.success("", Users.getUserInfo(username)));
    } else {
      renderJson(Https.success("", Users.getUserInfo2(username)));
    }
  }

  /**
   * 登录加获取用户信息
   */

  public void getUserInfo() {
    final String username = getPara("username", "");
    final String password = getPara("password", "");
    Users users =Users.getUserInfo(username);

    if (Shiros.login(username, password)) {
      if(users.getState()!=0){
        renderJson(Https.success("", users));
      }else{
        renderJson(Https.failure("该用户已被禁用,请联系管理员解禁", ""));
      }
    } else {
      renderJson(Https.failure("用户名或密码错误", ""));
    }

  }

  /**
   * 添加一个用户
   */
  @Before(POST.class)
  @RequiresPermissions({"user:add"})
  public void add() {

    final String username = getPara("username", "");
    final String password = getPara("password", "");
    final String name = getPara("name", "");
    final String nickname = getPara("nickname", "");

    boolean result = false;
    boolean result2 = true;
    if (StrKit.notBlank(username, password, name)) {
      String token = User.create(username, name);
      if (token != null) {
        result = Users.addUser(username, password, name, nickname, token);
        //目前先使用这个tid
        result2 = result & addUsersToTeam("212416687",username);
      }
    }
    if(result){
      if(result2){
        renderJson(Https.success("添加成功",""));
      }else {
        renderJson(Https.failure("用户加群失败",""));
      }
    }else {
      renderJson(Https.failure("添加失败",""));
    }
  }

  /**
   * 将新创建的人员添加进默认一个群组
   * @param tid
   * @param username
   */
  private boolean addUsersToTeam(String tid,String username) {
    String owner = "";
    Team team = Team.dao.findByON(tid);
    if (team != null) {
      owner = team.getOwner();
    }
     String msg = "欢迎加入本群";
     String magree =  "0";

    JSONArray aa = new JSONArray();
    boolean res = false;
    if (StrKit.notBlank(tid, owner, username, msg, magree)) {

      String members = "['"+username+"']";
      //网易云操作
      String data = Teams.addUsers(tid, owner, members, msg, magree);

      JSONObject jsonObject = JSONObject.parseObject(data);
      if (jsonObject.getInteger("code") == 200) {

        if (jsonObject.toString().indexOf("faccid") != -1) {
          aa = jsonObject.getJSONObject("faccid").getJSONArray("accid");
        }else {
          //添加用户到本地
          Users users = Users.getUserId(username);
          res = UserTeam.dao.add(users.getId(), tid);
        }
      }
    }

    return  res;
  }



  /**
   * 删除一个用户，先不使用此方法
   */
  @RequiresPermissions({"user:del"})
  public void del() {

    final String username = getPara("username", "");

    boolean result = false;

    if (StrKit.notBlank(username)) {
      result = Shiros.deleteUser(username);
    }

    renderJson(Https.success("", Models.result(result)));
  }

  /**
   * 禁用用户
   *
   * @username 用户名
   * @needkick 是否踢出apk
   */
  @Before(POST.class)
  @RequiresPermissions({"user:block"})
  public void block() {
    final String username = getPara("username", "");
    final String needkick = getPara("needkick", "false");
    boolean result = false;
    if (StrKit.notBlank(username)) {
      if (needkick.equals("true")) {
        result = true;
      }
      Users users = Users.getUserId(username);
      if (users != null) {
        result = User.blockID(username, result);
        users.setState(0);
        result = result & users.update();
      }
    }

    if (result) {
      if (needkick.equals("true")) {
        renderJson(Https.success("用户禁用成功,无法被解禁", ""));
      } else {
        renderJson(Https.success("用户禁用成功", ""));
      }
    } else {
      renderJson(Https.failure("用户禁用失败", ""));
    }


  }


  /**
   * 解禁用户
   *
   * @username 用户名
   */
  @Before(POST.class)
  @RequiresPermissions({"user:unblock"})
  public void unblock() {
    final String username = getPara("username", "");
    boolean result = false;
    if (StrKit.notBlank(username)) {
      Users users = Users.getUserId(username);
      if (users != null) {
        result = User.unBlockID(username);
        users.setState(1);
        result = result & users.update();
      }
    }

    if (result) {
      renderJson(Https.success("用户解禁成功", ""));
    } else {
      renderJson(Https.failure("用户解禁失败", ""));
    }


  }

  /**
   * 设置用户信息 可设置NIM的name
   *
   * @password 用户新密码
   * @name 用户名
   * @nickname 用昵称
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"user:set"})
  public void set() {
    final String password = getPara("password", "");
    final String name = getPara("name", "");
    final String nickname = getPara("nickname", "");

    boolean result = false;

    String username = Shiros.getUser();

    if (StrKit.notBlank(username)) {
      Users users = Users.dao
          .findFirst("SELECT * FROM users WHERE username = ?", username);

      if (null != users) {
        if (StrKit.notBlank(name)) {
          users.setName(name);
        }

        if (StrKit.notBlank(nickname)) {
          users.setNickname(nickname);
        }

        if (StrKit.notBlank(password)) {
          Shiros.setPwd(users.getName(), password);
        }
        //这里修该NIM的name
        result = User.updateUinfo(users.getUsername(), users.getName(), null);
        if (result) {
          result = users.update();
        }
      }
    }

    if (result) {
      renderJson(Https.success("修改成功", ""));
    } else {
      renderJson(Https.failure("修改失败", ""));
    }
  }

  /**
   * 设置桌面端在线时，移动端是否需要推送
   *
   * @username 不填的话就是当前登录用户
   * @donnopOpen 0不推送，1推送
   */
//  @Before(GET.class)
  @RequiresPermissions({"user:setDonnop"})
  public void setDonnop() {
    final String donnopOpen = getPara("donnopOpen", "1");
    String username1 = Shiros.getUser();
    final String username = getPara("username", username1);
    boolean a = false;
    if (donnopOpen.equals(1)) {
      a = User.setDonnop(username, a);
    }

    if (a) {
      renderJson(Https.success("设置成功", ""));
    } else {
      renderJson(Https.failure("设置失败", ""));
    }
  }


  /**
   * 根据部门id获取用户列表
   */

  @RequiresPermissions({"user:list"})
  public void list() {
    final int did = getParaToInt("did", -1);
    final int page = getParaToInt("page", 1);
    final int size = getParaToInt("size", PropKit.getInt("paginate_size", 10));

    Page<Users> list = null;

    list = Department.dao.getUsersBydepID(did,page, size);

    renderJson(Https.success("", list));

  }

  /**
   * 根据群组id获取群内用户列表信息
   * tid 群组的no值
   * all 为空时查询所有成员不分页
   */
  @Before(POST.class)
  @RequiresPermissions({"user:getTeamUsers"})
  public void getTeamUsers() {
    final String tid = getPara("tid", "");
    final int all = getParaToInt("all", -1);
    final int page = getParaToInt("page", 1);
    final int size = getParaToInt("size", PropKit.getInt("paginate_size", 10));
    if (all == -1) {
      Page<Users> list = null;
      if (StrKit.notBlank(tid)) {
        String res = Teams.getUserInfos(tid,"1");
//        if (StrKit.notBlank(res)) {
//          JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
//          JSONObject jsonObject2 = (JSONObject) jsonObject.getJSONArray("tinfos").get(0);
//          JSONArray
//        }
        list = Team.dao.getUsersByTno(tid, page, size);
      }
      renderJson(Https.success("", list));
    }else {
       List<Users> listAll = Team.dao.getUsersByTno2(tid);
      renderJson(Https.success("", listAll));
    }
    //TODO 封装(完成)

  }

  /**
   * 重新获取Token
   *
   * @username 当前用户名，不用传
   */
//  @Before(GET.class)
  @RequiresAuthentication
  public void refreshToken() {
    String username1 = Shiros.getUser();
    final String username = getPara("username", username1);
    boolean a = false;
    Users users = Users.getUserId(username);

    if (StrKit.notBlank(username) && users != null) {
      String newToken = User.refreshToken(username);
      //修改本地存储
      if (StrKit.notBlank(newToken)) {
        users.setToken(newToken);
        a = users.update();
      }
    }

    if (a) {
      renderJson(Https.success("token修改成功", ""));
    } else {
      renderJson(Https.failure("", ""));
    }
  }

  /**
   * 获取全部的人员列表
   */
  public void findUserAll(){
    renderJson(Https.success("查询成功",Users.dao.find("select id,username,name,nickname,state from users")));
  }

}
