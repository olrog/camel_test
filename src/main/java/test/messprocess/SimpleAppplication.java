package test.messprocess;

import org.apache.camel.spring.javaconfig.Main;

import lombok.SneakyThrows;
import test.messprocess.ctx.SimpleCamelCtx;

public class SimpleAppplication {
	@SneakyThrows
	public static void main(String[] args) {
		Main main = new Main();
		main.setConfigClass(SimpleCamelCtx.class);
		main.run();
	}
}
