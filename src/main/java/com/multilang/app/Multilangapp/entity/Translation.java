package com.multilang.app.Multilangapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "translation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locate_id")
    private Locate locate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translated_text_id")
    private TranslatedText translatedText;

}
