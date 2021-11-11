package com.data.batch;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;


@AutoConfigureMockMvc
@SpringBootTest
class BatchApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BatchApplicationTests.class);

    @Test
    void contextLoads() {
    }


    @Test
    public void 날짜_Test() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        logger.info("date : {}, SystemTime : {}", date, System.currentTimeMillis());
    }
}
