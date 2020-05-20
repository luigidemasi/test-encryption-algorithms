package com.redhat.ldemasi;

import org.jasypt.commons.CommonUtils;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.iv.IvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.logging.Level;

import static com.redhat.ldemasi.Constant.IV_LENGTH;
import static com.redhat.ldemasi.Constant.MESSAGE_CHARSET;
import static com.redhat.ldemasi.Constant.SALT_SIZE;
import static com.redhat.ldemasi.Utils.AlgoritmTestResult;
import static com.redhat.ldemasi.AlgoritmTestShortResult.*;
import static com.redhat.ldemasi.Constant.RANDOM_GENRATOR;
import static com.redhat.ldemasi.Utils.getReason;
import static com.redhat.ldemasi.Constant.logger;


public class PlainJavaTestAlgorithm implements TestAlgorithm {

    private IvGenerator ivGenerator;
    private SaltGenerator saltGenerator;
    private String password;
    private String data;

    public PlainJavaTestAlgorithm(IvGenerator ivGenerator, String password, String data) {
        this.ivGenerator = ivGenerator;
        this.saltGenerator = new RandomSaltGenerator(RANDOM_GENRATOR);
        this.password = password;
        this.data = data;
    }

    public AlgoritmTestResult testAlgorithm(String algorithm) throws Exception {
        boolean isEncrypted = false;
        Encryptor encryptor = new Encryptor(algorithm);
        try {
            String encrypted = encryptor.encrypt(data);
            isEncrypted = true;
            return data.equals(encryptor.decrypt(encrypted)) ? new AlgoritmTestResult(OK, OK.reason) : new AlgoritmTestResult(DECRYPT_FAIL, DECRYPT_FAIL.reason);
        } catch (org.jasypt.exceptions.EncryptionOperationNotPossibleException e) {
            logger.log(Level.FINE, "Error while using " + algorithm + " ", e);
            return isEncrypted ? new AlgoritmTestResult(NOT_POSSIBLE_DECRYPT, getReason(e)) : new AlgoritmTestResult(NOT_POSSIBLE, getReason(e));
        }  catch (InvalidAlgorithmParameterException e) {
            logger.log(Level.FINE, "Error while using " + algorithm + " ", e);
            return new AlgoritmTestResult(AlgoritmTestShortResult.NOT_POSSIBLE, getReason(e));
        }catch (Exception e) {
            logger.log(Level.FINE, "Error while using " + algorithm + " ", e);
            return new AlgoritmTestResult(AlgoritmTestShortResult.UNKNOWN, getReason(e));
        }
    }


    private class Encryptor {
        PBEKeySpec pbeKeySpec;
        SecretKeyFactory secretKeyFactory;
        SecretKey secretKey;
        private Cipher encryptCipher = null;
        private Cipher decryptCipher = null;

        public Encryptor(String algorithm) throws Exception {
            this.pbeKeySpec = new PBEKeySpec(password.toCharArray());
            this.secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            this.secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            this.encryptCipher = Cipher.getInstance(algorithm);
            this.decryptCipher = Cipher.getInstance(algorithm);
        }

        public String encrypt(String data) throws Exception {
            final byte[] salt = saltGenerator.generateSalt(SALT_SIZE);
            byte[] iv = ivGenerator.generateIv(IV_LENGTH);
            byte[] encryptedMessage;
            byte[] message = data.getBytes();

            /*
             * Perform encryption using the Cipher
             */
            final PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 1, new IvParameterSpec(iv));

            synchronized (this.encryptCipher) {
                this.encryptCipher.init(
                        Cipher.ENCRYPT_MODE, this.secretKey, parameterSpec);
                encryptedMessage = this.encryptCipher.doFinal(message);
            }

            if (ivGenerator.includePlainIvInEncryptionResults()) {
                // Insert plain IV before the encryption result
                encryptedMessage = CommonUtils.appendArrays(iv, encryptedMessage);
            }

            if (saltGenerator.includePlainSaltInEncryptionResults()) {
                // Insert unhashed salt before the encryption result
                encryptedMessage = CommonUtils.appendArrays(salt, encryptedMessage);

            }
            encryptedMessage = Base64.getEncoder().encode(encryptedMessage);
            return new String(encryptedMessage,MESSAGE_CHARSET);
        }

        public String decrypt(final String data) throws Exception {
            byte[] encryptedMessage = data.getBytes(MESSAGE_CHARSET);
            encryptedMessage = Base64.getDecoder().decode(encryptedMessage);

            if (saltGenerator.includePlainSaltInEncryptionResults()
                    && ivGenerator.includePlainIvInEncryptionResults()) {
                // Check that the received message is bigger than the salt + IV
                if (encryptedMessage.length <= SALT_SIZE + IV_LENGTH) {
                    throw new EncryptionOperationNotPossibleException();
                }
            } else if (saltGenerator.includePlainSaltInEncryptionResults()) {
                // Check that the received message is bigger than the salt
                if (encryptedMessage.length <= SALT_SIZE) {
                    throw new EncryptionOperationNotPossibleException();
                }
            } else if (ivGenerator.includePlainIvInEncryptionResults()) {
                // Check that the received message is bigger than the IV
                if (encryptedMessage.length <= SALT_SIZE) {
                    throw new EncryptionOperationNotPossibleException();
                }
            }

            byte[] salt = null;
            byte[] encryptedMessageKernel = null;

            final int saltStart = 0;
            final int saltSize =
                    (SALT_SIZE < encryptedMessage.length ? SALT_SIZE : encryptedMessage.length);
            final int saltEncMesKernelStart =
                    (SALT_SIZE < encryptedMessage.length ? SALT_SIZE : encryptedMessage.length);
            final int saltEncMesKernelSize =
                    (SALT_SIZE < encryptedMessage.length ? (encryptedMessage.length - SALT_SIZE) : 0);

            salt = new byte[saltSize];
            encryptedMessageKernel = new byte[saltEncMesKernelSize];

            System.arraycopy(encryptedMessage, saltStart, salt, 0, saltSize);
            System.arraycopy(encryptedMessage, saltEncMesKernelStart, encryptedMessageKernel, 0, saltEncMesKernelSize);
            try {
                byte[] iv = null;
                byte[] finalEncryptedMessageKernel = null;
                if (ivGenerator.includePlainIvInEncryptionResults()) {

                    final int ivStart = 0;
                    final int ivSize =
                            (IV_LENGTH < encryptedMessageKernel.length ? IV_LENGTH : encryptedMessageKernel.length);
                    final int encMesKernelStart =
                            (IV_LENGTH < encryptedMessageKernel.length ? IV_LENGTH : encryptedMessageKernel.length);
                    final int encMesKernelSize =
                            (IV_LENGTH < encryptedMessageKernel.length ? (encryptedMessageKernel.length - IV_LENGTH) : 0);

                    iv = new byte[ivSize];
                    finalEncryptedMessageKernel = new byte[encMesKernelSize];

                    System.arraycopy(encryptedMessageKernel, ivStart, iv, 0, ivSize);
                    System.arraycopy(encryptedMessageKernel, encMesKernelStart, finalEncryptedMessageKernel, 0, encMesKernelSize);

                } else {
                    iv = ivGenerator.generateIv(IV_LENGTH);
                    finalEncryptedMessageKernel = encryptedMessageKernel;

                }

                final byte[] decryptedMessage;

                /*
                 * Perform decryption using the Cipher
                 */
                final PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 1, new IvParameterSpec(iv));

                synchronized (this.decryptCipher) {
                    this.decryptCipher.init(
                            Cipher.DECRYPT_MODE, this.secretKey, parameterSpec);
                    decryptedMessage =
                            this.decryptCipher.doFinal(finalEncryptedMessageKernel);
                }

                // Return the results
                return new String(decryptedMessage,MESSAGE_CHARSET);

            } catch (final InvalidKeyException e) {
                // The problem could be not having the unlimited strength policies
                // installed, so better give a usefull error message.
                handleInvalidKeyException(e);
                throw new EncryptionOperationNotPossibleException();
            }
        }
    }

    private void handleInvalidKeyException(final InvalidKeyException e) {

        if ((e.getMessage() != null) &&
                ((e.getMessage().toUpperCase().indexOf("KEY SIZE") != -1))) {

            throw new EncryptionOperationNotPossibleException(
                    "Encryption raised an exception. A possible cause is " +
                            "you are using strong encryption algorithms and " +
                            "you have not installed the Java Cryptography " +
                            "Extension (JCE) Unlimited Strength Jurisdiction " +
                            "Policy Files in this Java Virtual Machine\n\nMessage:" + e.getMessage());

        }

    }
}