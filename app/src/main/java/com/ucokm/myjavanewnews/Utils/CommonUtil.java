package com.ucokm.myjavanewnews.Utils;

import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;

public class CommonUtil {

    public static ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

    public static class Mapper {
        public static String NewsCountry(final String countryName) {
            switch (countryName) {
                case "Indonesia":
                    return "id";
                case "United States":
                    return "us";
                case "France":
                    return "fr";
                case "Italy":
                    return "it";
                case "Singapore":
                    return "id";
                case "New Zealand":
                    return "nz";
                case "India":
                    return "in";
                default: return "all";
            }
        }

        public static String NewsCategory(String categoryName) {
            switch (categoryName) {
                case "Business":
                    return "business";
                case "Entertainment":
                    return "entertainment";
                case "Health":
                    return "health";
                case "Science":
                    return "science";
                case "Sports":
                    return "sports";
                case "Technology":
                    return "technology";
                default: return "general";
            }
        }
    }
}
