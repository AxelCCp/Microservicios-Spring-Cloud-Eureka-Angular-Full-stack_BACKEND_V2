package microservice.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"microservice.alumnos.commons.models.entity"})		//SE PONEN LOS NOMBRES DE LOS PACKAGES DEL MICROSERVICIO COMMONS, PARA Q PUEDA LEER EL LA CLASE ENTITY Q HAY DENTRO. ESTO ES PQ SE LLAMAN DIFERENTE LOS PACKAGES. EL PRINCIPAL CON EL DEL MODEL ENTITY DE COMMONS.
public class A0MicroserviciosUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(A0MicroserviciosUsuariosApplication.class, args);
	}

}
