package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.Weather;
import dwu.swcmop.trippacks.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    //도시별 날씨 조회
//    @GetMapping("/weather")
//    public Weather createDiary(@RequestBody String city){
//        return weatherService.createDiary(city);
//    }
//
    //도시별 날씨 조회
    @GetMapping("/weather")
    public ResponseEntity<?> createDiary(@RequestParam String city) {
        Weather weather = weatherService.createDiary(city);

        if (weather != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("weather", weather.getWeather());
            response.put("temperature", weather.getTemperature());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Weather data not found", HttpStatus.NOT_FOUND);
        }
    }

}
