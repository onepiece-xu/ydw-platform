package com.ydw.open;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleMp {
	public static void main(String[] args) {
		SimpleMp SimpleMp = new SimpleMp();
		SimpleMp.generateCode();
	}
//	@Test
    public void generateCode() {
        //指定包名
        String packageName = "com.ydw.platform";
        //指定生成的表名


        String[] tableNames = new String[]{"sync_app"};


        generateByTables(packageName, tableNames);
    }
 
    /**
     * 根据表自动生成
     *
     * @param serviceNameStartWithI 默认为false
     * @param packageName           包名
     * @param tableNames            表名
     * @author Terry
     */
    private void generateByTables(String packageName, String... tableNames) {
        //配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
        //全局变量配置
        GlobalConfig globalConfig = getGlobalConfig();
        //包名配置
        PackageConfig packageConfig = getPackageConfig(packageName);
        //自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig);
    }
 
    /**
     * 集成
     *
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     * @author Terry
     */
    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }
 
    /**
     * 设置包名
     *
     * @param packageName 父路径包名
     * @return PackageConfig 包名配置
     * @author Terry
     */
    private PackageConfig getPackageConfig(String packageName) {
        return new PackageConfig()
                .setParent(packageName)
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("model.db")
                .setXml("mapper")
                .setMapper("dao");
    }
 
    /**
     * 全局配置
     *
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     * @author Terry
     */
    private GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(getOutputDir("cmpWorkspace/ccmp-service/ccmp-user"));
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("heao");// 作者
 
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        return gc;
    }
 
    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     * @author Terry
     */
    private String getOutputDir(String projectName) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        int index = path.indexOf(projectName);
  //      return path.substring(1, index) + projectName + "/src/main/java/";
//        return "F:\\";
        return "D:\\tempcode\\net_bar_info";
    }
 
    /**
     * 策略配置
     *
     * @param tableNames 表名
     * @return StrategyConfig
     * @author Terry
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {
        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                // 表名、字段名、是否使用下划线命名（默认 false）

                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames);
    }
 
    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     * @author Terry
     */
    private DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://121.36.97.240:3306/cgp_paas?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8";
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("Yidianwan!23")
                .setDriverName("com.mysql.cj.jdbc.Driver");
    }
 
}