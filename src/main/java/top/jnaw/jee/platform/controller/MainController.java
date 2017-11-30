package top.jnaw.jee.platform.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Log;
import top.jnaw.jee.utils.Shiros;

public class MainController extends Controller {

  public void index() {
    redirect("/main");
  }

  public void main() {
    boolean result = Shiros.addUser("liuxiaopeng", "admin");
    Log.d("add admin: " + result);

    result = Shiros.setPwd("admin", "123456");
    Log.d("set admin pwd: " + result);


    renderHtml("Main");
  }
/*
    result = Shiros.setRoles("admin", "admin", "user");
    Log.d("set admin roles: " + result);*/

 /*   result = Shiros.setPermissions("admin", "user:create", "user:delete");
    Log.d("set admin perms: " + result);

    result = Shiros.setPermissions("user", "content:view");
    Log.d("set user perms: " + result);*/

  public void login() {
    renderJson(Https.failure("没有权限",""));
  }

  public void check() {
    String user = getPara("user");
    String pwd = getPara("pwd");

    if (Shiros.login(user, pwd)) {
      redirect(PropKit.get("shiro_redirect_success_url"));
    } else {
      redirect(PropKit.get("shiro_redirect_unauthorized_url"));
    }

    Log.i("is authc: " + Shiros.isAuthc());
  }

  @RequiresAuthentication
  @RequiresRoles(value = {"admin", "user", "test"}, logical = Logical.OR)
  @RequiresPermissions({"user:create", "user:delete"})
  public void admin() {
    String r1 = "user";
    String r2 = "admin";
    String r3 = "foo";

    List<String> checker = new ArrayList<>();
    checker.add(r1);
    checker.add(r2);
    checker.add(r3);

    boolean results[] = Shiros.hasRoles(checker);
    for (boolean result : results) {
      Log.d("check r: " + result);
    }

    results = Shiros.hasRoles(r1, r2, r3);
    for (boolean result : results) {
      Log.i("check r again: " + result);
    }

    r1 = "article:add";
    r2 = "article:post";
    r3 = "article:del";
//    r3 = "article:get";

    List<String> todo = new ArrayList<>();
    todo.add(r1);
    todo.add(r2);
    todo.add(r3);

    Shiros.setRoles("admin", "test");
    Shiros.setPermissions("test", todo);
    Shiros.setPermissions("test", "pc:add", "pc:del");

    // [Neo] admin pc:add
    boolean array[] = Shiros.isPermitted("pc:add", "pc:del");
    for (boolean e : array) {

    }

    Shiros.hasRoles("test");

    results = Shiros.isPermitted(checker);
    for (boolean result : results) {
      Log.d("check p: " + result);
    }

    results = Shiros.isPermitted(r1, r2, r3);
    for (boolean result : results) {
      Log.i("check p again: " + result);
    }

    Log.e("admin user: " + Shiros.getUser());
    renderHtml("admin");
  }

  public void foo() {
    Log.e("foo user: " + Shiros.getUser());

    List<String> result = Shiros.getRoles("admin");
    Log.i("admin's roles = " + result.toString());

    Map<String, List<String>> authz = new HashMap<>();

    ArrayList<String> a1 = new ArrayList<>();
    a1.add("A1");
    a1.add("A2");
    authz.put("dummy", a1);

    Shiros.setUser("t", "pp", authz);

    Shiros.setPermissions("dummy", a1);
    result = Shiros.getPermissions("dummy");
    Log.i("dummy's perms = " + result.toString());

    Shiros.logout();

    renderHtml("foo");
  }

}
