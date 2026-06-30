package com.example.schedulemanagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.Connection;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleManagementApplication {

    // application.yml(또는 properties)에서 설정값을 읽어옵니다.
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagementApplication.class, args);

        // 서버 실행 확인을 위한 로그
        System.out.println("========================================");
        System.out.println("ScheduleManagement 서버가 성공적으로 떴습니다!");
        System.out.println("========================================");
    }

    // 서버가 켜질 때 설정값 확인 및 DB 연결 테스트
    @Bean
    public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
        return args -> {
            System.out.println("\n▶ 설정 파일 읽기 테스트");
            System.out.println("DB URL = " + url);
            System.out.println("DB USER = " + username);
            System.out.println("DB PASSWORD = " + password);

            System.out.println("\n▶ 실제 DB 커넥션 테스트");
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("🎉🎉 물리적 DB 연결 성공! 🎉🎉");
                System.out.println("연결된 데이터베이스 카탈로그: " + connection.getCatalog());

            } catch (Exception e) {
                System.out.println("😭😭 DB 연결 실패... 에러를 확인해주세요.");
                System.out.println("에러 메시지: " + e.getMessage());

            }
        };
    }
}