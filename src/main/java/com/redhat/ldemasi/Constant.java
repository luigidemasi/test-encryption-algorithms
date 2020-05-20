package com.redhat.ldemasi;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Constant {
    public static final int IV_LENGTH = 16;
    public static final int LINE_LENGTH = 180;
    public static final int CELL_LENGTH = 35;
    public static final String DEFAULT_DATA = "{json:{userId:'12345678901234567890'}}";
    public static final Logger logger = Logger.getGlobal();
    public static final String MESSAGE_CHARSET = "UTF-8";
    public static final int SALT_SIZE = 8;
    public static final String RANDOM_GENRATOR = "NativePRNG";
    public static final String DEFAULT_PASSWORD = "wiu34we233[]weuokw/12340645798/3@#4";
    public static final String FORMAT = "%s\t%s\t%s";
    public static final List<String> ALGORITHMS = Arrays.asList(
            "PBEWITHHMACSHA1ANDAES_128",
            "PBEWITHHMACSHA1ANDAES_256",
            "PBEWITHHMACSHA224ANDAES_128",
            "PBEWITHHMACSHA224ANDAES_256",
            "PBEWITHHMACSHA256ANDAES_128",
            "PBEWITHHMACSHA256ANDAES_256",
            "PBEWITHHMACSHA384ANDAES_128",
            "PBEWITHHMACSHA384ANDAES_256",
            "PBEWITHHMACSHA512ANDAES_128",
            "PBEWITHHMACSHA512ANDAES_256",
            "PBEWITHMD2ANDDES",
            "PBEWITHMD5AND128BITAES-CBC-OPENSSL",
            "PBEWITHMD5AND192BITAES-CBC-OPENSSL",
            "PBEWITHMD5AND256BITAES-CBC-OPENSSL",
            "PBEWITHMD5ANDDES",
            "PBEWITHMD5ANDRC2",
            "PBEWITHMD5ANDTRIPLEDES",
            "PBEWITHSHA1ANDDES",
            "PBEWITHSHA1ANDDESEDE",
            "PBEWITHSHA1ANDRC2",
            "PBEWITHSHA1ANDRC2_128",
            "PBEWITHSHA1ANDRC2_40",
            "PBEWITHSHA1ANDRC4_128",
            "PBEWITHSHA1ANDRC4_40",
            "PBEWITHSHA256AND128BITAES-CBC-BC",
            "PBEWITHSHA256AND192BITAES-CBC-BC",
            "PBEWITHSHA256AND256BITAES-CBC-BC",
            "PBEWITHSHAAND128BITAES-CBC-BC",
            "PBEWITHSHAAND128BITRC2-CBC",
            "PBEWITHSHAAND128BITRC4",
            "PBEWITHSHAAND192BITAES-CBC-BC",
            "PBEWITHSHAAND2-KEYTRIPLEDES-CBC",
            "PBEWITHSHAAND256BITAES-CBC-BC",
            "PBEWITHSHAAND3-KEYTRIPLEDES-CBC",
            "PBEWITHSHAAND40BITRC2-CBC",
            "PBEWITHSHAAND40BITRC4",
            "PBEWITHSHAANDIDEA-CBC",
            "PBEWITHSHAANDTWOFISH-CBC");
}
