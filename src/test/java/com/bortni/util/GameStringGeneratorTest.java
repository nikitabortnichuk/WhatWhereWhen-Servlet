package com.bortni.util;

import com.bortni.controller.util.GameStringGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStringGeneratorTest {

    @Test
    public void shouldReturnString_WhenGenerate(){
        String actual = GameStringGenerator.generate();

        assertTrue(actual.matches("[\\w]*"));
    }

}
