package com.example.realestate.job;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 1:19 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            // Cố gắng parse với định dạng chuẩn yyyy-MM-dd
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            // Nếu không đúng định dạng chuẩn, kiểm tra trường hợp "Q1/2022"
            if (date.matches("Q[1-4]/\\d{4}")) {
                int quarter = Integer.parseInt(date.substring(1, 2));
                int year = Integer.parseInt(date.substring(3));
                // Trả về ngày đầu tiên của quý
                switch (quarter) {
                    case 1: return LocalDate.of(year, 1, 1);
                    case 2: return LocalDate.of(year, 4, 1);
                    case 3: return LocalDate.of(year, 7, 1);
                    case 4: return LocalDate.of(year, 10, 1);
                }
            }
            // Trả về null hoặc giá trị mặc định nếu không parse được
            return null;
        }
    }
}
