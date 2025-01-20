package com.evilcorp.bloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.evilcorp.bloom")
public class BloomApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(BloomApplication.class);
    app.setLazyInitialization(true);
    app.run(args);
    System.out.println("Hello World!");
  }

}
