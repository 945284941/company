package top.jnaw.jee.platform.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import top.jnaw.jee.platform.model.base.BaseComment;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Shiros;


@SuppressWarnings("serial")
public class Comment extends BaseComment<Comment> {

  public static final Comment dao = new Comment().dao();

  /**
   * 查询评论列表
   * @param page
   * @param pageSize
   * @param nid
   * @return
   */
  public static Page<Record> getComments(int page, int pageSize, int nid) {
    Page<Record> comments = null;

    String select = "select * ";
    String sqlExceptSelect = " from comment where 1=1"
      + " and nid=" + nid
      + " order by create_time desc";

    return Db.paginate(page, pageSize, select, sqlExceptSelect);
  }

  /**
   * 添加评论
   * @param notice_id
   * @param content
   * @return
   */
  public static boolean add(int notice_id, String content) {
    boolean flag = false;

      Comment comment = new Comment();
      comment.setNid(notice_id);
      comment.setUid(Users.getUserInfo(Shiros.getUser()).getId());
      comment.setContent(content);
      comment.setCreateTime(Dates.nowByFormat());
      flag = comment.save();

    return flag;
  }

  /**
   * 删除评论
   * @param id
   * @return
   */
  public static boolean del(int id) {
    boolean flag = false;

      Comment comment = Comment.dao.findById(id);
      if (comment != null) {
        comment.setRemove(0);
        flag = comment.update();
      }

    return flag;
  }

  /**
   * 修改评论
   * @param id
   * @param content
   * @return
   */
  public static boolean set(int id, String content) {
    boolean flag = false;

      Comment comment = Comment.dao.findById(id);
      if (comment != null) {
        comment.setContent(content);
        flag = comment.update();
      }

    return flag;
  }

}

