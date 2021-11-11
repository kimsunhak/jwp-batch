package com.data.batch.domain.city;


import com.data.batch.domain.center.CovidCenter;
import com.data.batch.repository.center.CovidCenterRepository;
import com.data.batch.service.CovidCityService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class cityTest {
    private static final Logger logger = LoggerFactory.getLogger(cityTest.class);
    public static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");
    @Autowired
    private CovidCenterRepository covidCenterRepository;

    @Autowired
    private CovidCityService covidCityService;

    @Test
    public void JobParameters_테스트() throws Exception {
        LocalDate date = LocalDate.now();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("orderDate", date.format(FORMATTER))
                .toJobParameters();

        logger.info("jobParameters {}", jobParameters);
    }

    @Test
    public void dateTest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        System.out.println(date.replaceAll("-", ""));
    }


    @Test
    public void JSON_테스트() throws Exception {
        String covidCenterUrl = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=300&returnType=json&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D";

        URL url = new URL(covidCenterUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String result = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            result = result.concat(line);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");

        CovidCenter covidCenter = new CovidCenter();

        List<CovidCenter> covidCenterList = covidCenterRepository.findAll();

        if (covidCenterList.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                for (Object key : object.keySet()) {
                    if (key.equals("id")) {
                        covidCenter.setId((Long) object.get(key));
                    }

                    if (key.equals("address")) {
                        covidCenter.setAddress(object.get(key).toString());
                    }

                    if (key.equals("centerName")) {
                        covidCenter.setCenterName(object.get(key).toString());
                    }

                    if (key.equals("lat")) {
                        covidCenter.setLat(object.get(key).toString());
                    }

                    if (key.equals("lng")) {
                        covidCenter.setLng(object.get(key).toString());
                    }
                }
                logger.info("Size Null ---> id: {}, address : {}, centerName : {}, lat : {}, lng : {}", covidCenter.toEntity().getId(), covidCenter.toEntity().getAddress(), covidCenter.toEntity().getCenterName(), covidCenter.toEntity().getLat(), covidCenter.toEntity().getLng());
//                covidCenterRepository.save(covidCenter.toEntity());
            }
        } else if (covidCenterList.size() < jsonArray.size()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                for (Object key : object.keySet()) {
                    if (key.equals("id")) {
                        covidCenter.setId((Long) object.get(key));
                    }

                    if (key.equals("address")) {
                        covidCenter.setAddress(object.get(key).toString());
                    }

                    if (key.equals("centerName")) {
                        covidCenter.setCenterName(object.get(key).toString());
                    }

                    if (key.equals("lat")) {
                        covidCenter.setLat(object.get(key).toString());
                    }

                    if (key.equals("lng")) {
                        covidCenter.setLng(object.get(key).toString());
                    }
                }
            }
            logger.info("covidCenterList.size() < jsonArray.size() ---> id: {}, address : {}, centerName : {}, lat : {}, lng : {}", covidCenter.toEntity().getId(), covidCenter.toEntity().getAddress(), covidCenter.toEntity().getCenterName(), covidCenter.toEntity().getLat(), covidCenter.toEntity().getLng());
        } else {
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject object = (JSONObject) jsonArray.get(j);
                for (Object key : object.keySet()) {
                    if (key.equals("id")) {
                         if (!covidCenterList.get(j).getId().equals(object.get(key))) {
                            covidCenter.setId((Long) object.get(key));
                        }
                    }
                    if (key.equals("address")) {
                        if (!covidCenterList.get(j).getAddress().equals(object.get(key).toString())) {
                            covidCenter.setAddress(object.get(key).toString());
                        }
                    }

                    if (key.equals("centerName")) {
                        if (!covidCenterList.get(j).getCenterName().equals(object.get(key).toString())) {
                            covidCenter.setCenterName(object.get(key).toString());
                        }
                    }

                    if (key.equals("lat")) {
                        if (!covidCenterList.get(j).getLat().equals(object.get(key).toString())) {
                            covidCenter.setLat(object.get(key).toString());
                        }
                    }

                    if (key.equals("lng")) {
                        if (!covidCenterList.get(j).getLng().equals(object.get(key).toString())) {
                            covidCenter.setLng(object.get(key).toString());
                        }
                    }
                }
                if (covidCenter.getAddress() != null) {
//                    covidCenterRepository.save(covidCenter.toEntity());
                    logger.info("covidCenter.getAddress() ---> id: {}, address : {}, centerName : {}, lat : {}, lng : {}", covidCenter.toEntity().getId(), covidCenter.toEntity().getAddress(), covidCenter.toEntity().getCenterName(), covidCenter.toEntity().getLat(), covidCenter.toEntity().getLng());
                }
            }
        }
    }


    @Test
    public void covid_center_테스트_진행() throws Exception {
        String covidCenterUrl = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=300&returnType=json&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D";

        URL url = new URL(covidCenterUrl);
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
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");   // 업데이트된 센터 리스트

        CovidCenter covidCenter = new CovidCenter();

        List<CovidCenter> covidCenterList = covidCenterRepository.findAllByOrderById();    // 기존 센터 리스트

        if (covidCenterList.isEmpty()) {    // 기존 센터가 없는 경우
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                for (Object key : object.keySet()) {
                    if (key.equals("id")) {
                        covidCenter.setId(Long.valueOf(object.get(key).toString()));
                    }
                    if (key.equals("address")) {
                        covidCenter.setAddress(object.get(key).toString());
                    }
                    if (key.equals("centerName")) {
                        covidCenter.setCenterName(object.get(key).toString());
                    }
                    if (key.equals("lat")) {
                        covidCenter.setLat(object.get(key).toString());
                    }
                    if (key.equals("lng")) {
                        covidCenter.setLng(object.get(key).toString());
                    }
                }
                covidCenterRepository.save(covidCenter.toEntity());
            }
        } else {    // 기존 센터가 있는 경우
            for (int i = 0; i < (jsonArray.size() <= covidCenterList.size() ? jsonArray.size() : covidCenterList.size()); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (!object.get("id").equals(covidCenterList.get(i).getId()) && !object.get("address").toString().equals(covidCenterList.get(i).getAddress())) {
                    covidCenterList.get(i).setId(Long.valueOf(object.get("id").toString()));
                    covidCenterList.get(i).setAddress(object.get("address").toString());
                    covidCenterList.get(i).setCenterName(object.get("centerName").toString());
                    covidCenterList.get(i).setLat(object.get("lat").toString());
                    covidCenterList.get(i).setLng(object.get("lng").toString());
                    logger.info("전체카운트 : {}",covidCenterList.get(i));
                    covidCenterRepository.save(covidCenterList.get(i));
                }
            }
            logger.info("Size : {}", covidCenterList.size());

            if (jsonArray.size() < covidCenterList.size()) {
                for (int j = jsonArray.size(); j < covidCenterList.size(); j++) {
                    covidCenterRepository.deleteById(covidCenterList.get(j).getId());
                }
            } else if(covidCenterList.size() < jsonArray.size()) {
                for (int k = covidCenterList.size(); k < jsonArray.size(); k++) {
                    JSONObject object = (JSONObject) jsonArray.get(k);
                    covidCenter.setId(Long.valueOf(object.get("id").toString()));
                    covidCenter.setAddress(object.get("address").toString());
                    covidCenter.setCenterName(object.get("centerName").toString());
                    covidCenter.setLat(object.get("lat").toString());
                    covidCenter.setLng(object.get("lng").toString());
                    logger.info("test :{}", covidCenter.getId());
                    covidCenterRepository.save(covidCenter.toEntity());
                }
            }
        }
    }

    @Disabled
    @Test
    public void covidCenter_Test() throws Exception {
        String covidCenterUrl = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=300&returnType=json&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D";

        URL url = new URL(covidCenterUrl);
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
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");   // 업데이트된 센터 리스트

        CovidCenter covidCenter = new CovidCenter();

        List<CovidCenter> covidCenterList = covidCenterRepository.findAllByOrderById();    // 기존 센터 리스트

        if (covidCenterList.isEmpty()) {    // 기존 센터가 없는 경우
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                for (Object key : object.keySet()) {
                    if (key.equals("id")) {
                        covidCenter.setId(Long.valueOf(object.get(key).toString()));
                    }
                    if (key.equals("address")) {
                        covidCenter.setAddress(object.get(key).toString());
                    }
                    if (key.equals("centerName")) {
                        covidCenter.setCenterName(object.get(key).toString());
                    }
                    if (key.equals("lat")) {
                        covidCenter.setLat(object.get(key).toString());
                    }
                    if (key.equals("lng")) {
                        covidCenter.setLng(object.get(key).toString());
                    }
                }
                covidCenterRepository.save(covidCenter.toEntity());
            }
        } else {    // 기존 센터가 있는 경우
            if (jsonArray.size() > covidCenterList.size()) {    // 센터 추가됨
                for(int i = covidCenterList.size(); i < jsonArray.size(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    for (Object key : object.keySet()) {
                        if (key.equals("id")) {
                            covidCenter.setId(Long.valueOf(object.get(key).toString()));
                        }
                        if (key.equals("address")) {
                            covidCenter.setAddress(object.get(key).toString());
                        }
                        if (key.equals("centerName")) {
                            covidCenter.setCenterName(object.get(key).toString());
                        }
                        if (key.equals("lat")) {
                            covidCenter.setLat(object.get(key).toString());
                        }
                        if (key.equals("lng")) {
                            covidCenter.setLng(object.get(key).toString());
                        }
                    }
                    covidCenterRepository.save(covidCenter.toEntity());
                }
            }
        }
    }


    @Test
    public void covidCityTest() throws Exception {
        covidCityService.covidCity();
    }

}
