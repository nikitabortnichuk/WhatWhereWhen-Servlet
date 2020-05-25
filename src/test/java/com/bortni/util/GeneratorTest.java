package com.bortni.util;

import com.bortni.controller.util.GameStringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    GameStringGenerator gameStringGenerator;
    @BeforeEach
    public void setGenerator(){
        gameStringGenerator = new GameStringGenerator();
    }

    @Test
    public void shouldReturnRandomString_WhenCallGenerate(){ //todo naming

        System.out.println(gameStringGenerator.generate());
        assertTrue(
                gameStringGenerator.generate().matches("[a-zA-Z0-9]+")
        );

    }

}
