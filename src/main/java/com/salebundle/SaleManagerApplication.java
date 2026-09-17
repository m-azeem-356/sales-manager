package com.salebundle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaleManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SaleManagerApplication.class, args);

        new Thread(() -> {
            try {
                Thread.sleep(3000);

                String os = System.getProperty("os.name").toLowerCase();

                if (os.contains("win")) {
                    new ProcessBuilder(
                            "cmd", "/c", "start", "",
                            "http://localhost:8080/"
                    ).start();
                } else {
                    new ProcessBuilder(
                            "xdg-open",
                            "http://localhost:8080/"
                    ).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}