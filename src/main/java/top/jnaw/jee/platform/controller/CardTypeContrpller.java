package top.jnaw.jee.platform.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.CardType;
import top.jnaw.jee.platform.model.Users;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Shiros;
import top.jnaw.jee.utils.nim.User;

/**
 * Created by lxh on 2017/11/24.
 */
public class CardTypeContrpller extends Controller {

  /**
   * 修改卡片的类型
   * <p>
   * 参数:
   *
   * @id:卡片的id
   * @name:卡片的名称 返回值：
   * @JSONARRAY
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"cardtype:set"})
  public void set() {

    //---------parameter
    final int id = getParaToInt("id", -1);
    final String name = getPara("name", "");
    boolean flage = false;

    //-----------Handle
    if (id >= 0) {
      flage = CardType.dao.set(id, name);
    }

    //------------result
    if (flage) {
      renderJson(Https.success("修改成功", ""));
    } else {
      renderJson(Https.failure("修改失败", ""));
    }
  }

  /**
   * 删除 card 的类型
   * <p>
   * 参数：
   *
   * @id:卡片的id 返回值：
   * @JSONARRAY
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"cardtype:del"})
  public void del() {

    //-----parameter
    final int id = getParaToInt("id", -1);
    boolean flage = false;

    //----Handle
    if (id >= 0) {
      flage = CardType.dao.rm(id);
    }

    //-----result
    if (flage) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 查看card类型列表
   * 参数:
   *
   * @page 页数
   * @pageSize 页数
   * @name 卡片的名称 返回值：
   * @JSONARRAY
   */
  @Before(GET.class)
  @RequiresAuthentication
  @RequiresPermissions({"cardtype:getCardTypes"})
  public void getCardTypes() {

    //---parameter
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    final String name = getPara("name", "");
    final String type = getPara("type", "");
    Page<Record> list = null;
    List<CardType> clist = null;
    switch (type) {

      case "fenye":
        //----Handler
        list = CardType.dao.getCardTypes(page, pageSize, name);

        break;
      default:
        clist = CardType.dao.getCardType(name);
        break;
    }
    //result
    if (list != null && list.getList().size() != 0) {
      renderJson(Https.success("查询成功", list));
      return;
    }
    if (clist != null && clist.size() != 0) {
      renderJson(Https.success("查询成功", clist));
      return;
    }
  }


  /**
   * 增加card类型
   * 参数：
   *
   * @name 卡片的名称
   * @create_id 创建者的 返回值
   * @result
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"cardtype:add"})
  public void add() {

    //---parameter
    final String name = getPara("name", "");
    final int create_id = getParaToInt("create_id", -1);
    boolean flage = false;

    //-----Handler
    if (StrKit.notBlank(name) && create_id >= 0) {
      flage = CardType.dao.add(name);
    }

    //------result
    if (flage) {
      renderJson(Https.success("保存成功", ""));
    } else {
      renderJson(Https.failure("保存失败", ""));
    }

  }
}
