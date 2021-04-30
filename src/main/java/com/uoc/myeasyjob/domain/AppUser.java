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
public class AppUser implements Serializable {

    private static final long serialVersionUID = -547578759801472949L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_app_user", nullable = false)
    private Integer idAppUser;

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Basic
    @Column(name = "image", nullable = true, length = -1)
    private byte[] image;

    @Basic
    @Column(name = "phone", nullable = true, length = -1)
    private String phone;
}
