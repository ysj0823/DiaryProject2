package project.diary.domain.diary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.diary.dto.diary.DiaryUpdateRequestDTO;
import project.diary.dto.user.UserUpdateRequestDto;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Diary {

    @Id
    private int diary_id;
    private String diary_user;
    private String diary_weather;
    private String diary_emotion;
    private String diary_title;
    private String diary_content;

    @Builder
    public Diary(int diary_id, String diary_user, String diary_weather, String diary_emotion, String diary_title, String diary_content) {
        this.diary_id = diary_id;
        this.diary_user = diary_user;
        this.diary_weather = diary_weather;
        this.diary_emotion = diary_emotion;
        this.diary_title = diary_title;
        this.diary_content = diary_content;

    }

    public void diaryUpdate(DiaryUpdateRequestDTO diaryUpdateRequestDTO) {
        this.diary_weather = diaryUpdateRequestDTO.getDiary_weather();
        this.diary_emotion = diaryUpdateRequestDTO.getDiary_emotion();
        this.diary_title = diaryUpdateRequestDTO.getDiary_title();
        this.diary_content = diaryUpdateRequestDTO.getDiary_content();
    }
}
