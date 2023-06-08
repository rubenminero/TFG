package ruben.SPM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ruben.SPM.model.Entities.Admin;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.AdminService;

import java.util.Date;


@SpringBootApplication
public class SPMApplication {

	@Autowired
	private  AdminService adminService;

	public static void main(String[] args) {

		SpringApplication.run(SPMApplication.class, args);

	}



}
