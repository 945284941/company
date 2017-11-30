package top.jnaw.jee.platform;

import com.jfinal.kit.PropKit;
import org.junit.Before;
import org.junit.Test;
import top.jnaw.jee.utils.Generator;

public class MainConfigTest {

  @Before
  public void setUp() throws Exception {
    PropKit.use("developer.properties");
  }

  @Test
  public void configPlugin() throws Exception {
    Generator.model(Generator.dataSourcePlugin().getDataSource());
  }

}