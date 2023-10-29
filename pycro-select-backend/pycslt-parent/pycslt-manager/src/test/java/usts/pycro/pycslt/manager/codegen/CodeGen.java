package usts.pycro.pycslt.manager.codegen;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 13:54
 */
public class CodeGen {

    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "12345678";

    public static void main(String[] args) {
        // 配置数据源
        DataSource dataSource = createDataSourceConfig("db_pycslt");

        // 创建配置内容
        GlobalConfig globalConfig = createGlobalConfig();

        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
    }

    public static DataSource createDataSourceConfig(String dbName) {
        return createDataSourceConfig(dbName, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createDataSourceConfig(String dbName, String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(String.format("jdbc:mysql://192.168.73.128:3306/%s?characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true", dbName));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


    public static GlobalConfig createGlobalConfig() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();


        // 注释配置
        globalConfig.getJavadocConfig()
                .setAuthor("Pycro");

        // 设置根包
        globalConfig.getPackageConfig()
                .setSourceDir("E:\\Project\\尚品甄选\\workspace\\pycro-select-backend\\pycslt-parent\\pycslt-manager\\src\\main\\java")
                .setBasePackage("usts.pycro.pycslt.manager");

        // 设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setGenerateTable("category");


        // 设置生成 entity 并启用 Lombok
        // globalConfig.enableEntity()
        //         .setWithLombok(true);

        // 设置生成 mapper
        globalConfig.enableMapper()
                .setMapperAnnotation(true);

        // 设置生成service
        globalConfig.enableService();

        // 设置生成serviceImpl
        globalConfig.enableServiceImpl();

        // 设置生成Controller
        globalConfig.enableController();

        // 可以单独配置某个列
        /*ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.getStrategyConfig()
                .setColumnConfig("tb_account", columnConfig);*/

        return globalConfig;
    }
}

