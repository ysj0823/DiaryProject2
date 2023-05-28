package project.diary.dto.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryUpdateRequestDTO {
    private String diaryWeather;
    private String diaryEmotion;
    private String diaryTitle;
    private String diaryContent;
}