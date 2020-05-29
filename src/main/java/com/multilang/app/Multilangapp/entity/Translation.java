package com.multilang.app.Multilangapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "translation")
@Data
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translated_text_id")
    private TranslatedText translatedText;

    @Column(name = "text")
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locate_id")
    private Locate locate;

}
