package com.uoc.myeasyjob.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Model class that represents the RatingView Object.
 */
@Getter
@Setter
@AllArgsConstructor
public class RatingView implements Serializable {

    private static final long serialVersionUID = 8461387585929724463L;
    private Integer quantity;
    private Double score;
}
