package top.jnaw.jee.platform.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import top.jnaw.jee.platform.model.base.BaseNotice;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Shiros;


@SuppressWarnings("serial")
public class Notice extends BaseNotice<Notice> {

  public static final Notice dao = new Notice().dao();

  /**
   * 查询列表
   * @param page
   * @param pageSize
   * @param title
   * @param start_time
   * @param end_time
   * @param type
   * @param code
   * @return
   */
  public static Page<Record> getNotices(int page, int pageSize, String title, String start_time,
    String end_time, String type, String code) {
    Page<Record> list = null;

    String select = "SELECT"
      + "  n.id,"
      + "  n.title,"
      + "  n.content,"
      + "  n.create_time,"
      + "  t.name type_name";
    String minesqlExceptSelect = " FROM notice n "
      + " LEFT JOIN notice_type t "
      + " ON n.type = t.id "
      + " WHERE 1=1 "
      + " AND n.create_id = " + Users.getUserInfo(Shiros.getUser()).getId()
      + " AND n.remove = 1";
    String mesqlExceptSelect = " FROM notice n "
      + " LEFT JOIN notice_type t"
      + " ON n.type = t.id"
      + " LEFT JOIN notice_people p"
      + " ON n.id = p.notice_id"
      + " WHERE 1=1"
      + " AND p.uid = " + Users.getUserInfo(Shiros.getUser()).getId()
      + " AND n.remove = 1";
    if (StrKit.notBlank(title)) {
      minesqlExceptSelect += " AND n.title='" + title+"'";
      mesqlExceptSelect += " AND n.title='" + title+"'";
    }
    if (StrKit.notBlank(start_time) && StrKit.notBlank(end_time)) {
      minesqlExceptSelect +=
        " ANd n.create_time > '" + start_time + "' ANd n.create_time < '" + end_time + "'";
      mesqlExceptSelect +=
        " ANd n.create_time > '" + start_time + "' ANd n.create_time < '" + end_time + "'";
    }
    if (StrKit.notBlank(type)) {
      minesqlExceptSelect += " AND n.type=" + type;
      mesqlExceptSelect += " AND n.type=" + type;
    }
    for (VALUE value : VALUE.values()) {
      if (value.toString().equals(code)) {
        switch (code) {
          case "mine":
            list = Db.paginate(page, pageSize, select, minesqlExceptSelect);
            break;
          case "me":
            list = Db.paginate(page, pageSize, select, mesqlExceptSelect);
            break;
        }
      }
    }

    return list;
  }

  /**
   * 查询被通知人列表
   * @param page
   * @param pageSize
   * @param notice_id
   * @return
   */
  public Page<Record> getNoticePeoples(int page, int pageSize, int notice_id) {
    Page<Record> list = null;

    String select = "SELECT"
      + "  p.id,"
      + "  p.status,"
      + "  u.name";
    String sqlExceptSelect = " FROM notice_people p "
      + " LEFT JOIN users u "
      + " ON p.uid = u.id "
      + " WHERE p.notice_id =" + notice_id;
    list = Db.paginate(page, pageSize, select, sqlExceptSelect);

    return list;
  }

  /**
   * 添加通知
   * @param title
   * @param content
   * @param type
   * @param comment
   * @return
   */
  public int add(String title,String content,int type,int comment){
    Notice notice = new Notice();
    notice.setTitle(title);
    notice.setContent(content);
    notice.setType(type);
    notice.setCreateId(Users.getUserInfo(Shiros.getUser()).getId());
    notice.setCreateTime(Dates.nowByFormat());
    notice.setComment(comment);
    notice.save();
    return notice.getId();
  }

  /**
   * 删除通知
   * @param id
   * @return
   */
  public boolean del(int id) {
    boolean flag = false;

    Notice notice = Notice.dao.findById(id);
    if (notice != null) {
      notice.setRemove(0);
      flag = notice.update();
    }

    return flag;
  }

  /**
   * 撤销/发布
   * @param id
   * @return
   */
  public Notice setRepealStatus(int id) {
    boolean flag = false;

    Notice notice = Notice.dao.findById(id);

    if (notice != null) {
      if (0 == notice.getRepeal()) {
        notice.setRepeal(1);
        flag = notice.update();
      } else {
        notice.setRepeal(0);
        flag = notice.update();
      }
    }

    if (flag) {
      return notice;
    }
    Notice notice1 = null;
    return notice1;
  }

  /**
   * 修改通知已读
   * @param notice_id
   * @return
   */
  public boolean setStatus(int notice_id) {
    NoticePeople noticePeople = new NoticePeople();
    boolean flag = false;

    String select = "SELECT * ";
    String sqlExceptSelect = " FROM notice_people "
      + " WHERE 1=1"
      + " AND uid =" + Users.getUserInfo(Shiros.getUser()).getId()
      + " AND notice_id =" + notice_id;
    noticePeople = NoticePeople.dao.findFirst(select + sqlExceptSelect);

    if (noticePeople != null) {
      noticePeople.setStatus(0);
      flag = noticePeople.update();
    }

    return flag;
  }

  public enum VALUE {mine, me;}
}
