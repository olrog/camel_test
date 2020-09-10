package test.messprocess.generic.bus;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.stax.StAXBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import test.messprocess.repos.Cd;

public class InputCamelRoute extends RouteBuilder{

	@PropertyInject("jaxb.package")
    private String jaxbPackage;
	
	@Override
	public void configure() throws Exception {
		JaxbDataFormat jaxb = new JaxbDataFormat(jaxbPackage);
		
		from("file:{{folder.source}}")
			.choice()
//			.when(header("CamelFile2Name").endsWith(".txt"))
			.when().simple("${file:ext} == 'txt'")
        	.to("Queue:queue:inbox_txt")
//        	.when(header("CamelFileName").endsWith(".xml"))
    		.when().simple("${file:ext} == 'xml'")
    		.marshal(jaxb)
    		.split(StAXBuilder.stax(Cd.class)).streaming()
    		.to("Queue:queue:inbox_xml")
    		.endChoice()
    		.otherwise()
    		.throwException(new Exception("Wrong File Format"))
        	.to("Queue:queue:inbox_err")
        	.end();
	}
	
}
