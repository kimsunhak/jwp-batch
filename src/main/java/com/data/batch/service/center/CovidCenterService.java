package com.data.batch.service.center;


import com.data.batch.domain.center.CovidCenter;
import com.data.batch.repository.center.CovidCenterRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CovidCenterService {

    private static final Logger logger = LoggerFactory.getLogger(CovidCenterService.class);

    private final CovidCenterRepository covidCenterRepository;

    @Transactional
    public void covidCenter() throws Exception {
        String covidCenterUrl = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=300&returnType=json&serviceKey=S6i7QaD51JF7cr%2FOd3%2FGMKn6kTco2c6OcvtH3r8eAGLtGAxr4faGU7DuMiCwbOUX5uJMqauADzhGwugU9e7s7w%3D%3D";

        URL url = new URL(covidCenterUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
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
        } else {
            // 기존 센터가 있는 경우
            for (int i = 0; i < (jsonArray.size() <= covidCenterList.size() ? jsonArray.size() : covidCenterList.size()); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (!object.get("id").equals(covidCenterList.get(i).getId()) && !object.get("address").toString().equals(covidCenterList.get(i).getAddress())) {
                    covidCenterList.get(i).setId(Long.valueOf(object.get("id").toString()));
                    covidCenterList.get(i).setAddress(object.get("address").toString());
                    covidCenterList.get(i).setCenterName(object.get("centerName").toString());
                    covidCenterList.get(i).setLat(object.get("lat").toString());
                    covidCenterList.get(i).setLng(object.get("lng").toString());
                    covidCenterRepository.save(covidCenterList.get(i));
                }
            }

            if (jsonArray.size() < covidCenterList.size()) {
                for (int j = jsonArray.size(); j < covidCenterList.size(); j++) {
                    covidCenterRepository.deleteById(covidCenterList.get(j).getId());
                }
            } else if (covidCenterList.size() < jsonArray.size()) {
                for (int k = covidCenterList.size(); k < jsonArray.size(); k++) {
                    JSONObject object = (JSONObject) jsonArray.get(k);
                    covidCenter.setId(Long.valueOf(object.get("id").toString()));
                    covidCenter.setAddress(object.get("address").toString());
                    covidCenter.setCenterName(object.get("centerName").toString());
                    covidCenter.setLat(object.get("lat").toString());
                    covidCenter.setLng(object.get("lng").toString());
                    covidCenterRepository.save(covidCenter.toEntity());
                }
            }
        }
    }


    @Transactional
    public void tempCovidCenter() throws Exception {
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
                    logger.info("전체카운트 : {}", covidCenterList.get(i));
                    covidCenterRepository.save(covidCenterList.get(i));
                }
            }
            logger.info("Size : {}", covidCenterList.size());

            if (jsonArray.size() < covidCenterList.size()) {
                for (int j = jsonArray.size(); j < covidCenterList.size(); j++) {
                    covidCenterRepository.deleteById(covidCenterList.get(j).getId());
                }
            } else if (covidCenterList.size() < jsonArray.size()) {
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

}
