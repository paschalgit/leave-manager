package com.peswa.leavemanager;

import com.peswa.leavemanager.dto.request.RoleRequest;
import com.peswa.leavemanager.dto.request.UserRequest;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.service.RoleService;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class LeaveManagerApplication {

	@Value("${peswa.app.init-admin.username}")
	private String username;

	@Value("${peswa.app.init-admin.password}")
	private String password;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(LeaveManagerApplication.class, args);
	}


	@EventListener
	public void seed(ContextRefreshedEvent event){
		System.out.println("Seeding Database...");

		roleService.addRole(new RoleRequest(RoleEnum.ROLE_ADMIN));
		roleService.addRole(new RoleRequest(RoleEnum.ROLE_USER));

		if(!userService.existsByUsername(username)){
			userService.addAdmin(new UserRequest(username,password,RoleEnum.ROLE_ADMIN));
		}

	}
}
