package com.uoc.myeasyjob.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post implements Serializable {

    private static final long serialVersionUID = -8438528855566922996L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_post", nullable = false)
    private Integer idPost;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "id_category", nullable = false)
    private Integer idCategory;

    @Column(name = "id_city", nullable = false)
    private Integer idCity;

    @Basic
    @Column(name = "title", nullable = false, length = -1)
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Basic
    @Column(name = "date", nullable = false)
    private Date date;

    @Basic
    @Column(name = "location_latitude", nullable = true, length = -1)
    private String locationLatitude;

    @Basic
    @Column(name = "location_longitude", nullable = true, length = -1)
    private String locationLongitude;

    @JsonInclude()
    @Transient
    private List<PostImage> postImageList;

    @JsonInclude()
    @Transient
    private PostImage mainPostImage;

    public Post(Post post) {
        this.idPost = post.getIdPost();
        this.idUser = post.getIdUser();
        this.idCategory = post.getIdCategory();
        this.idCity = post.getIdCity();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.date = post.getDate();
        this.locationLatitude = post.getLocationLatitude();
        this.locationLongitude = post.getLocationLongitude();
    }
}
