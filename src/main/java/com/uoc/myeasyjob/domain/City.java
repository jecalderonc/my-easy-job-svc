package com.uoc.myeasyjob.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class City implements Serializable {

    private static final long serialVersionUID = 2594808560841891610L;
    @Id
    @Column(name = "id_city", nullable = false)
    private Integer idCity;

    @Basic
    @Column(name = "city_name", nullable = false, length = -1)
    private String cityName;

    @JsonInclude()
    @Transient
    private Integer activeCount;
}
