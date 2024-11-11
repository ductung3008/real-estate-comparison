package com.example.realestate.util;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 11:07 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {
    private final MessageSource messageSource;

    public String getLocalizedMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}
