package project.diary.domain.advice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Advice {

    @Id
    private String adviceId;
    private String adviceContent;
    private String adviceEmotion;

}