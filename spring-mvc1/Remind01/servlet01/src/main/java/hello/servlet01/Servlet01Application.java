package hello.servlet01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Servlet01Application {

	public static void main(String[] args) {
		SpringApplication.run(Servlet01Application.class, args);
	}

}
