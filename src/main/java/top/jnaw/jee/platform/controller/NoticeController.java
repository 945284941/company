package top.jnaw.jee.platform.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.Notice;
import top.jnaw.jee.platform.model.NoticePeople;
import top.jnaw.jee.utils.Https;

public class NoticeController extends Controller {

  /**
   * 查询通知列表
   * 参数：
   * page:页码
   * pageSize:每页条数
   * title：标题
   * start_time：创建时间
   * end_time：结束时间
   * type:通知类型
   * code:为类型 mine查询我创建的、me查询与我相关、为null查询与我相关
   * 返回值：
   * JSONARRAY
   */
  @Before(GET.class)
  @RequiresPermissions({"notice:getNotices"})
  public void getNotices() {
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    final String title = getPara("title", "");
    final String start_time = getPara("start_time", "");
    final String end_time = getPara("end_time", "");
    final String type = getPara("type", "");
    final String code = getPara("code", "me");

    Page<Record> list = Notice.getNotices(page, pageSize, title, start_time, end_time, type, code);

    if (list != null && list.getList().size() > 0) {
      renderJson(Https.success("查询成功", list));
    } else {
      renderJson(Https.success("查询失败", ""));
    }
  }

  /**
   * 查询被通知人列表
   * 参数：
   * page: 分页页码
   * pageSiz:分页页宽
   * nid:通知id
   * 返回值：
   * JSONARRAY
   */
  @Before(GET.class)
  @RequiresPermissions({"notice:getNoticePeoples"})
  public void getNoticePeoples() {
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    final int notice_id = getParaToInt("nid", -1);
    Page<Record> list = null;

    if (notice_id > 0) {
      list = Notice.dao.getNoticePeoples(page, pageSize, notice_id);
    }

    if (list != null && list.getList().size() > 0) {
      renderJson(Https.success("查询成功", list));
    } else {
      renderJson(Https.success("查询失败", ""));
    }
  }

  /**
   * 根据通知id获取通知的基本信息
   * 参数：
   * id:通知编号
   * 返回值：
   * JSONOBJECT
   */
  @Before(GET.class)
  @RequiresPermissions({"notice:getNotice"})
  public void getNotice() {
    final int notice_id = getParaToInt("id", -1);
    Notice notice = null;

    if (notice_id > 0) {
      notice = Notice.dao.findById(notice_id);
    }
    if (notice != null) {
      renderJson(Https.success("查询成功", notice));
    } else {
      renderJson(Https.success("查询失败", ""));
    }
  }

  /**
   * 增加通知
   * 参数：
   * title：标题
   * content：内容
   * type：类型
   * departmentIds：部门id  1,2,3
   * userIds:通知人id   32,33,34
   * comment：是否允许评论
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"notice:add"})
  public void add() {
    final String title = getPara("title", "");
    final String content = getPara("content", "");
    final int type = getParaToInt("type", -1);
    final String departmentIds = getPara("departmentIds", "");
    final String userIds = getPara("userIds", "");
    final int comment = getParaToInt("comment", 1);
    boolean flag = false;

    //存通知信息
    int notice_id = 0;
    if (StrKit.notBlank(title) && StrKit.notBlank(content) && type > 0 && comment > -1) {
      notice_id = Notice.dao.add(title, content, type, comment);
    }
    //判断departmentIds、userIds
    if (StrKit.notBlank(departmentIds) || StrKit.notBlank(userIds)) {
      //处理部门id
      int[] departmentIdArr = new int[]{-1};
      if (StrKit.notBlank(departmentIds)) {
        String[] departments = departmentIds.split(",");
        departmentIdArr = new int[departments.length];//存储前台传过来的部门id
        for (int i = 0; i < departments.length; i++) {
          departmentIdArr[i] = Integer.parseInt(departments[i]);
        }
      }
      //处理前台传过来用户id
      String[] users = null;
      if (StrKit.notBlank(userIds)) {
        users = userIds.split(",");
      }
      //存被通知人
      flag = NoticePeople.add(notice_id, users, departmentIdArr);
    }

    if (flag) {
      renderJson(Https.success("添加成功", ""));
    } else {
      //被通知人没有添加成功时，当前条通知
      Notice.dao.deleteById(notice_id);
      renderJson(Https.failure("添加失败", ""));
    }
  }

  /**
   * 删除通知
   * 参数：
   * id:通知id
   * 返回值：
   * result
   */
  @Before(POST.class)
  @RequiresPermissions({"notice:del"})
  public void del() {
    final int id = getParaToInt("id", -1);
    boolean flag = false;

    if (id > 0) {
      flag = Notice.dao.del(id);
    }

    if (flag) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 撤回/发布
   * 参数：
   * id:通知id
   * 返回值：
   * result
   */
  @Before(POST.class)
  @RequiresPermissions({"notice:setRepeal"})
  public void setRepeal() {
    final int id = getParaToInt("id", -1);
    Notice notice = null;

    if (id > 0) {
      notice = Notice.dao.setRepealStatus(id);
    }

    if (notice != null) {
      if (notice.getRepeal() == 1) {
        renderJson(Https.success("发布成功", ""));
      } else {
        renderJson(Https.failure("撤销成功", ""));
      }
    } else {
      renderJson(Https.failure("修改状态失败", ""));
    }
  }

  /**
   * 修改通知已读
   * 参数：
   * nid:通知编号
   * uid:用户编号
   * 返回值：
   * result
   */
  @Before(POST.class)
  @RequiresPermissions({"notice:setStatus"})
  public void setStatus() {
    final int notice_id = getParaToInt("nid", -1);
    boolean flag = false;

    if (notice_id > 0) {
      flag = Notice.dao.setStatus(notice_id);
    }

    if (flag) {
      renderJson(Https.success("已读", ""));
    } else {
      renderJson(Https.failure("未读", ""));
    }
  }

}
