package com.example.realestate.job;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 7:21 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FirstElementIntegerDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        // Kiểm tra nếu node là mảng thì lấy phần tử đầu tiên
        if (node.isArray() && node.size() > 0) {
            return node.get(0).asInt();
        } else if (node.isInt()) {
            // Nếu là số nguyên đơn lẻ
            return node.asInt();
        }
        // Trả về null nếu không phải số nguyên hoặc mảng trống
        return null;
    }
}
