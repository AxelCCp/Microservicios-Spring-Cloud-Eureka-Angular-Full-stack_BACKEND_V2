package microservice.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})			// 95 : SE QUITA LA CONFIGURACIÓN  AUTOMÁTOCA DE JPA, YA Q EL PROYECTO ESTÁ CON MONGO. ESTO PASA PQ DENTRO DE LAS DEPENDENCIA DE LOS MICROSERVICIOS COMMONS QUE ESTAN EN EL POM, AMBAS DEPENDENCIAS TIENEN JPA.
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication															//89 : SE QUITÓ ESTA PQ CON MONGO YA NO SON ENTITY---> @EntityScan({"microservice.respuestas.models.entity","microservice.alumnos.commons.models.entity","microservice.commons.examenes.model.entity"})			//PRIMERO SE COPIA EL PACKAGE PROPIO.
public class MicroserviciosRespuestasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosRespuestasApplication.class, args);
	}

}
