package top.jnaw.jee.platform.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.Comment;
import top.jnaw.jee.utils.Https;


public class CommentController extends Controller {

  /**
   * 评论列表
   * 参数：
   * nid:通知id
   * 返回值：
   * JSONARRAY
   */
  @Before(GET.class)
  @RequiresPermissions({"comment:getComments"})
  public void getComments() {
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    final int nid = getParaToInt("nid", -1);
    Page<Record> comments = null;

    if (nid > 0) {
      comments = Comment.getComments(page, pageSize, nid);
    }

    if (comments != null && comments.getList().size() != 0) {
      renderJson(Https.success("查询成功", comments));
    } else {
      renderJson(Https.success("查询失败", ""));
    }
  }

  /**
   * 添加评论
   * 参数：
   * nid:通知id
   * content：评论内容
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"comment:add"})
  public void add() {
    final int notice_id = getParaToInt("nid", -1);
    final String content = getPara("content", "");
    boolean flag = false;

    if (notice_id > 0 && StrKit.notBlank(content)) {
      flag = Comment.add(notice_id, content);
    }

    if (flag) {
      renderJson(Https.success("评论成功", ""));
    } else {
      renderJson(Https.failure("评论失败", ""));
    }
  }

  /**
   * 删除评论
   * 参数：
   * id:评论id
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"comment:del"})
  public void del() {
    final int id = getParaToInt("id", -1);
    boolean flag = false;

    if (id > 0) {
      flag = Comment.del(id);
    }

    if (flag) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 修改评论
   * 参数：
   * id：评论id
   * content:评论内容
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"comment:setComment"})
  public void setComment() {
    final int id = getParaToInt("id", -1);
    final String content = getPara("content", "");
    boolean flag = false;

    if (id > 0 && StrKit.notBlank(content)) {
      flag = Comment.set(id, content);
    }

    if (flag) {
      renderJson(Https.success("修改成功", ""));
    } else {
      renderJson(Https.failure("修改失败", ""));
    }

  }

}
