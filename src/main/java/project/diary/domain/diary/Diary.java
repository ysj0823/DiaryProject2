package project.diary.domain.diary;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.diary.dto.diary.DiaryUpdateRequestDTO;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Diary {

    @Id
    private int diaryId;
    private String diaryUser;
    private String diaryWeather;
    private String diaryEmotion;
    private String diaryTitle;
    private String diaryContent;

    @Builder
    public Diary(int diaryId, String diaryUser, String diaryWeather, String diaryEmotion, String diaryTitle, String diaryContent) {
        this.diaryId = diaryId;
        this.diaryUser = diaryUser;
        this.diaryWeather = diaryWeather;
        this.diaryEmotion = diaryEmotion;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;

    }

    public void diaryUpdate(DiaryUpdateRequestDTO diaryUpdateRequestDTO) {
        this.diaryWeather = diaryUpdateRequestDTO.getDiaryWeather();
        this.diaryEmotion = diaryUpdateRequestDTO.getDiaryEmotion();
        this.diaryTitle = diaryUpdateRequestDTO.getDiaryTitle();
        this.diaryContent = diaryUpdateRequestDTO.getDiaryContent();
    }
}