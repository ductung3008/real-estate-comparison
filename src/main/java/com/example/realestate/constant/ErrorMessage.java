package com.example.realestate.constant;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 11:04 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

public interface ErrorMessage {
    public interface Auth {
        String ERR_INVALID_TOKEN     = "auth.error.invalid_token";
        String ERR_EXPIRED_SESSION   = "auth.error.expired_session";
        String ERR_UNSUPPORTED_TOKEN = "auth.error.unsupported_token";
        String ERR_INVALID_SIGNATURE = "auth.error.invalid_signature";
        String ERR_NOT_LOGIN         = "auth.error.not_login";
        String ERR_MISSING_PREFIX    = "auth.error.missing_prefix";
        String ERR_FORBIDDEN         = "auth.error.forbidden";
    }

    public interface User {
        String ERR_NOT_FOUND_BY_USERNAME  = "user.error.not_found_username";
        String ERR_NOT_FOUND_BY_ID        = "user.error.not_found_id";
        String ERR_CANNOT_UPDATE_USERNAME = "user.error.not_update_username";
    }

    public interface Project {
        String ERR_NOT_FOUND_BY_ID = "project.error.not_found_id";
    }

    public interface PropertyType {
        String ERR_NOT_FOUND_BY_ID      = "property.error.not_found_id";
        String ERR_NOT_FOUND_IN_PROJECT = "property.error.not_found_in_project";
    }

    public interface Place {
        String ERR_NOT_FOUND_IN_PROJECT = "place.error.not_found_in_project";
        String ERR_NOT_FOUND_BY_ID      = "place.error.not_found_id";
    }
}
