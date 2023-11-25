package dwu.swcmop.trippacks.service;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import dwu.swcmop.trippacks.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {
    @Value("${openweathermap.key}")
    private String apiKey;

    public Weather createDiary(String city) {

        // 날씨 데이터 가져오기
        String weatherData = getWeatherString(city);

        // 받아온 날씨 json 파싱
        Map<String, Object> parsedWeather = parseWeather(weatherData);

        Weather nowDiary = new Weather();
        nowDiary.setWeather(parsedWeather.get("main").toString());
//        nowDiary.setTemperature((Double) parsedWeather.get("temp") - 273.15);

        double temperature = (Double) parsedWeather.get("temp") - 273.15;
        temperature = Math.round(temperature * 100.0) / 100.0;
        nowDiary.setTemperature(temperature);
        nowDiary.setIcon(parsedWeather.get("icon").toString()); // Set the icon value

        return nowDiary;
    }


    private String getWeatherString(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //  apiUrl을 HttpURL 형식으로 연결
            connection.setRequestMethod("GET"); // get 요청
            int responseCode = connection.getResponseCode();   // 요청을 보낸 다음에 받아온 응답 결과를 응답 코드(상태 코드)로 받아옴

            BufferedReader br;
            if (responseCode == 200) {    // 정상
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();   // 결괏값을 response에 쌓음

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            return "failed to get response!";
        }
    }

    private Map<String, Object> parseWeather(String jsonString) {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;  // parsing 결괏값

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);     // 파싱 결과
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");          // main의 value
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));
        return resultMap;
    }
}