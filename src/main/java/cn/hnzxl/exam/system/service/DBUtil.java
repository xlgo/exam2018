package cn.hnzxl.exam.system.service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据库
 * @author ZXL
 *
 */
@Component
public class DBUtil {
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void updateDb(){
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setBaselineOnMigrate(true);
		flyway.migrate();
	}
}
