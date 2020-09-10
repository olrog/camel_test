package test.messprocess.generic.bus;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import test.messprocess.repos.Cd;
import test.messprocess.utils.SQLFinder;

public class ProcessXmlCamelRoute extends RouteBuilder{

	@PropertyInject("jaxb.package")
    private String jaxbPackage;
	
	private JdbcTemplate jdbcTemplate;
	private SQLFinder queryFinder;

	public ProcessXmlCamelRoute(JdbcTemplate jdbcTemplate, SQLFinder queryFinder) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryFinder = queryFinder;
	}
	
	@Override
	public void configure() throws Exception {
		JaxbDataFormat jaxb = new JaxbDataFormat(jaxbPackage);
		
		from("Queue:queue:inbox_xml")
		.unmarshal(jaxb)
		.process(exchange -> {
				Cd cd = exchange.getIn().getBody(Cd.class);
				KeyHolder holder = new GeneratedKeyHolder();
				jdbcTemplate.update(conn -> {
					PreparedStatement ps = conn.prepareStatement(queryFinder.find("INSERT_CATALOG"),new String[] {"id"});
					ps.setString(1, cd.getTitle());
					ps.setString(2, cd.getArtist());
					ps.setString(3, cd.getCountry());
					ps.setString(4, cd.getCompany());
					ps.setString(5, cd.getPrice());
					ps.setString(6, cd.getYear());
					return ps;
				}, holder);
				
				Map<String, Object> headers = exchange.getIn().getHeaders();
				String allHeaders = prepareHeaders(headers);
				
				Map<String, Object> body = new HashMap<>();
				body.put("cd_id", holder.getKey());
				body.put("headers", allHeaders);
				exchange.getIn().setBody(body);
		})
		.to(queryFinder.find("INSERT_HEADERS"))
		.end();
		
		
		//looker
		from("timer:base?period=6000")
		.to("sql:select * from catalog").log("${body}").log(">>>>>>>>>>>>>>>>>>>>>>>>>")
		.to("sql:select * from headers").log("${body}").log(">>>>>>>>>>>>>>>>>>>>>>>>>\n").end();
		
	}

	private String prepareHeaders(Map<String, Object> headers) {
		String allHeaders = headers.keySet().stream().map(key -> key + "=" + headers.get(key))
	      .collect(Collectors.joining(", ", "{", "}"));

		return allHeaders.substring(0, Math.min(allHeaders.length(), 100));
	}

}
