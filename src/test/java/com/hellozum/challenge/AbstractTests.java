package com.hellozum.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTests {

	protected String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
