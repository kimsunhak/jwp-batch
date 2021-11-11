package com.data.batch.domain.center;


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
public class CovidCenter {

    @Id
    @Column(name = "covid_center_id")
    private Long id;

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String centerName; // 접종센터 이름

    @Column(nullable = false)
    private String lat; // 위도

    @Column(nullable = false)
    private String lng; // 경도

    public CovidCenter(Long id, String address, String centerName, String lat, String lng) {
        this.id = id;
        this.address = address;
        this.centerName = centerName;
        this.lat = lat;
        this.lng = lng;
    }

    public CovidCenter toEntity() {
        return CovidCenter.builder()
                .id(this.id)
                .address(this.address)
                .centerName(this.centerName)
                .lat(this.lat)
                .lng(this.lng)
                .build();
    }
}
