package top.jnaw.jee.platform.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;
import top.jnaw.jee.platform.model.base.BaseCardType;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Shiros;

/**
 * Created by lxh on 2017/11/23.
 */
public class CardType extends BaseCardType<CardType> {

  public static final CardType dao = new CardType();

  /**
   * 修改卡片类型的基本信息
   */
  public boolean set(int id, String name) {
    CardType cardType = CardType.dao.findById(id);
    if (StrKit.notBlank(name)) {
      cardType.setName(name);
    }
    return cardType.update();
  }

  /**
   * 删除卡片的类型||改变状态
   * @param id
   * @return
   */
  public boolean rm(int id){
    CardType cardType = CardType.dao.findById(id);
    cardType.setRemove(0);
    return  cardType.update();
  }

  /**
   * 分页查询卡片的类型
   * @param page
   * @param pageSize
   * @param name
   * @return
   */
  public Page<Record> getCardTypes(int page,int pageSize,String name){
    String select="SELECT\n"
        + "  id,\n"
        + "  name";
    String sqlExceptSelect=" FROM card_type\n"
        + " WHERE remove != 0 AND name LIKE '%"+name+"%'";
    return Db.paginate(page,pageSize,select,sqlExceptSelect);
  }

  /**
   * 查询卡片列表不分页
   * @param name
   * @return
   */
  public List<CardType> getCardType(String name){
    String select="SELECT\n"
        + "  id,\n"
        + "  name\n"
        + " FROM card_type\n"
        + " WHERE remove != 0 AND name LIKE '%"+name+"%'";
    return CardType.dao.find(select);
  }

  public boolean add(String name){
    CardType ct = new CardType();
    ct.setName(name);
    ct.setCreateId(Users.getUserInfo(Shiros.getUser()).getInt("id"));
    ct.setCreateTime(Dates.nowNormalTime());
    ct.setRemove(1);
    return ct.save();
  }
}
