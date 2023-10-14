package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.Weather;
import dwu.swcmop.trippacks.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    //도시별 날씨 조회
    @GetMapping("/weather")
    public Weather createDiary(@RequestBody String city){
        return weatherService.createDiary(city);
    }


}
