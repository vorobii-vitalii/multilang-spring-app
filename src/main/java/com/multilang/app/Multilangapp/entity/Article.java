package com.multilang.app.Multilangapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "translated_title")
    private TranslatedText translatedTitle;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "translated_body")
    private TranslatedText translatedBody;

    @Column(name = "created_at")
    private Date createdAt;

}
