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
public class Category implements Serializable {

    private static final long serialVersionUID = 2378005785292345708L;
    @Id
    @Column(name = "id_category", nullable = false)
    private Integer idCategory;

    @Basic
    @Column(name = "category_name", nullable = false, length = -1)
    private String categoryName;

    @JsonInclude()
    @Transient
    private Integer activeCount;

}
