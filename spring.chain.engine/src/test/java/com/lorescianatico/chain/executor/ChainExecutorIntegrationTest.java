package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionConfiguration;
import com.lorescianatico.chain.configuration.LoaderConfig;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.loader.XMLChainLoader;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ChainExecutorBean.class, DummyHandler.class, AnotherDummyHandler.class,
        ChainExecutionConfiguration.class, XMLChainLoader.class, LoaderConfig.class},
        properties = {"catalogfile = ./src/test/resources/configuration.xml"})
class ChainExecutorIntegrationTest {

    @Autowired
    private ChainExecutor chainExecutor;

    @Test
    void testExecutor(){
        try {
            chainExecutor.executeChain("Chain", new AbstractChainContext() {});
        } catch (ChainExecutionException e) {
            fail();
        }
    }

}
