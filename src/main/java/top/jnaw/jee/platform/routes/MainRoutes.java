package top.jnaw.jee.platform.routes;

import com.jfinal.config.Routes;
import top.jnaw.jee.platform.controller.CommentController;
import top.jnaw.jee.platform.controller.DepartmentController;
import top.jnaw.jee.platform.controller.NoticeController;
import top.jnaw.jee.platform.controller.NoticeTypeController;
import top.jnaw.jee.platform.controller.OpinionController;
import top.jnaw.jee.platform.controller.UserController;
import top.jnaw.jee.platform.controller.MainController;
import top.jnaw.jee.platform.controller.*;

public class MainRoutes extends Routes {

  @Override
  public void config() {
    add("/", MainController.class);
    add("/department", DepartmentController.class);
    add("/user", UserController.class);
    add("/opinion", OpinionController.class);
    add("/notice", NoticeController.class);
    add("/notice_type", NoticeTypeController.class);
    add("/comment", CommentController.class);
    add("/card",CardController.class);
    add("/cardtype",CardTypeContrpller.class);
    add("/team",TeamController.class);
    add("/msg",MsgController.class);
  }
}
