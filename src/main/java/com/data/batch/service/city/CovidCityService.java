package com.data.batch.service;


import com.data.batch.domain.city.CovidCity;
import com.data.batch.repository.city.CovidCityRepository;
import com.data.batch.repository.vaccine.VaccineStatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class CovidCityService {

    private static final Logger logger = LoggerFactory.getLogger(CovidCityService.class);

    private final CovidCityRepository covidCityRepository;

    private final VaccineStatRepository vaccineStatRepository;

    @Transactional
    public void covidCity() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String replaceDate = date.replaceAll("-","");

        String stringBuilder = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson" + "?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=blJmO7wRJvddq%2F8lUa%2B2e27dAJ%2BL9V1iDZnDtAWeX2DJHzbhtk9%2FkXE%2F%2FuBk5HkReIVfQXs34a4ezLdoE9dSoA%3D%3D" +
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=1" +
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=19" +
                "&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + replaceDate +
                "&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + replaceDate;

        URL url = new URL(stringBuilder);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            result.append(line.trim());
        }

        InputSource inputSource = new InputSource(new StringReader(result.toString()));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);

        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document doc = builder.parse(inputSource);

        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("//item");

        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        CovidCity covidCity = new CovidCity();

        for (int i=0; i<nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            for (int j=0; j<child.getLength(); j++) {
                Node node = child.item(j);

                if ("gubun".equals(node.getNodeName())) {
                    covidCity.setGubun(node.getTextContent());
                };

                if ("defCnt".equals(node.getNodeName())) {
                    covidCity.setDefCnt(node.getTextContent());
                };

                if ("incDec".equals(node.getNodeName())) {
                    covidCity.setIncDec(node.getTextContent());
                };

                if ("deathCnt".equals(node.getNodeName())) {
                    covidCity.setDeathCnt(node.getTextContent());
                };

                if ("isolIngCnt".equals(node.getNodeName())) {
                    covidCity.setIsolIngCnt(node.getTextContent());
                };

                if ("isolClearCnt".equals(node.getNodeName())) {
                    covidCity.setIsolClearCnt(node.getTextContent());
                };

                if ("qurRate".equals(node.getNodeName())) {
                    covidCity.setQurRate(node.getTextContent());
                };

                if ("stdDay".equals(node.getNodeName())) {
                    covidCity.setStdDay(node.getTextContent());
                };
            }
            covidCityRepository.save(covidCity.toEntity());
        }
    }
}
