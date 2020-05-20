package com.redhat.ldemasi;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.redhat.ldemasi.Constant.CELL_LENGTH;
import static com.redhat.ldemasi.Constant.FORMAT;
import static com.redhat.ldemasi.Constant.LINE_LENGTH;
import static com.redhat.ldemasi.Constant.logger;
import static java.util.logging.Level.FINE;

public class Utils {


    public static String getReason(Throwable e){
        if(e.getMessage() != null){
            return e.getClass().getSimpleName()+": "+e.getMessage();
        }
        return e.getClass().getName();
    }


    public static String padTo(String s) {
        return padTo(s, CELL_LENGTH);
    }

    public static String padTo(String s, char c, int length) {
        if (s.length() < length) {
            StringBuilder sb = new StringBuilder(s);
            for (int i = 0; i < length - s.length(); i++) {
                sb.append(c);
            }
            return sb.toString();
        }
        return s;
    }

    public static String padTo(String s, int length) {
        return padTo(s, ' ', length);
    }



    public static class AlgoritmTestResult {

        public AlgoritmTestShortResult shortResult;
        public String reason;

        public AlgoritmTestResult(AlgoritmTestShortResult shortResult, String reason){
            this.shortResult = shortResult;
            this.reason = reason;
        }

    }

    public static int getJCEMaxKeyLength() {
        try {
            return Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ex) {
            return -1;
        }
    }

    public static String getStrategyName(TestAlgorithm strategy) {
        return strategy instanceof PlainJavaTestAlgorithm ? "Plain Java" : "Jasyp";
    }

    public static String getProviders(){
        return Arrays.asList(Security.getProviders()).stream().map(x->x.getName()).collect(Collectors.joining(","));
    }

    public static void printResults(TestAlgorithm strategy, List<String> result){
        // Print response.
        String line = padTo("", '-', LINE_LENGTH);
        int keyLength = getJCEMaxKeyLength();
        String jce = keyLength == Integer.MAX_VALUE ? "YES" : "NO";
        System.out.println(line);
        System.out.println(String.format("Unsing %s, Java Version: %s, max key length: %d (JCE: %s), JCE providers: %s",
                getStrategyName(strategy), System.getProperty("java.version"), keyLength, jce, getProviders()) + "\n" + line);
        System.out.println(String.format(FORMAT, padTo("Algorithm"), padTo("Result"), padTo("Reason"))
                + "\n" + line);
        for (String r : result) {
            System.out.println(r);
        }
    }

    public static List<String> testStrategy(TestAlgorithm strategy, List<String> algorithms) throws Exception{
        List<String> result = new ArrayList<>();
        for (String algorithm : algorithms) {
            Utils.AlgoritmTestResult works = strategy.testAlgorithm(algorithm);
            logger.log(FINE, "Testing " + algorithm + "..." + works.shortResult);
            result.add(String.format(FORMAT, padTo(algorithm), padTo(works.shortResult.toString()), padTo(works.reason)));
        }
        return result;
    }

}
