package microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class A0MicroserviciosGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(A0MicroserviciosGatewayApplication.class, args);
	}

}
