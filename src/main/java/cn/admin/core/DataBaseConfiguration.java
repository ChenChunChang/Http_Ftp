package cn.admin.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("unused")
@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration implements EnvironmentAware {

	@Autowired
	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub

	}

	@Bean(name = "default", destroyMethod = "close", initMethod = "init")
	@Primary
	public DataSource dataSource() {

		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(env.getProperty("jdbc.url"));
		datasource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		datasource.setUsername(env.getProperty("jdbc.username"));
		datasource.setPassword(env.getProperty("jdbc.password"));
		datasource.setInitialSize(0);
		return datasource;
	} 

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		//sessionFactory.setTypeAliasesPackage("");
		sessionFactory
				.setMapperLocations(new PathMatchingResourcePatternResolver()
						.getResources(env
								.getProperty("mybatis.mapperLocations")));//
		SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
		return sqlSessionFactory;
	}
}
