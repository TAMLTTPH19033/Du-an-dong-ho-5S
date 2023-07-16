package com.datn.dongho5s;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjectDatnApplication {


	public static void main(String[] args) throws Exception {
//		DiaChiAPI.callGetTinhThanhAPI();
//		DiaChiAPI.callGetQuanHuyenAPI();
		SpringApplication.run(ProjectDatnApplication.class, args);

	}

}
