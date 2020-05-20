package com.redhat.ldemasi;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jasypt.iv.IvGenerator;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.iv.RandomIvGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static com.redhat.ldemasi.Constant.ALGORITHMS;
import static com.redhat.ldemasi.Constant.DEFAULT_DATA;
import static com.redhat.ldemasi.Constant.DEFAULT_PASSWORD;
import static com.redhat.ldemasi.Constant.RANDOM_GENRATOR;
import static com.redhat.ldemasi.Constant.logger;

public class CommandLineController {

    CommandLineParser parser = new DefaultParser();
    Options options = prepareOptions();
    CommandLine commandLine = null;
    List<TestAlgorithm> strategies;
    List<String> algorithms;
    String password;
    String data;

    public CommandLineController(String[] args) {

        try {
            this.commandLine = parser.parse(prepareOptions(), args);
            this.password = Optional.ofNullable(this.commandLine.getOptionValue("p")).orElse(DEFAULT_PASSWORD);
            this.data = Optional.ofNullable(this.commandLine.getOptionValue("d")).orElse(DEFAULT_DATA);

            String[] algorithmsArray = this.commandLine.getOptionValues("a");
            this.algorithms =  algorithmsArray != null ? Arrays.asList(algorithmsArray) : ALGORITHMS;

            String strategy = this.commandLine.getOptionValue("s");
            IvGenerator ivGenerator = getIv(this.commandLine);
            logger.log(Level.FINE, "got option for strategy: " + strategy);
            strategies = new ArrayList<>();

            if (strategy == null ){
                strategies.add(new PlainJavaTestAlgorithm(ivGenerator,password,data));
                strategies.add(new JasypTestAlgorithm(ivGenerator,password,data));
                return;
            }

            if (strategy.equalsIgnoreCase("plain")) {
                strategies.add(new PlainJavaTestAlgorithm(ivGenerator,password,data));
                return;
            }
            if (strategy.equalsIgnoreCase("jasyp")) {
                strategies.add(new JasypTestAlgorithm(ivGenerator,password,data));
                return;
            }
            logger.log(Level.SEVERE, "invalid option for strategy: " + strategy);
            throw new IllegalArgumentException("invalid option for strategy: " + strategy);

        } catch (ParseException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            new HelpFormatter().printHelp("java -jar test.jar", options);
            System.exit(-1);
        }
    }

    private static Options prepareOptions() {
        Options options = new Options();
        options.addOption(getStrategyOption());
        options.addOption(getIVOption());
        options.addOption(getPasswordOption());
        options.addOption(getDataOption());
        options.addOption(getAlgorithmsOption());
        return options;
    }


    private static Option getAlgorithmsOption() {
        return Option.builder("a").desc("Comma separated values of algorithms to use for testing. Default: "+ String.join(",",ALGORITHMS))
                .longOpt("algorithms")
                .required(false)
                .type(String.class)
                .valueSeparator(',')
                .type(List.class)
                .hasArgs()
                .build();
    }

    private static Option getStrategyOption() {
        return Option.builder("s").desc("valid argument are plain or jasyp. Switch between plain java encryption or jasyp library")
                .longOpt("strategy")
                .required(false)
                .numberOfArgs(1)
                .type(String.class)
                .hasArg()
                .build();
    }

    private static Option getIVOption() {
        return Option.builder("i").desc("Use the initialization vector (a.k.a. nonce) during encryption. This option doesn't take argument.")
                .required(false)
                .longOpt("iv")
                .numberOfArgs(0)
                .hasArg(false)
                .build();
    }

    private static Option getDataOption() {
        return Option.builder("d")
                .desc("data to encrypt. Default: \"{json:{userId:'12345678901234567890'}}\"")
                .required(false)
                .type(String.class)
                .longOpt("data")
                .numberOfArgs(1)
                .hasArg(true)
                .build();
    }

    private static Option getPasswordOption() {
        return Option.builder("p")
                .desc("password used for encrypting data. Default: \"wiu34we233[]weuokw/12340645798/3@#4\"")
                .required(false)
                .type(String.class)
                .longOpt("password")
                .numberOfArgs(1)
                .hasArg(true)
                .build();
    }

    private IvGenerator getIv(org.apache.commons.cli.CommandLine commandLine) {
        if (commandLine.hasOption("-iv")) {
            return new RandomIvGenerator(RANDOM_GENRATOR); }
        return new NoIvGenerator();
    }

    public List<TestAlgorithm> getStrategies() {
        return strategies;
    }

    public List<String> getAlgorithms() {
        return this.algorithms;
    }
}
