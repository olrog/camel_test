package test.messprocess.ctx;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import test.messprocess.generic.bus.InputCamelRoute;
import test.messprocess.generic.bus.ProcessTxtCamelRoute;
import test.messprocess.generic.bus.ProcessXmlCamelRoute;
import test.messprocess.utils.SQLFinder;

@Configuration
public class SimpleSpringBeanConfiguration {
	
	@Autowired
	SQLFinder queryFinder;

	@Bean
	InputCamelRoute inputCamelRoute() {
		return new InputCamelRoute();
	}
	
	@Bean
	ProcessXmlCamelRoute processXmlCamelRoute() {
		return new ProcessXmlCamelRoute(jdbcTemplate(), queryFinder);
	}
	
	@Bean
	ProcessTxtCamelRoute processTxtCamelRoute() {
		return new ProcessTxtCamelRoute();
	}
	
	@Bean
	JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(h2DataSource());
	} 
	
	@Bean(name = "dataSource")
	DataSource h2DataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("db/chema.sql")
//				.addScript("db/test_data.sql")
				.build();
	}
}
