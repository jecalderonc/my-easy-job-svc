package com.uoc.myeasyjob.rest.model;

import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Rating;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Model class that represents the User view Object.
 */
@Getter
@Setter
public class UserView implements Serializable {

    private static final long serialVersionUID = 6716330222026691618L;
    private Integer idAppUser;
    private String name;
    private String email;
    private byte[] image;
    private String description;
    private String phone;
    private RatingView ratingView;
    private List<Rating> ratingList;

    public UserView(AppUser appUser){
        this.idAppUser = appUser.getIdAppUser();
        this.name = appUser.getName();
        this.email = appUser.getEmail();
        this.image = appUser.getImage();
        this.description = appUser.getDescription();
        this.phone = appUser.getPhone();
    }
}
