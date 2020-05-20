package com.redhat.ldemasi;

public  enum AlgoritmTestShortResult {

    OK("-"),
    DECRYPT_FAIL("Failed to decrypt"),
    NOT_POSSIBLE("Encryption not possible"),
    NOT_POSSIBLE_DECRYPT("Unable to decrypt"),
    UNKNOWN("Encryption/Decryption failed for unknown reason");

    public final String reason;

    AlgoritmTestShortResult(String reason){
        this.reason = reason;
    }

}
