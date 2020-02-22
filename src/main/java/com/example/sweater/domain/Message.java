package com.example.sweater.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "author"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    @NonNull
    @NotBlank(message = "Please fill the message!")
    @Length(max = 2048, message = "Message's to long!")
    private String text;
    @Column(name = "tag")
    @NonNull
    @Length(max = 255, message = "Tag's to long!")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "filename")
    private String filename;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }
}