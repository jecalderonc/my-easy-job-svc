package com.uoc.myeasyjob.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class PostImage implements Serializable {

    private static final long serialVersionUID = 5848463446494928971L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post_image", nullable = false)
    private Integer idPostImage;

    @Basic
    @Column(name = "id_post", nullable = false)
    private Integer idPost;

    @Basic
    @Column(name = "priority")
    private Integer priority;


    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Basic
    @Column(name = "image", nullable = true, length = -1)
    private byte[] image;
}
