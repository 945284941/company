package top.jnaw.jee.platform.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import java.lang.reflect.Type;
import java.util.List;
import top.jnaw.jee.platform.model.base.BaseTeam;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Team extends BaseTeam<Team> {

  public static final Team dao = new Team().dao();

  /**
   * 根据no删除群组
   *
   * @param no 群组标志
   */
  public boolean deleteByON(String no) {
    String Sql = "delete from team where no='" + no + "'";
    int a = Db.update(Sql);
    if (a > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 查询单个群组的信息，不包含用户列表
   *
   * @param no 群账号-tid
   */
  public Team findByON(String no) {
    String Sql = "select * from team where no = '" + no + "'";
    List<Team> teams = Team.dao.find(Sql);
    if (teams.size() > 0) {
      return teams.get(0);
    } else {
      return null;
    }
  }

  /**
   * 分页查询群组的用户列表
   */
  public Page<Users> getUsersByTno(String tid, int page, int size) {

    final String select = "SELECT"
        + "  users.id   id,"
        + "  users.username ,"
        + "  users.name name,"
        + "  users.nickname ";
    final String except = " FROM user_team "
        + "  JOIN users ON users.id = user_team.uid"
        + "  WHERE users.state= 1 and  user_team.tno = '" + tid + "' ORDER BY id";

    Page<Users> list = Users.dao.paginate(page, size, select, except);
    return list;
  }

  /**
   * 根据群号查询所有成员列表
   * @param tid  群号对应no
   * @return
   */
  public List<Users> getUsersByTno2(String tid) {
    final String select = "SELECT"
        + "  users.id   id,"
        + "  users.username ,"
        + "  users.name name,"
        + "  users.nickname "
        + "  team.name tname "
        + "  JOIN users ON users.id = user_team.uid"
        + "  WHERE users.state= 1 and  user_team.tno = '" + tid + "' ORDER BY id";
    List<Users> list = Users.dao.find(select);
    return list;
  }

  public Page<Team> getTeams(int page,int size ){
    final String select = "SELECT * ";
    final String except = " FROM team  ORDER BY id";

    Page<Team> list = Team.dao.paginate(page, size, select, except);

    return list;

  }

  public List<Team> getTeamsAll( ){
    final String select = "SELECT * FROM team  ORDER BY id";

    List<Team> list = Team.dao.find(select);

    return list;

  }
}