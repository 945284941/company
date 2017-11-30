package top.jnaw.jee.platform.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.NoticeType;
import top.jnaw.jee.utils.Https;

public class NoticeTypeController extends Controller {

  /**
   * 查询列表（分页）
   * 参数：
   * page:页码
   * pageSize:每页条数
   * 返回值：
   * JSONARRAY
   */
  @Before(GET.class)
  @RequiresPermissions({"noticetype:getNoticeTypes"})
  public void getNoticeTypes() {
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);

    Page<NoticeType> list = NoticeType.getNoticeTypes(page, pageSize);

    if (list != null && list.getList().size() != 0) {
      renderJson(Https.success("查询成功", list));
    } else {
      renderJson(Https.failure("查询失败", ""));
    }
  }

  /**
   * 查询列表（不分页）
   * 返回值：
   * JSONARRAY
   */
  @Before(GET.class)
  @RequiresPermissions({"noticetype:getNoticeTypes"})
  public void getNoticeTypesNotPage() {

    List<NoticeType> list = NoticeType.getNoticeTypesNotPage();

    if (list != null && list.size() != 0) {
      renderJson(Https.success("查询成功", list));
    } else {
      renderJson(Https.failure("查询失败", ""));
    }
  }


  /**
   * 根据类型id查询类型信息
   * 参数：
   * id：类型编号
   * 返回值：
   * JSONOBJECT
   */
  @Before(GET.class)
  @RequiresPermissions({"noticetype:getNoticeType"})
  public void getNoticeType() {
    final int id = getParaToInt("id", -1);
    NoticeType noticeType = null;

    if (id > 0) {
      noticeType = NoticeType.getNoticeType(id);
    }

    if (noticeType != null) {
      renderJson(Https.success("查询成功", noticeType));
    } else {
      renderJson(Https.failure("查询失败", ""));
    }

  }

  /**
   * 添加类型
   * 参数：
   * name:类型名称
   * create_id：创建者id
   * 返回值：
   * JSONOBJECT
   */
  @Before(POST.class)
  @RequiresPermissions({"noticetype:add"})
  public void add() {
    final String name = getPara("name", "");
    boolean flag = false;

    if (StrKit.notBlank(name)) {
      flag = NoticeType.add(name);
    }

    if (flag) {
      renderJson(Https.success("添加成功", ""));
    } else {
      renderJson(Https.failure("添加失败", ""));
    }
  }

  /**
   * 删除类型
   * 参数：
   * id:类型编号
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"noticetype:del"})
  public void del() {
    final int id = getParaToInt("id", -1);
    boolean flag = false;

    if (id > 0) {
      flag = NoticeType.del(id);
    }

    if (flag) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 修改
   * 参数：
   * id：类型编号
   * name：类型名称
   * 返回值：
   * RESULT
   */
  @Before(POST.class)
  @RequiresPermissions({"noticetype:set"})
  public void set() {
    final int id = getParaToInt("id", -1);
    final String name = getPara("name", "");
    boolean flag = false;

    if (id > 0) {
      flag = NoticeType.set(id, name);
    }

    if (flag) {
      renderJson(Https.success("修改成功", ""));
    } else {
      renderJson(Https.failure("修改失败", ""));
    }
  }

}
