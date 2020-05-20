package com.redhat.ldemasi;

import org.jasypt.iv.IvGenerator;

public interface TestAlgorithm {

    public Utils.AlgoritmTestResult testAlgorithm(String algorithm) throws Exception;
}
