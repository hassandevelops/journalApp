package com.bug2feature.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherApiResponse {
    private Current current;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Current {

        @JsonProperty("temp_c")
        private double tempC;

        @JsonProperty("feelslike_c")
        private double feelslikeC;
    }
}
