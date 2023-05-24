package project.diary.dto.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.diary.domain.diary.Diary;
import project.diary.domain.user.User;

@Getter
@Setter
@NoArgsConstructor
public class DiaryRequestDTO {

    private int diary_id;
    private String diary_user;
    private String diary_weather;
    private String diary_emotion;
    private String diary_title;
    private String diary_content;

    public DiaryRequestDTO(Diary entity){
        this.diary_id = entity.getDiary_id();
        this.diary_user = entity.getDiary_user();
        this.diary_weather = entity.getDiary_weather();
        this.diary_emotion = entity.getDiary_emotion();
        this.diary_title = entity.getDiary_title();
        this.diary_content = entity.getDiary_content();
    }
}
