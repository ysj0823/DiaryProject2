package project.diary.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.diary.domain.diary.Diary;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDTO {

    private int diaryId;
    private String diaryUser;
    private String diaryWeather;
    private String diaryEmotion;
    private String diaryTitle;
    private String diaryContent;

    public DiaryResponseDTO(Diary entity){
        this.diaryId = entity.getDiaryId();
        this.diaryUser = entity.getDiaryUser();
        this.diaryWeather = entity.getDiaryWeather();
        this.diaryEmotion = entity.getDiaryEmotion();
        this.diaryTitle = entity.getDiaryTitle();
        this.diaryContent = entity.getDiaryContent();
    }
}