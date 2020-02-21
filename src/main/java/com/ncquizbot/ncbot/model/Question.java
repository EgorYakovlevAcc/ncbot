package com.ncquizbot.ncbot.model;

import com.google.inject.internal.asm.$Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Lob
    private String content;
    @OneToOne(mappedBy="question")
    private Answer answer;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Option> options;

}
