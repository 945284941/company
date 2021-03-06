package top.jnaw.jee.platform.model;

import com.jfinal.kit.StrKit;
import java.util.List;
import top.jnaw.jee.platform.model.base.BaseUsers;
import top.jnaw.jee.utils.Shiros;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Users extends BaseUsers<Users> {

  public static final Users dao = new Users().dao();

  /**
   * 根据用户名获取用户dao
   *
   * @param username 用户名
   */
  public static Users getUserInfo(String username) {
    Users users = Users.dao
        .findFirst("SELECT"
            + "  users.id   id,"
            + "  users.username,"
            + "  users.name name,"
            + "  users.nickname,"
            + "  users.state,"
            + "  users.token"
            + "  FROM users"
            + "  WHERE username = ?", username);
    return users;
  }

  /**
   * 根据用户名获取用户dao
   *
   * @param username 用户名
   */
  public static Users getUserInfo2(String username) {
    Users users = Users.dao
        .findFirst("SELECT"
            + "  users.id   id,"
            + "  users.username,"
            + "  users.name name,"
            + "  users.nickname"
            + "  FROM users"
            + "  WHERE username = ?", username);
    return users;
  }

  /**
   * 添加用户
   *
   * @param username 用户名，登录名
   * @param password 登录密码
   * @param name 姓名
   * @param nickname 昵称
   */
  public static boolean addUser(String username, String password, String name,
      String nickname, String token) {

    boolean result = Shiros.addUser(username, password);

    if (result) {
      Users users = Users.dao
          .findFirst("SELECT * FROM users WHERE username = ?", username);
      users.setName(name);
      if (StrKit.notBlank(nickname)) {
        users.setNickname(name);
      } else {
        users.setNickname(nickname);
      }
      users.setToken(token);
      result = users.update();
    }

    return result;
  }


  /**
   * 根据用户名获取用户所在的部门
   *
   * @param username 用户名
   * @return 部门结构
   */
  public static List<Department> getDepartments(String username) {
    List<Department> list = null;

    Users user = Users.dao
        .findFirst("SELECT * FROM users"
            + " WHERE username = ?", username);
    if (null != user) {
      list = Department.dao.find("SELECT"
              + "   department.id,"
              + "   department.name,"
              + "   department.pid"
              + " FROM department"
              + " LEFT JOIN departments_users AS du"
              + "   ON du.uid = ?"
              + " WHERE du.did = department.id",
          user.getId());
    }

    return list;
  }

  /**
   * 根据用户名获取id
   * @param username 用户名
   * @return
   */
  public static Users getUserId(String username){
    String sql ="select * from users where username='"+username+"'";
    List<Users> users = Users.dao.find(sql);
    if(users.size()>0){
      return users.get(0);
    }else {
      return null;
    }
  }
}
