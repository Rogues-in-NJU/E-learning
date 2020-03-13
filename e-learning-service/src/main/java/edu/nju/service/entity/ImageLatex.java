package edu.nju.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@Entity
@Table(name = "img")
@NoArgsConstructor
@AllArgsConstructor
public class ImageLatex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;

    private String imageLatex;

}
