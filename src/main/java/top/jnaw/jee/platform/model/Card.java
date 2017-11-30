package top.jnaw.jee.platform.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import top.jnaw.jee.platform.model.base.BaseCard;

import java.util.List;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Log;
import top.jnaw.jee.utils.Shiros;

/**
 * Created by lxh on 2017/11/23.
 */
public class Card extends BaseCard<Card> {

  public static final Card dao = new Card();

  /**
   * 查询我发布的卡片
   * @param title
   * @param startTime
   * @param endTime
   * @param page
   * @param pageSize
   * @return
   */
  public Page<Record> mine(String title, String startTime, String endTime, int page, int pageSize) {
    String select = "SELECT--\n"
      + "  c.id,\n"
      + "  c.title,\n"
      + "  c.context,\n"
      + "  c.create_time,\n"
      + "  ct.name as type_name,"
      + "  u.name as create_name " ;
    String sqlExceptSelect = " FROM card c LEFT JOIN card_type ct ON c.type_id = ct.id left  join users u on u.id=c.create_id \n"
      + "WHERE c.create_id = ? AND c.title LIKE '%"+title+"%' AND ct.remove = 1";
    if(StrKit.notBlank(startTime)&&StrKit.notBlank(endTime)){
      sqlExceptSelect +=" AND c.create_time > "+startTime+" AND c.create_time < "+endTime+"";
    }
    return Db
      .paginate(page, pageSize, select, sqlExceptSelect, Users.getUserInfo(Shiros.getUser()).getInt("id"));
  }

  /**
   * 查询与我相关的卡片
   */
  public Page<Record> me(String title, String startTime, String endTime, int page, int pageSize) {
    String select = "SELECT\n"
      + "  c.id,\n"
      + "  c.title,\n"
      + "  c.context,\n"
      + "  c.create_time,\n"
      + "  ct.name as type_name ,"
      + "  u.name as create_name ";
    String sqlExceptSelect = " FROM card c LEFT JOIN card_type ct ON c.type_id = ct.id left  join users u on u.id=c.create_id \n"
      + "  LEFT JOIN card_people_status cps ON c.id = cps.cid\n"
      + "WHERE c.title LIKE '%"+title+"%' AND cps.uid = ? and ct.remove = 1 ";
    if(StrKit.notBlank(startTime)&&StrKit.notBlank(endTime)){
      sqlExceptSelect +=" AND c.create_time > "+startTime+" AND c.create_time < "+endTime+"";
    }

    return Db
      .paginate(page, pageSize, select, sqlExceptSelect, Users.getUserInfo(Shiros.getUser()).getInt("id"));
  }

  /**
   * 查询全部的卡片
   * @param title
   * @param startTime
   * @param endTime
   * @param page
   * @param pageSize
   * @return
   */
  public Page<Record> all(String title, String startTime, String endTime, int page, int pageSize) {
    String select = "SELECT\n"
      + "  c.id,\n"
      + "  c.title,\n"
      + "  c.context,\n"
      + "  c.create_time,\n"
      + "  ct.name as type_name, "
      + "  u.name as create_name ";

    String sqlExceptSelect = " FROM card c LEFT JOIN card_type ct ON "
      + "c.type_id = ct.id left  join "
      + " users u on u.id=c.create_id \n"
      + "WHERE  1=1 AND "
      + "c.title LIKE '%"+title+"%' "
      + "AND ct.remove = 1";
    if(StrKit.notBlank(startTime)&&StrKit.notBlank(endTime)){
      sqlExceptSelect +=" AND c.create_time > "+startTime+" AND c.create_time < "+endTime+"";
    }

    return Db.paginate(page, pageSize, select, sqlExceptSelect);
  }

  /**
   * 过个人查询卡片的列表
   * @param title
   * @param startTime
   * @param endTime
   * @param page
   * @param pageSize
   * @param create_id
   * @return
   */
  public Page<Record> findCardListByPeople(String title, String startTime, String endTime, int page,
    int pageSize, int create_id) {
    String select = "SELECT\n"
      + "  c.id,\n"
      + "  c.title,\n"
      + "  c.context,\n"
      + "  c.create_time,\n"
      + "  ct.name as type_name,  "
      + "  u.name as create_name ";

    String sqlExceptSelect = "FROM card c LEFT JOIN card_type ct "
      + "ON c.type_id = ct.id left  join users u "
      + "on u.id=c.create_id \n"
      + "WHERE c.title LIKE '%"+title+"%'  AND  "
      + "      c.create_id = ?";
    if(StrKit.notBlank(startTime)&&StrKit.notBlank(endTime)){
      sqlExceptSelect +=" AND c.create_time > '"+startTime+"' AND c.create_time < '"+endTime+"'";
    }
    return Db.paginate(page, pageSize, select, sqlExceptSelect, create_id);
  }

  /**
   * 精简查询卡片的详情
   * @param id
   * @return
   */
  public Card findCardById(int id) {
    return Card.dao.findFirst("SELECT " +
      "  c.id, " +
      "  c.title, " +
      "  c.context, " +
      "  c.create_time, " +
      "  u.name AS create_name " +
      "  FROM card c "+
      "  LEFT JOIN users u "+
      "  ON c.create_id = u.id " +
      "  WHERE c.id = ?", id);
  }

  /**
   * 添加卡片
   * @param title
   * @param context
   * @param type_id
   * @return
   */
  public int add(String title, String context, int type_id) {
    Card c = new Card();
    c.setTitle(title);
    c.setContext(context);
    Log.d(Shiros.getUser());
    c.setCreateId(Users.getUserInfo(Shiros.getUser()).getInt("id"));
    c.setCreateTime(Dates.nowNormalTime());
    c.setRemove(1);
    c.setTypeId(type_id);
    c.save();
    return c.getId();
  }

  /**
   * 删除卡片 即修改状态
   * @param id
   * @return
   */
  public boolean del(int id){
    Card c = Card.dao.findById(id);
    c.setRemove(0);
    return c.update();
  }
  public boolean set(int id,String title,String context){
    Card c = Card.dao.findById(id);
    if(StrKit.notBlank(context)) {
      c.setContext(context);
    }
    if(StrKit.notBlank(title)) {
      c.setTitle(title);
    }
    return c.update();
  }



}
