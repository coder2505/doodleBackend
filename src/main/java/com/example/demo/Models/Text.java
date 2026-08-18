package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "text_table")
public class Text {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int textid;

    String text;
    String background;
    String font;

    @OneToOne(mappedBy = "text")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Room room;

}
