package com.evilcorp.bloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.evilcorp.bloom")
public class BloomApplication {

  public static void main(String[] args) {
    SpringApplication.run(BloomApplication.class, args);
    System.out.println("Hello World!");
  }
}
