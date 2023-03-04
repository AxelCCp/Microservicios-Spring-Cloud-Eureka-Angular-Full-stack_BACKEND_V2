package microservice.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"microservice.commons.examenes.model.entity"})    // PARA PODER LEER LAS CLASES Q ESTÁN EN ESE PACKAGE DEL MICROSERVICIO COMMONS.
public class A0MicroserviciosExamenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(A0MicroserviciosExamenesApplication.class, args);
	}

}
