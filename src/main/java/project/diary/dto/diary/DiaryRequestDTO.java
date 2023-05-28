package project.diary.dto.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.diary.domain.diary.Diary;

@Getter
@Setter
@NoArgsConstructor
public class DiaryRequestDTO {

    private int diaryId;
    private String diaryUser;
    private String diaryWeather;
    private String diaryEmotion;
    private String diaryTitle;
    private String diaryContent;

    public DiaryRequestDTO(Diary entity){
        this.diaryId = entity.getDiaryId();
        this.diaryUser = entity.getDiaryUser();
        this.diaryWeather = entity.getDiaryWeather();
        this.diaryEmotion = entity.getDiaryEmotion();
        this.diaryTitle = entity.getDiaryTitle();
        this.diaryContent = entity.getDiaryContent();
    }

    public void setDiaryUser(String diaryUser){
        this.diaryUser = diaryUser;
    }
}