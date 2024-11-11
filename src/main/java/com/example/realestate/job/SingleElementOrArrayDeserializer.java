package com.example.realestate.job;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 7:24 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class SingleElementOrArrayDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.isExpectedStartArrayToken()) {
            // Bắt đầu mảng, lấy phần tử đầu tiên
            JsonToken nextToken = p.nextToken();

            // Nếu mảng trống, trả về 0
            if (nextToken == JsonToken.END_ARRAY) {
                return 0;
            }

            Integer value = p.getIntValue();

            // Bỏ qua các phần tử còn lại trong mảng
            while (p.nextToken() != JsonToken.END_ARRAY) {
                p.skipChildren();
            }
            return value;
        } else {
            // Nếu không phải mảng, parse trực tiếp
            return p.getIntValue();
        }
    }
}
