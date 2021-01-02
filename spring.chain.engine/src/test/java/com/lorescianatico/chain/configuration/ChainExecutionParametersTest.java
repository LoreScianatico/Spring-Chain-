package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.util.ConfigType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ChainExecutionParametersTest {

    @Test
    public void getCatalogFileExtension() {
        ChainExecutionParameters chainExecutionParameters = new ChainExecutionParameters();
        chainExecutionParameters.setCatalogFileLocation("file.xml");
        Assertions.assertEquals(ConfigType.XML, chainExecutionParameters.getCatalogFileExtension());
    }

}