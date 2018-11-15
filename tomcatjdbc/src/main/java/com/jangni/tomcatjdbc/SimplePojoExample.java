package com.jangni.tomcatjdbc;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/15 22:37
 **/
public class SimplePojoExample extends JdbcProsLoad {

    public void TestConnect(){
        Properties dbProperties = init("jdbc.properties");
//        dbProperties.setProperty("url", "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8");
//        dbProperties.setProperty("username", "root");
//        dbProperties.setProperty("password", "root");
//        dbProperties.setProperty("", "");

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("root");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(1000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        DataSourceFactory dataSourceFactory = new DataSourceFactory();

        Connection con = null;
        try {
           con = dataSourceFactory.createDataSource(dbProperties).getConnection();
            con = datasource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            int cnt = 1;
            while (rs.next()) {
                System.out.println((cnt++) + ". Host:" + rs.getString("Host") +
                        " User:" + rs.getString("User") + " Password:" + rs.getString("Password"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public static void main(String[] args) {

    new SimplePojoExample().TestConnect();

    }
}
