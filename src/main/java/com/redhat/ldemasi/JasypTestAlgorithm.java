package com.redhat.ldemasi;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.IvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;

import java.util.List;
import java.util.logging.Level;

import static com.redhat.ldemasi.Utils.AlgoritmTestResult;
import static com.redhat.ldemasi.Constant.RANDOM_GENRATOR;
import static com.redhat.ldemasi.Utils.getReason;
import static com.redhat.ldemasi.Constant.logger;
import static com.redhat.ldemasi.AlgoritmTestShortResult.OK;


/**
 * Main encryption tests.
 *
 * @author nobulletnav
 */
public class JasypTestAlgorithm implements TestAlgorithm {

    private SaltGenerator saltGenerator;
    private IvGenerator ivGenerator;
    private String password;
    private String data;

    public JasypTestAlgorithm(IvGenerator ivGenerator, String password, String data){
        this.ivGenerator = ivGenerator;
        this.saltGenerator = new RandomSaltGenerator(RANDOM_GENRATOR);
        this.password = password;
        this.data = data;
    }


    public  AlgoritmTestResult testAlgorithm(String algorithm) {
        boolean isEncrypted = false;
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(password);
            encryptor.setAlgorithm(algorithm);
            encryptor.setKeyObtentionIterations(1);
            encryptor.setIvGenerator(ivGenerator);
            encryptor.setSaltGenerator(saltGenerator);
            String encrypted = encryptor.encrypt(data);
            isEncrypted = true;
            return data.equals(encryptor.decrypt(encrypted)) ? new AlgoritmTestResult(OK, "-") : new AlgoritmTestResult(AlgoritmTestShortResult.DECRYPT_FAIL,"Not able to Decrypt");
        } catch (org.jasypt.exceptions.EncryptionOperationNotPossibleException e) {
            logger.log(Level.FINE, "Error while using " + algorithm + " ", e);
            return isEncrypted ?  new AlgoritmTestResult(AlgoritmTestShortResult.NOT_POSSIBLE_DECRYPT,  getReason(e)) : new AlgoritmTestResult(AlgoritmTestShortResult.NOT_POSSIBLE, getReason(e));
        } catch (Exception e) {
            logger.log(Level.FINE, "Error while using " + algorithm + " ", e);
            return new AlgoritmTestResult(AlgoritmTestShortResult.UNKNOWN, getReason(e));
        }
    }

}
