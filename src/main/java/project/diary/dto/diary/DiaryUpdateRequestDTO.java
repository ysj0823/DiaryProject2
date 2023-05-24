package project.diary.dto.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryUpdateRequestDTO {
    private String diary_weather;
    private String diary_emotion;
    private String diary_title;
    private String diary_content;
}
