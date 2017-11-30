package top.jnaw.jee.platform.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.Opinion;
import top.jnaw.jee.utils.Dates;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Shiros;


public class OpinionController extends Controller {

  /**
   * 提交意见
   */
  @RequiresAuthentication
  @RequiresPermissions({"opinion:add"})
  public void addOpinion() {
    final String yijian = getPara("yijian", "");
    String username = Shiros.getUser();
    String date = Dates.nowNormalTime();

    if (StrKit.notBlank(yijian)) {
      Opinion op = new Opinion();
      op.setDate(date);
      op.setUsername(username);
      op.setYijian(yijian);

      if (op.save()) {
        renderJson(Https.success("提交成功,感谢您的意见反馈", ""));
      } else {
        renderJson(Https.failure("提交失败", ""));
      }
    } else {
      renderJson(Https.failure("提交失败", ""));
    }
  }
}
