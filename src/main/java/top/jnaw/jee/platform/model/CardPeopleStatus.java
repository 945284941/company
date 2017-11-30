package top.jnaw.jee.platform.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import top.jnaw.jee.platform.model.base.BaseCardPeopleStatus;

/**
 * Created by lxh on 2017/11/23.
 */
public class CardPeopleStatus extends BaseCardPeopleStatus<CardPeopleStatus> {

  public static final CardPeopleStatus dao = new CardPeopleStatus();

  /**
   * 读取卡片已读未读的人员列表
   * @param id
   * @param page
   * @param pageSize
   * @return
   */
  public Page<Record> getCardPeoples(int id, int page, int pageSize) {
    String select = "SELECT\n"
        + "  cps.id,\n"
        + "  u.name,\n"
        + "  cps.status,\n"
        + "  cps.uid,\n"
        + "  cps.cid";
    String sqlExceptSelect = " FROM card_people_status cps LEFT JOIN users u ON cps.uid = u.id\n"
        + "WHERE cps.cid = ?";
    return Db.paginate(page, pageSize, select, sqlExceptSelect, id);
  }

  /**
   * 添加卡片与人之间的联系 chuan rende数组
   * @param cid
   * @param newLooker
   * @return
   */
  public boolean add(int cid,String[] newLooker){
    List<Record> list=new ArrayList<Record>();
    for(int i=0;i<newLooker.length;i++) {
      Record cps = new Record();
      cps.set("cid",cid);
      cps.set("status",1);
      cps.set("uid",Integer.parseInt(newLooker[i]));
      list.add(cps);
    }
    return Db.tx(new IAtom() {
      @Override
      public boolean run() throws SQLException {
       int[] count=Db.batchSave("card_people_status",list,500);
        boolean flag=true;
       for (int i=0;i<count.length;i++){
         if(count[i]==0){
           flag=false;
         }
       }
        return flag;
      }
    });
  }

  /**
   * 添加 根据 群
   * @param cid
   * @param list
   * @return
   */
  public boolean addl(int cid,List<UserTeam> list){
    List<Record> newlist=new ArrayList<Record>();
    for(int i=0;i<list.size();i++) {
      Record cps = new Record();
      cps.set("cid",cid);
      cps.set("status",1);
      cps.set("uid",list.get(i).getInt("id"));
      newlist.add(cps);
    }
    return Db.tx(new IAtom() {
      @Override
      public boolean run() throws SQLException {
        int[] count=Db.batchSave("card_people_status",newlist,500);
        boolean flag=true;
        for (int i=0;i<count.length;i++){
          if(count[i]==0){
            flag=false;
          }
        }
        return flag;
      }
    });
  }

  /**
   * 修改人与卡片的状态
   * @param id
   * @param uid
   * @return
   */
  public boolean setStatus(int id,int uid){
    CardPeopleStatus cps = CardPeopleStatus.dao
        .findFirst("select * from card_people_status where cid=? and uid=?",id,uid);
    cps.setStatus(1);
    return cps.update();
  }
}
