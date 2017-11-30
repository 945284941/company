package top.jnaw.jee.platform.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import top.jnaw.jee.platform.model.base.BaseNoticePeople;

@SuppressWarnings("serial")
public class NoticePeople extends BaseNoticePeople<NoticePeople> {

  public static final NoticePeople dao = new NoticePeople().dao();

  /**
   * 根据通知id 部门id[] 员工id[]添加被通知人
   * @param notice_id
   * @param users
   * @param departmentIdArr
   * @return
   */
  public static boolean add(int notice_id, String[] users, int[] departmentIdArr) {
    Map<String, String> userIdsMap = new HashMap<>();//存用户id
    if (departmentIdArr[0] > -1) {
      //根据部门id查询子部门id(包括当前部门id)
      Map<String, String> departmentIdMap = Department.getDepartmentIds(departmentIdArr);
      //取出所有部门id
      String ids = "";
      for (Entry<String, String> entry : departmentIdMap.entrySet()) {
        ids += entry.getKey() + ",";
      }
      ids = ids.substring(0, ids.length() - 1);
      //根据部门id查询部门下所有员工
      List<Users> usersList = Department.getDeparmentPeople(ids);
      //存用户id
      if (usersList != null) {
        for (Users user : usersList) {
          userIdsMap.put(String.valueOf(user.getId()),
            String.valueOf(user.getId()));//所有部门中用户id
        }
      }
    }
    //处理前台传过来用户id
    if (users != null && users.length > 0) {
      for (int i = 0; i < users.length; i++) {
        userIdsMap.put(users[i], users[i]);//存前台传过来的用户id
      }
    }
    //存被通知人
    String userIdStr = "";
    for (Map.Entry<String, String> entry : userIdsMap.entrySet()) {
      userIdStr += entry.getKey() + ",";
    }
    String[] userIdArr = userIdStr.split(",");
    //封装
    List<Record> userIdList = new ArrayList<Record>();//把所有被通知人存到list中，批量添加时使用
    for (int i = 0; i < userIdArr.length; i++) {
      NoticePeople noticePeople = new NoticePeople();
      noticePeople.setUid(Integer.parseInt(userIdArr[i]));
      noticePeople.setNoticeId(notice_id);
      noticePeople.setStatus(1);
      userIdList.add(noticePeople.toRecord());
    }
    //事务
    boolean succeed = Db.tx(new IAtom() {
      public boolean run() throws SQLException {
        int[] newFlag = Db.batchSave("notice_people", userIdList, 500);
        boolean flag = true;
        for (int i = 0; i < newFlag.length; i++) {
          if (0 == newFlag[i]) {
            flag = false;
          }
        }
        return flag;
      }
    });

    return succeed;
  }

  public static List<NoticePeople> getPeople(int nid) {
    List<NoticePeople> peopleList = NoticePeople.dao
      .find("select * from notice_people where nid=", nid);
    return peopleList;
  }
}
