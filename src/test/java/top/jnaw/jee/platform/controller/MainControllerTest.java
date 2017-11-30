package top.jnaw.jee.platform.controller;

import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import top.jnaw.jee.platform.model.Department;
import top.jnaw.jee.utils.Log;

public class MainControllerTest {

  @Before
  public void setUp() throws Exception {
    Log.init("test");
  }

  @Test
  public void foo() throws Exception {
    Department d = null;
    List<Department> list = new ArrayList();

    d = new Department();
    d.setId(1);
    d.setName("a.org");
    d.setPid(0);
    list.add(d);

    d = new Department();
    d.setId(2);
    d.setName("b.org");
    d.setPid(0);
    list.add(d);

    d = new Department();
    d.setId(3);
    d.setName("a.a");
    d.setPid(1);
    list.add(d);

    d = new Department();
    d.setId(4);
    d.setName("a.b");
    d.setPid(1);
    list.add(d);

    d = new Department();
    d.setId(5);
    d.setName("b.a");
    d.setPid(2);
    list.add(d);

    d = new Department();
    d.setId(6);
    d.setName("b.a.ii");
    d.setPid(5);
    list.add(d);

//    JSONObject line = getParentLine(d, list, null);
//    Log.i(line.toJSONString());

    JSONArray arr = Department.getChildren(list, 0, null);
    Log.i(arr.toString());

  }

}