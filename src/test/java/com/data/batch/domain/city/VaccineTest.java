package com.data.batch.domain.city;

import com.data.batch.domain.vaccine.VaccineStat;
import com.data.batch.repository.vaccine.VaccineStatRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineTest {

    private static final Logger logger = LoggerFactory.getLogger(VaccineTest.class);

    @Autowired
    private VaccineStatRepository vaccineStatRepository;

    @Test
    public void 백신_Test() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        //https://api.odcloud.kr/api/15077756/v1/vaccine-stat?page=1&perPage=18&returnType=XML&cond%5BbaseDate%3A%3AEQ%5D=2021-04-23%2000%3A00%3A00&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D
        //https://api.odcloud.kr/api/15077756/v1/vaccine-stat?page=1&perPage=18&returnType=XML&cond%5BbaseDate%3A%3AEQ%5D=2021-04-23%2000%3A00%3A00&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D

        String stringBuilder = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat" + "?" + URLEncoder.encode("page", "UTF-8") + "=1" +
                "&" + URLEncoder.encode("perPage", "UTF-8") + "=18" +
                "&" + URLEncoder.encode("returnType", "UTF-8") + "=json" +
                "&" + URLEncoder.encode("cond[baseDate::EQ]", "UTF-8") + "=" + date + "%2000%3A00%3A00" +
                "&" + URLEncoder.encode("serviceKey", "UTF-8") + "=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D";
        URL url= new URL(stringBuilder);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String result = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            result = result.concat(line);
        }

        // JSON parser 문자열 데이터를 객체화 시킴
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

        // JSON 배열 데이터 시작
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");

        VaccineStat vaccineStat = new VaccineStat();


        for (int i=0; i<jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            for (Object key : object.keySet()) {
                if (key.equals("baseDate")) {
                    vaccineStat.setBaseDate(object.get(key).toString());
                }

                if (key.equals("sido")) {
                    vaccineStat.setSido(object.get(key).toString());
                }

                if (key.equals("firstCnt")) {
                    vaccineStat.setFirstCnt(object.get(key).toString());
                }

                if (key.equals("secondCnt")) {
                    vaccineStat.setSecondCnt(object.get(key).toString());
                }

                if (key.equals("totalFirstCnt")) {
                    vaccineStat.setTotalFirstCnt(object.get(key).toString());
                }

                if (key.equals("totalSecondCnt")) {
                    vaccineStat.setTotalSecondCnt(object.get(key).toString());
                }

                if (key.equals("accumulatedFirstCnt")) {
                    vaccineStat.setAccumulatedFirstCnt(object.get(key).toString());
                }

                if (key.equals("accumulatedSecondCnt")) {
                    vaccineStat.setAccumulatedSecondCnt(object.get(key).toString());
                }

            }
            logger.info("시도 : {}, 전체카운트 : {}",vaccineStat.toEntity().getSido(), vaccineStat.toEntity().getTotalFirstCnt());
        }
    }
}
