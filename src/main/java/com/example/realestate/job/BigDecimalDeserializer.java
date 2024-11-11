package com.example.realestate.job;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 11:56 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().trim();
        if (value.isEmpty()) {
            return BigDecimal.ZERO; // Giá trị mặc định nếu trường hợp là null hoặc rỗng
        }
        return new BigDecimal(value);
    }
}