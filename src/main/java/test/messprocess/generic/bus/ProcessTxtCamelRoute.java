package test.messprocess.generic.bus;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ProcessTxtCamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("Queue:queue:inbox_txt")
		.log(LoggingLevel.INFO, "file","${body}");
	}

}
