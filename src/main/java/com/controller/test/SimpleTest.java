package com.controller.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class SimpleTest {

    @Test
    public void test(){
        int x=23;
        int y=24;
        Assert.assertEquals(42,x+y);
    }
}
