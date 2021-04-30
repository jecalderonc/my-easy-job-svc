package com.uoc.myeasyjob.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uoc.myeasyjob.rest.model.RatingUserView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Rating implements Serializable {

    private static final long serialVersionUID = 5327687911448411538L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rating", nullable = false)
    private Integer idRating;

    @Basic
    @Column(name = "score", nullable = true)
    private Integer score;

    @Basic
    @Column(name = "comment", nullable = true, length = -1)
    private String comment;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "id_qualifier_user", nullable = false)
    private Integer idQualifierUser;

    @Basic
    @Column(name = "date", nullable = false, length = -1)
    private Date date;

    @JsonInclude()
    @Transient
    private RatingUserView qualifierRatingUserView;
}
