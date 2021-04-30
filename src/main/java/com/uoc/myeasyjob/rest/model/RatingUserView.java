package com.uoc.myeasyjob.rest.model;

import com.uoc.myeasyjob.domain.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Model class that represents the RatingUserView Object.
 */
@Getter
@Setter
@NoArgsConstructor
public class RatingUserView implements Serializable {

    private static final long serialVersionUID = 2560037398330950142L;
    private Integer idAppUser;
    private String name;

    public RatingUserView(AppUser appUser){
        this.idAppUser = appUser.getIdAppUser();
        this.name = appUser.getName();
    }
}
