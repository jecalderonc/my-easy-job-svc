package com.uoc.myeasyjob.rest.model;

import com.uoc.myeasyjob.domain.Category;
import com.uoc.myeasyjob.domain.City;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.domain.PostImage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Model class that represents the PostCardView Object.
 */
@Getter
@Setter
public class PostCardView extends Post implements Serializable {

    private static final long serialVersionUID = 2736280577864081128L;
    private City city;
    private Category category;
    private RatingView ratingView;
    private UserView userView;
    private List<PostImage> postImageList;
    private PostImage mainPostImage;

    public PostCardView(Post post, City city, Category category, RatingView ratingView, UserView userView) {
        super(post);
        this.city = city;
        this.category = category;
        this.ratingView = ratingView;
        this.userView = userView;
    }

    public PostCardView(Post post, City city, Category category, RatingView ratingView, UserView userView, PostImage postImage) {
        super(post);
        this.city = city;
        this.category = category;
        this.ratingView = ratingView;
        this.userView = userView;
        this.mainPostImage = postImage;
    }
}
