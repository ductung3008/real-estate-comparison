package com.example.realestate.constant;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 10:34 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

public interface Endpoint {
    public interface V1 {
        String PREFIX = "/api/v1";

        public interface Auth {
            String PREFIX = V1.PREFIX + "/auth";
            String LOGIN  = PREFIX + "/login";
        }

        public interface User {
            String PREFIX       = V1.PREFIX + "/users";
            String CREATE_USER  = PREFIX;
            String GET_MY_USER  = PREFIX + "/me";
            String GET_ALL_USER = PREFIX;
            String GET_USER     = PREFIX + "/{userId}";
            String UPDATE_USER  = PREFIX + "/{userId}";
            String DELETE_USER  = PREFIX + "/{userId}";
        }

        public interface Project {
            String PREFIX            = V1.PREFIX + "/projects";
            String CREATE_PROJECT    = PREFIX;
            String IMPORT_PROJECT    = PREFIX + "/import";
            String GET_PROJECT       = PREFIX;
            String GET_PROJECT_BY_ID = PREFIX + "/{projectId}";
            String DELETE_PROJECT    = PREFIX + "/{projectId}";
            String UPDATE_PROJECT    = PREFIX + "/{projectId}";
        }

        public interface PropertyType {
            String PREFIX          = V1.PREFIX + "/projects/{projectId}/property-types";
            String PROPERTY_PREFIX = V1.PREFIX + "/property-types";
            String CREATE_PROPERTY = PREFIX;
            String GET_PROPERTIES  = PREFIX;
            String GET_PROPERTY    = PREFIX + "/{propertyId}";
            String DELETE_PROPERTY = PROPERTY_PREFIX + "/{propertyId}";
            String UPDATE_PROPERTY = PROPERTY_PREFIX + "/{propertyId}";
        }

        public interface Place {
            String PREFIX       = V1.PREFIX + "/projects/{projectId}/places";
            String PLACE_PREFIX = V1.PREFIX + "/places";
            String CREATE_PLACE = PREFIX;
            String IMPORT_PLACE = PLACE_PREFIX + "/import";
            String GET_PLACES   = PREFIX;
            String GET_PLACE    = PREFIX + "/{placeId}";
            String DELETE_PLACE = PLACE_PREFIX + "/{placeId}";
            String UPDATE_PLACE = PLACE_PREFIX + "/{placeId}";
        }

        public interface Price {
            String PREFIX = V1.PREFIX + "/projects/{projectId}/prices";
            String GET_PRICE_BY_PROJECT = PREFIX;
            String IMPORT_PRICE = V1.PREFIX + "/prices/import";
        }

        public interface Stat {
            String PREFIX = V1.PREFIX + "/stats";
            String PRICE = PREFIX + "/prices";
            String DISTRICT = PREFIX + "/districts";
            String AREA = PREFIX + "/areas";
            String PARKING = PREFIX + "/parking";
        }
    }
}
