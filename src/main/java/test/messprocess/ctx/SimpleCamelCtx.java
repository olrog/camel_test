package test.messprocess.ctx;

import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.PropertyInject;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "test.messprocess")
public class SimpleCamelCtx extends CamelConfiguration {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void setupCamelContext(CamelContext camelContext) throws Exception {
		camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
		camelContext.getShutdownStrategy().setLogInflightExchangesOnTimeout(false);
		camelContext.getRegistry().bind("catalog", dataSource);
		camelContext.addComponent("Queue", getJmsComponent());
//		camelContext.addComponent("Queue", getJmsComponent());
//		camelContext.addComponent("Queue", getJmsComponent());
		
	}

	private JmsComponent getJmsComponent() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"vm://localhost:/?broker.persistent=false&broker.useJmx=false");

		JmsComponent answer = new JmsComponent();
		answer.setConnectionFactory(connectionFactory);
		return answer;
	}
}
