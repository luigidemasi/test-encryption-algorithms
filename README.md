Test Encryption Algorithm
============

Small java command line application that show which PBE (Password Based Encryption) Algorithm needs an 
Initialilzation Vector (IV), also known as Nonce.

Usage
----------
```
usage: java -jar test.jar
 -a,--algorithms <arg>   Comma separated values of algorithms to use for
                         testing. Default:
                         PBEWITHHMACSHA1ANDAES_128,PBEWITHHMACSHA1ANDAES_2
                         56,PBEWITHHMACSHA224ANDAES_128,PBEWITHHMACSHA224A
                         NDAES_256,PBEWITHHMACSHA256ANDAES_128,PBEWITHHMAC
                         SHA256ANDAES_256,PBEWITHHMACSHA384ANDAES_128,PBEW
                         ITHHMACSHA384ANDAES_256,PBEWITHHMACSHA512ANDAES_1
                         28,PBEWITHHMACSHA512ANDAES_256,PBEWITHMD2ANDDES,P
                         BEWITHMD5AND128BITAES-CBC-OPENSSL,PBEWITHMD5AND19
                         2BITAES-CBC-OPENSSL,PBEWITHMD5AND256BITAES-CBC-OP
                         ENSSL,PBEWITHMD5ANDDES,PBEWITHMD5ANDRC2,PBEWITHMD
                         5ANDTRIPLEDES,PBEWITHSHA1ANDDES,PBEWITHSHA1ANDDES
                         EDE,PBEWITHSHA1ANDRC2,PBEWITHSHA1ANDRC2_128,PBEWI
                         THSHA1ANDRC2_40,PBEWITHSHA1ANDRC4_128,PBEWITHSHA1
                         ANDRC4_40,PBEWITHSHA256AND128BITAES-CBC-BC,PBEWIT
                         HSHA256AND192BITAES-CBC-BC,PBEWITHSHA256AND256BIT
                         AES-CBC-BC,PBEWITHSHAAND128BITAES-CBC-BC,PBEWITHS
                         HAAND128BITRC2-CBC,PBEWITHSHAAND128BITRC4,PBEWITH
                         SHAAND192BITAES-CBC-BC,PBEWITHSHAAND2-KEYTRIPLEDE
                         S-CBC,PBEWITHSHAAND256BITAES-CBC-BC,PBEWITHSHAAND
                         3-KEYTRIPLEDES-CBC,PBEWITHSHAAND40BITRC2-CBC,PBEW
                         ITHSHAAND40BITRC4,PBEWITHSHAANDIDEA-CBC,PBEWITHSH
                         AANDTWOFISH-CBC
 -d,--data <arg>         data to encrypt. Default:
                         "{json:{userId:'12345678901234567890'}}"
 -i,--iv                 Use the initialization vector (a.k.a. nonce)
                         during encryption. This option doesn't take
                         argument.
 -p,--password <arg>     password used for encrypting data. Default:
                         "wiu34we233[]weuokw/12340645798/3@#4"
 -s,--strategy <arg>     valid argument are plain or jasyp. Switch between
                         plain java encryption or jasyp library

```

Default Output:

```
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Unsing Plain Java, Java Version: 1.8.0_232, max key length: 2147483647 (JCE: YES), JCE providers: SUN,SunRsaSign,SunEC,SunJSSE,SunJCE,SunJGSS,SunSASL,XMLDSig,SunPCSC,BC
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Algorithm                               Result                                  Reason                             
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PBEWITHHMACSHA1ANDAES_128               NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA1ANDAES_256               NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA224ANDAES_128             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA224ANDAES_256             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA256ANDAES_128             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA256ANDAES_256             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA384ANDAES_128             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA384ANDAES_256             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA512ANDAES_128             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHHMACSHA512ANDAES_256             NOT_POSSIBLE                            InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
PBEWITHMD2ANDDES                        OK                                      -                                  
PBEWITHMD5AND128BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5AND192BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5AND256BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5ANDDES                        OK                                      -                                  
PBEWITHMD5ANDRC2                        OK                                      -                                  
PBEWITHMD5ANDTRIPLEDES                  OK                                      -                                  
PBEWITHSHA1ANDDES                       OK                                      -                                  
PBEWITHSHA1ANDDESEDE                    OK                                      -                                  
PBEWITHSHA1ANDRC2                       OK                                      -                                  
PBEWITHSHA1ANDRC2_128                   OK                                      -                                  
PBEWITHSHA1ANDRC2_40                    OK                                      -                                  
PBEWITHSHA1ANDRC4_128                   OK                                      -                                  
PBEWITHSHA1ANDRC4_40                    OK                                      -                                  
PBEWITHSHA256AND128BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHA256AND192BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHA256AND256BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHAAND128BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND128BITRC2-CBC              OK                                      -                                  
PBEWITHSHAAND128BITRC4                  OK                                      -                                  
PBEWITHSHAAND192BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND2-KEYTRIPLEDES-CBC         OK                                      -                                  
PBEWITHSHAAND256BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND3-KEYTRIPLEDES-CBC         OK                                      -                                  
PBEWITHSHAAND40BITRC2-CBC               OK                                      -                                  
PBEWITHSHAAND40BITRC4                   OK                                      -                                  
PBEWITHSHAANDIDEA-CBC                   OK                                      -                                  
PBEWITHSHAANDTWOFISH-CBC                OK                                      -                                  
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Unsing Jasyp, Java Version: 1.8.0_232, max key length: 2147483647 (JCE: YES), JCE providers: SUN,SunRsaSign,SunEC,SunJSSE,SunJCE,SunJGSS,SunSASL,XMLDSig,SunPCSC,BC
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Algorithm                               Result                                  Reason                             
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PBEWITHHMACSHA1ANDAES_128               NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA1ANDAES_256               NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA224ANDAES_128             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA224ANDAES_256             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA256ANDAES_128             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA256ANDAES_256             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA384ANDAES_128             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA384ANDAES_256             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA512ANDAES_128             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHHMACSHA512ANDAES_256             NOT_POSSIBLE                            org.jasypt.exceptions.EncryptionOperationNotPossibleException
PBEWITHMD2ANDDES                        OK                                      -                                  
PBEWITHMD5AND128BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5AND192BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5AND256BITAES-CBC-OPENSSL      OK                                      -                                  
PBEWITHMD5ANDDES                        OK                                      -                                  
PBEWITHMD5ANDRC2                        OK                                      -                                  
PBEWITHMD5ANDTRIPLEDES                  OK                                      -                                  
PBEWITHSHA1ANDDES                       OK                                      -                                  
PBEWITHSHA1ANDDESEDE                    OK                                      -                                  
PBEWITHSHA1ANDRC2                       OK                                      -                                  
PBEWITHSHA1ANDRC2_128                   OK                                      -                                  
PBEWITHSHA1ANDRC2_40                    OK                                      -                                  
PBEWITHSHA1ANDRC4_128                   OK                                      -                                  
PBEWITHSHA1ANDRC4_40                    OK                                      -                                  
PBEWITHSHA256AND128BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHA256AND192BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHA256AND256BITAES-CBC-BC        OK                                      -                                  
PBEWITHSHAAND128BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND128BITRC2-CBC              OK                                      -                                  
PBEWITHSHAAND128BITRC4                  OK                                      -                                  
PBEWITHSHAAND192BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND2-KEYTRIPLEDES-CBC         OK                                      -                                  
PBEWITHSHAAND256BITAES-CBC-BC           OK                                      -                                  
PBEWITHSHAAND3-KEYTRIPLEDES-CBC         OK                                      -                                  
PBEWITHSHAAND40BITRC2-CBC               OK                                      -                                  
PBEWITHSHAAND40BITRC4                   OK                                      -                                  
PBEWITHSHAANDIDEA-CBC                   OK                                      -                                  
PBEWITHSHAANDTWOFISH-CBC                OK                                      -    

     
```
 
