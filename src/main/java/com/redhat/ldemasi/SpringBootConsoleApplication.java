package com.redhat.ldemasi;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;
import java.util.List;

import static com.redhat.ldemasi.Utils.printResults;
import static com.redhat.ldemasi.Utils.testStrategy;


@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("spring.main.banner-mode", "off");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        CommandLineController commandLineController = new CommandLineController(args);
        List<TestAlgorithm> strategies = commandLineController.getStrategies();
        List<String> algorithms = commandLineController.getAlgorithms();

        for (TestAlgorithm strategy : strategies) {
            List<String> result = testStrategy(strategy, algorithms);
            printResults(strategy, result);
        }
    }
}




