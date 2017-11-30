package top.jnaw.jee.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.jnaw.jee.platform.model.Department;
import top.jnaw.jee.platform.model.Users;
import top.jnaw.jee.utils.Https;
import top.jnaw.jee.utils.Log;
import top.jnaw.jee.utils.Models;
import top.jnaw.jee.utils.Shiros;

public class  DepartmentController extends Controller {


    /**
     * 传id获取 id与id的子部门
     * type  mine 我所在的部门
     * type all 全部的部门
     */
  @RequiresAuthentication
  @RequiresPermissions({"department:get"})
  public void get() {
    final int id = getParaToInt("id", -1);
    final String type = getPara("type", "");

    String json = null;

    String username = Shiros.getUser();
    List<Department> list = Department.getList();
    Department department = Department.dao.findById(id);

    switch (type) {
      case "mine":
        JSONArray array = new JSONArray();
        List<Department> mine = Users.getDepartments(username);

        Log.d("username = " + username);
        Log.d(mine.toString());

        if (null != mine && mine.size() > 0) {
          for (Department d : mine) {
            Log.i(d.toJson());
            array.add(Department.getParentLine(d, list, null));
          }

          json = array.toJSONString();
        }

        break;

      case "all":
        if (null != list) {
          JSONArray all = Department.getChildren(list, 0, null);
          json = all.toJSONString();
        }

        break;

      default:
        if (null != department) {
          JSONArray home = Department
              .getChildren(list, department.getPid(), null);
          int size = home.size();
          JSONObject self = null;

          for (int i = 0; i < size; ++i) {
            if (department.getId() == home.getJSONObject(i).getIntValue("id")) {
              self = home.getJSONObject(i);
            }
          }

          json = self.toJSONString();
        }
        break;
    }

    if (null == json) {
//      renderJson(404);
      renderJson(Https.failure("查询失败",""));
    } else {
      renderJson(Https.success("查询成功",JSONObject.parse(json)));
    }
  }

    /**
     * 添加一个部门
     */
    @RequiresPermissions({"department:add"})
  public void add() {
    final String name = getPara("name", "");
    final int pid = getParaToInt("pid", -1);

    boolean result = false;

    if (StrKit.notBlank(name) && pid >= 0) {
      Department department = new Department();
      department.setName(name);
      department.setPid(pid);
      result = department.save();
    }
    if (result){
      renderJson(Https.success("添加成功",""));
    }else {
      renderJson(Https.failure("添加失败",""));
    }
  }

  /**
   * 获取当前id的父级的部门
   */
  @RequiresPermissions({"department:top"})
  public void top(){

    final  int id=getParaToInt("id",-1);
    JSONArray array = new JSONArray();
    if (id >= 0) {
      Department department=Department.dao.findById(id);
      List<Department> list = Department.getList();
     array.add(Department.getParentLine(department,list,null));
    }
    String json=array.toJSONString();

    renderJson(Https.success("",JSONObject.parse(json)));

  }
  /*8
  s   删除一个部门
   */
  @RequiresPermissions({"department:rm"})
  public void rm() {
    final int id = getParaToInt("id", -1);

    boolean result = false;

    if (id >= 0) {
      Department department = Department.dao.findById(id);

      if (null != department) {
        result = department.delete();
      }
    }
    if (result){
      renderJson(Https.success("删除成功",""));
    }else {
      renderJson(Https.failure("删除失败",""));
    }
  }

  @RequiresPermissions({"department:set"})
  public void set() {
    final int id = getParaToInt("id", -1);
    final String name = getPara("name", "");
    final int pid = getParaToInt("pid", -1);

    boolean result = false;

    if (id >= 0) {
      Department department = Department.dao.findById(id);

      if (null != department) {
        if (StrKit.notBlank(name)) {
          department.setName(name);
        }

        if (pid >= 0) {
          department.setPid(pid);
        }

        result = department.update();
      }
    }

    if (result){
      renderJson(Https.success("设置成功",""));
    }else {
      renderJson(Https.failure("设置失败",""));
    }
  }

  /**
   * 获取全部的部门
   * type :all
   */
  @RequiresPermissions({"department:list"})
  public void list() {
    final int page = getParaToInt("page", 1);
    final int size = getParaToInt("size", PropKit.getInt("paginate_size", 10));

    final String select = "SELECT *";
    final String except = "FROM department ORDER BY id";

    Page<Department> list = Department.dao.paginate(page, size, select, except);
    renderJson(Https.success("",list));
  }


}
