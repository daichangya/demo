package com.daicy.ad.push.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    @Test
    public void encode() {
        System.out.println(JsonUtils.encode("564898985"));
        System.out.println(JsonUtils.encode(null));

    }
}