package com.data.batch.domain.vaccine;

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
public class VaccineStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_stat_id")
    private Long id;

    @Column
    private String baseDate;

    @Column
    private String sido;

    @Column
    private String firstCnt;

    @Column
    private String secondCnt;

    @Column
    private String totalFirstCnt;

    @Column
    private String totalSecondCnt;

    @Column
    private String accumulatedFirstCnt;

    @Column
    private String accumulatedSecondCnt;

    public VaccineStat(Long id, String baseDate, String sido, String firstCnt, String secondCnt, String totalFirstCnt, String totalSecondCnt, String accumulatedFirstCnt, String accumulatedSecondCnt) {
        this.id = id;
        this.baseDate = baseDate;
        this.sido = sido;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
    }

    public VaccineStat toEntity() {
        return VaccineStat.builder()
                .id(this.id)
                .baseDate(baseDate)
                .sido(this.sido)
                .firstCnt(this.firstCnt)
                .secondCnt(this.secondCnt)
                .totalFirstCnt(this.totalFirstCnt)
                .totalSecondCnt(this.totalSecondCnt)
                .accumulatedFirstCnt(this.accumulatedFirstCnt)
                .accumulatedSecondCnt(this.accumulatedSecondCnt)
                .build();
    }
}
