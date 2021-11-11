package com.data.batch.domain.city;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class CovidCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "covid_city_id")
    private Long id;

    @Column
    private String gubun; // 시/도 이름

    @Column
    private String defCnt; // 누적확진자

    @Column
    private String incDec; // 전일대비 증가수

    @Column
    private String deathCnt; // 사망자

    @Column
    private String isolIngCnt; // 격리중 환자

    @Column
    private String isolClearCnt; // 격리해제 수

    @Column
    private String qurRate; // 10만명당 발생 비율

    @Column
    private String stdDay; // 기준 일시

    public CovidCity(Long id, String gubun, String defCnt, String incDec, String deathCnt, String isolIngCnt, String isolClearCnt, String qurRate, String stdDay) {
        this.id = id;
        this.gubun = gubun;
        this.defCnt = defCnt;
        this.incDec = incDec;
        this.deathCnt = deathCnt;
        this.isolIngCnt = isolIngCnt;
        this.isolClearCnt = isolClearCnt;
        this.qurRate = qurRate;
        this.stdDay = stdDay;
    }

    public CovidCity toEntity() {
        return CovidCity.builder()
                .id(this.id)
                .gubun(this.gubun)
                .defCnt(this.defCnt)
                .incDec(this.incDec)
                .deathCnt(this.deathCnt)
                .isolIngCnt(this.isolIngCnt)
                .isolClearCnt(this.isolClearCnt)
                .qurRate(this.qurRate)
                .stdDay(this.stdDay)
                .build();
    }
}
