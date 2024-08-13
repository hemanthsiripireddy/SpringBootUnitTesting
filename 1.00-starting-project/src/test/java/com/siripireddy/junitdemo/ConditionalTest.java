package com.siripireddy.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {

    @Test
    @Disabled("Run only once jira #123 is fixed")
    public void basicTest(){

    }
    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void testWindowsOnly(){

    }
    @Test
    @EnabledOnOs(OS.MAC)
    public void testMacOnly(){

    }


    @Test
    @EnabledOnOs({OS.MAC,OS.WINDOWS})
    public void testMacAndWindowsOnly(){

    }

    @Test
    @EnabledOnOs(OS.LINUX)
    public void testLinuxOnly(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    public void testForJava13(){

    }
    @Test
    @EnabledOnJre(JRE.JAVA_17)
    public void testForJava22(){

    }

    @Test
    @EnabledForJreRange(min= JRE.JAVA_12,max = JRE.JAVA_17)
    public void testForJavaRange(){

    }

    @Test
    @EnabledForJreRange(min= JRE.JAVA_12)
    public void testForJavaMin(){

    }
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV",matches = "DEV")
    public void testOnlyForDevEnvironment(){

    }

    @Test
    @EnabledIfSystemProperty(named = "SYS_PROP",matches = "CI_CD_DEPLOY")
    public void testOnlyForSystemProperty(){

    }



}
