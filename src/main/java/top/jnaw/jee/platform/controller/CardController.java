package top.jnaw.jee.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import top.jnaw.jee.platform.model.Card;
import top.jnaw.jee.platform.model.CardPeopleStatus;
import top.jnaw.jee.platform.model.UserTeam;
import top.jnaw.jee.platform.model.Users;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Log;
import top.jnaw.jee.utils.Shiros;

/**
 * Created by lxh on 2017/11/23.
 */
public class CardController extends Controller {


  /**
   * 获取card的列表
   * 参数：
   *
   * @type 类型 mine or me or All 1.mine 我发布的 2.me 与我相关的
   * @title 卡片的标题
   * @startTime 开始时间
   * @endTime 结束时间
   * @create_id 查询的对方id
   * @page 页数
   * @pageSize 页显示量 <p>
   *
   * 返回值:
   * @JSONARRAY
   */
  @Before(GET.class)
  @RequiresAuthentication
  @RequiresRoles(value = {"card"})
  @RequiresPermissions({"card:getCards"})
  public void getCards() {

    //----paraeter
    final String type = getPara("type", "");
    final String title = getPara("title", "");
    final int create_id = getParaToInt("create_id", -1);
    final String startTime = getPara("startTime", "");
    final String endTime = getPara("endTime", "");
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    Page<Record> list = new Page<Record>();

    for (Baby b : Baby.values()) {
      if (b.toString().equals(type)) {
        switch (type) {
          case "mine":
            list = Card.dao.mine(title, startTime, endTime, page, pageSize);
            break;
          case "me":
            list = Card.dao.me(title, startTime, endTime, page, pageSize);
            break;
          case "all":
            list = Card.dao.all(title, startTime, endTime, page, pageSize);
          default:
            break;
        }
      } else {
        if (create_id >= 0) {
          list = Card.dao
            .findCardListByPeople(title, startTime, endTime, page, pageSize, create_id);
        }
      }
    }

    //----result
    if (null == list || list.getList().size() == 0) {
      renderJson(Https.failure("暂无数据", ""));
    } else {
      renderJson(Https.success("查询成功", list));
    }
  }

  /**
   * 获取卡片的详细信息
   * <p>
   * 参数 ：
   *
   * @id 卡片的id
   *
   * 返回值：
   * @JSONOBJECT
   */
  @Before(GET.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:getCard"})
  public void getCard() {

    final int id = getParaToInt("id", -1);
    Card c = null;

    if (id >= 0) {
      c = Card.dao.findCardById(id);
    }

    if (c == null) {
      renderJson(Https.failure("暂无数据", ""));
    } else {
      renderJson(Https.success("查询成功", c));
    }
  }


  /**
   * 查看关于卡片的已读未读人员列表信息
   * <p>
   * 参数：
   *
   * @id 卡片的id
   * @page 页码
   * @pageSize 页显示数量 返回值：
   * @JSONARRAY
   */
  @Before(GET.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:getCardPeoples"})
  public void getCardPeoples() {

    final int id = getParaToInt("id", -1);
    final int page = getParaToInt("page", 1);
    final int pageSize = getParaToInt("pageSize", 10);
    Page<Record> list = new Page<Record>();

    if (id >= 0) {
      list = CardPeopleStatus.dao.getCardPeoples(id, page, pageSize);
    }

    if (null == list || list.getList().size() == 0) {
      renderJson(Https.failure("暂无数据", ""));
    } else {
      renderJson(Https.success("查询成功", list));
    }
  }


  /**
   * 卡片的添加
   * <p>
   * 参数：
   *
   * @title 标题
   * @context 内容
   * @type_idl 类型的id
   * @looker 人的数组
   * @guoupId 群组id
   *
   * 返回值：
   * @result
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:add"})
  public void add() {
    final String title = getPara("title", "");
    final String context = getPara("context", "");
    final int type_id = getParaToInt("type_id", -1);
    final String lookers = getPara("lookers", "");
    final String groupIds = getPara("groupIds", "");
    JSONArray ja = new JSONArray();
    int cid = 0;
    boolean flage = false;

    if (StrKit.notBlank(title) && StrKit.notBlank(context) && type_id >= 0) {
      cid = Card.dao.add(title, context, type_id);
    }

    //单聊
    if (StrKit.notBlank(lookers)) {
      String[] newLooker = lookers.split(",");
      flage = CardPeopleStatus.dao.add(cid, newLooker);
    }

    //群聊
    if (StrKit.notBlank(groupIds)) {
      String[] newGroupId = groupIds.split(",");
      for (int i = 0; i < newGroupId.length; i++) {
        flage = CardPeopleStatus.dao
          .addl(cid, UserTeam.dao.findPeopleByNo(Integer.parseInt(newGroupId[i])));
      }
    }
    if (flage) {
      renderJson(Https.success("保存成功", ""));
    } else {

      Card.dao.deleteById(cid);
      renderJson(Https.failure("保存失败", ""));
    }

  }


  /**
   * 删除卡片（改变状态）
   * 参数：
   *
   * @id卡片的id 返回值:
   * @result
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:del"})
  public void del() {
    final int id = getParaToInt("id", -1);
    boolean falge = false;

    if (id >= 0) {
      falge = Card.dao.del(id);
    }

    if (falge) {
      renderJson(Https.success("删除成功", ""));
    } else {
      renderJson(Https.failure("删除失败", ""));
    }
  }

  /**
   * 卡片的修改
   * <p>
   * 参数：
   *
   * @id卡片的id
   * @title卡片的标题
   * @context卡片的内容 返回值：
   * @result
   */
  @Before(POST.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:set"})
  public void set() {
    final int id = getParaToInt("id", -1);
    final String title = getPara("title", "");
    final String context = getPara("context", "");
    boolean flage = false;

    if (id >= 0) {
      flage = Card.dao.set(id, title, context);
    }

    if (flage) {
      renderJson(Https.success("修改成功", ""));
    } else {
      renderJson(Https.failure("修改失败", ""));
    }
  }

  /***
   * 改变状态
   *
   * 参数:
   * @id卡片的id
   * @uid点击人的id
   */
  @Before(GET.class)
  @RequiresAuthentication
  @RequiresPermissions({"card:setStatus"})
  public void setStatus() {
    final int id = getParaToInt("id", -1);
    final int uid = getParaToInt("uid", -1);
    boolean flag = false;

    if (id >= 0 && uid >= 0) {
      flag = CardPeopleStatus.dao.setStatus(id, uid);
    }

    if (flag) {
      renderJson(Https.success("已查看", ""));
    } else {
      renderJson(Https.failure("修改状态失败", ""));
    }
  }

  public enum Baby {
    mine, me, all;
  }

}



