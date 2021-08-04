package com.ydw.admin;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class SimpleMp {
	
	public static void main(String[] args) {
		SimpleMp SimpleMp = new SimpleMp();
		SimpleMp.generateCode();
	}
	
    public void generateCode() {
        //指定包名
        String packageName = "com.ydw.admin";
        //指定生成的表名
        String[] tableNames = new String[]{"ip_black"};
        generateByTables(packageName, tableNames);
    }
 
    /**
     * 根据表自动生成
     * @author xulh
     * @date 2019年12月3日
     * @param packageName
     * @param tableNames void
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
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     * @author xulh
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
     * @param packageName 父路径包名
     * @return PackageConfig 包名配置
     * @author xulh
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
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     * @author xulh
     */
    private GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setDateType(DateType.ONLY_DATE);
        gc.setOutputDir(getOutputDir("ccmp"));
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("xulh");// 作者
 
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        return gc;
    }
 
    /**
     * 返回项目路径
     * @param projectName 项目名
     * @return 项目路径
     * @author xulh
     */
    private String getOutputDir(String projectName) {
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        int index = path.indexOf(projectName);
//        return path.substring(1, index) + projectName + "/src/main/java/";
        return "D:\\tttt\\";
    }
 
    /**
     * 策略配置
     * @param tableNames 表名
     * @return StrategyConfig
     * @author xulh
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {
        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("tb_")
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames);
    }
 
    /**
     * 配置数据源
     * @return 数据源配置 DataSourceConfig
     * @author xulh
     */
    private DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://121.36.97.240:3306/cgp_saas?tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false";
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("Yidianwan!23")
                .setDriverName("com.mysql.cj.jdbc.Driver");
    }
 
}