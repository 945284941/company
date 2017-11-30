package top.jnaw.jee.platform;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.template.Engine;
import top.jnaw.jee.plugin.shiro.ShiroInterceptor;
import top.jnaw.jee.platform.model._MappingKit;
import top.jnaw.jee.platform.routes.MainRoutes;
import top.jnaw.jee.utils.Cache;
import top.jnaw.jee.utils.Generator;
import top.jnaw.jee.utils.Log;
import top.jnaw.jee.utils.nim.NIMConfig;
import top.jnaw.jee.utils.nim.User;

/**
 * Created by neo on 17-10-12.
 */
public class MainConfig extends JFinalConfig {

  private Routes globalRoutes;

  @Override
  public void configConstant(Constants me) {
    // [Neo] init config file and log
    PropKit.use("product.properties");
   // PropKit.use("developer.properties");

    Log.init(PropKit.get("project_name"));
    //nim
    NIMConfig.init(PropKit.get("nim_app_key"), PropKit.get("nim_app_secret"));

    me.setDevMode(PropKit.getBoolean("dev_mode"));
  }

  @Override
  public void configRoute(Routes me) {
    // [Neo] add more routes
    me.add(new MainRoutes());

    // [Neo] copy into global
    globalRoutes = me;
  }

  @Override
  public void configEngine(Engine me) {

  }

  @Override
  public void configPlugin(Plugins me) {
    IDataSourceProvider provider = Generator.dataSourcePlugin();
    ActiveRecordPlugin arp = new ActiveRecordPlugin(provider);
    arp.setShowSql(PropKit.getBoolean("show_sql"));

    // TODO [Neo] after test case has run, uncomment this line
    _MappingKit.mapping(arp);

    me.add(arp);
    me.add(Generator.shiroPlugin(provider.getDataSource(), globalRoutes));
    me.add(Generator.cachePlugin());
  }

  @Override
  public void configInterceptor(Interceptors me) {
    me.add(new ShiroInterceptor());
  }

  @Override
  public void configHandler(Handlers me) {
    me.add(Generator.statHandler(PropKit.get("druid_stat_view")));

    // [Neo] check redis
    if (Cache.init()) {
      Log.i("redis is online");
    } else {
      Log.w("redis is offline");
    }
  }
}
