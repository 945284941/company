package top.jnaw.jee.platform.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import java.util.List;
import top.jnaw.jee.platform.model.base.BaseNoticeType;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Shiros;


@SuppressWarnings("serial")
public class NoticeType extends BaseNoticeType<NoticeType> {

  public static final NoticeType dao = new NoticeType().dao();

  /**
   * 查询列表（分页）
   * @param page
   * @param pageSize
   * @return
   */
  public static Page<NoticeType> getNoticeTypes(int page, int pageSize) {
    Page<NoticeType> list = null;

    String sql = "select * ";
    String sql1 = "from notice_type where remove=1";
    list = NoticeType.dao.paginate(page, pageSize, sql, sql1);

    return list;
  }

  /**
   * 查询列表（不分页）
   * @return
   */
  public static List<NoticeType> getNoticeTypesNotPage() {
    List<NoticeType> list = null;

    String select = "select * ";
    String sqlExceptSelect = "from notice_type where remove=1";
    list = NoticeType.dao.find(select + sqlExceptSelect);

    return list;
  }

  /**
   * 根据类型id查询类型信息
   * @param id
   * @return
   */
  public static NoticeType getNoticeType(int id) {
    NoticeType noticeType = NoticeType.dao.findById(id);

    return noticeType;
  }

  /**
   * 添加通知类型
   * @param name
   * @return
   */
  public static boolean add(String name) {
    boolean flag = false;

    NoticeType noticeType = new NoticeType();
    noticeType.setName(name);
    noticeType.setCreateId(Users.getUserInfo(Shiros.getUser()).getId());
    noticeType.setCreateTime(Dates.nowByFormat());
    noticeType.setRemove(1);
    flag = noticeType.save();

    return flag;
  }

  /**
   * 删除通知类型
   * @param id
   * @return
   */
  public static boolean del(int id) {
    boolean flag = false;

    NoticeType noticeType = NoticeType.dao.findById(id);
    if (noticeType != null) {
      noticeType.setRemove(0);
      flag = noticeType.update();
    }

    return flag;
  }

  /**
   * 修改通知类型
   * @param id
   * @param name
   * @return
   */
  public static boolean set(int id, String name) {
    boolean flag = false;
    NoticeType noticeType = new NoticeType();

    noticeType = NoticeType.dao.findById(id);

    if (noticeType != null) {
      if (StrKit.notBlank(name)) {
        noticeType.set("name", name);
      }
      flag = noticeType.update();
    }

    return flag;
  }
}
