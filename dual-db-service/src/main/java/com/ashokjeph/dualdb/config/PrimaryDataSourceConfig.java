package com.ashokjeph.dualdb.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.ashokjeph.dualdb.repository.primary", // your repository package
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Primary
    @Bean(name = "primaryDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("squid-dev-server.database.windows.net");
        ds.setPortNumber(1433);
        ds.setDatabaseName("squid-dev-database");
        ds.setEncrypt("true");  // Use string "true" instead of boolean
        ds.setHostNameInCertificate("*.database.windows.net");
        ds.setLoginTimeout(30);

        // Use Active Directory Interactive authentication as in your URL
        ds.setAuthentication("ActiveDirectoryInteractive");

        return ds;
    }

//    @Primary
//    @Bean(name = "primaryDataSource")
//    public DataSource primaryDataSource() {
//        try {
//            String jdbcUrl = "jdbc:sqlserver://squid-dev-server.database.windows.net:1433;" +
//                    "database=squid-dev-database;" +
//                    "encrypt=true;" +
//                    "hostNameInCertificate=*.database.windows.net;" +
//                    "loginTimeout=30;";
//
//            Connection connection = AzureMSSQLDataSourceUtil.createAzureSQLConnection(jdbcUrl);
//            HikariDataSource hikariDataSource = new HikariDataSource();
//            hikariDataSource.setJdbcUrl(jdbcUrl);
//            hikariDataSource.setUsername(""); // Leave blank since we're using access token
//            hikariDataSource.setPassword(""); // Leave blank
//            hikariDataSource.setConnectionTestQuery("SELECT 1");
//
//            // Provide a custom Connection using the token
//            hikariDataSource.setDataSource(new DataSource() {
//                @Override
//                public Connection getConnection() throws SQLException {
//                    try {
//                        return AzureMSSQLDataSourceUtil.createAzureSQLConnection(jdbcUrl);
//                    } catch (Exception e) {
//                        throw new SQLException("Failed to obtain Azure token-based connection", e);
//                    }
//                }
//
//                @Override
//                public Connection getConnection(String username, String password) throws SQLException {
//                    return getConnection();
//                }
//
//                @Override
//                public PrintWriter getLogWriter() throws SQLException {
//                    return null;
//                }
//
//                @Override
//                public void setLogWriter(PrintWriter out) throws SQLException {
//                    // no-op
//                }
//
//                @Override
//                public void setLoginTimeout(int seconds) throws SQLException {
//                    // no-op
//                }
//
//                @Override
//                public int getLoginTimeout() throws SQLException {
//                    return 30;
//                }
//
//                @Override
//                public Logger getParentLogger() throws SQLFeatureNotSupportedException {
//                    throw new SQLFeatureNotSupportedException("getParentLogger not supported");
//                }
//
//                @Override
//                public <T> T unwrap(Class<T> iface) throws SQLException {
//                    throw new SQLException("unwrap not supported");
//                }
//
//                @Override
//                public boolean isWrapperFor(Class<?> iface) throws SQLException {
//                    return false;
//                }
//            });
//
//
//            return hikariDataSource;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error configuring Azure SQL DataSource", e);
//        }
//    }


    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("primaryDataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.default_schema", "dbo");
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        return builder
                .dataSource(dataSource)
                .packages("com.ashokjeph.dualdb.entity.primary")
                .persistenceUnit("primaryPU")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

