package com.ssafy.bango.global.batch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityApiResponse {
    private List<Document> documents;
    private Meta meta;

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("category_group_code")
        private String categoryGroupCode;
        @JsonProperty("category_group_name")
        private String categoryGroupName;
        @JsonProperty("category_name")
        private String categoryName;
        private String distance;
        private String id;
        private String phone;
        @JsonProperty("place_name")
        private String placeName;
        @JsonProperty("place_url")
        private String placeUrl;
        @JsonProperty("road_address_name")
        private String roadAddressName;
        private String x;
        private String y;
    }

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;
        @JsonProperty("same_name")
        private SameName sameName;

        @Getter
        @Setter
        public static class SameName {
            private String keyword;
            private List<String> region;
            @JsonProperty("selected_region")
            private String selectedRegion;
        }
    }
}