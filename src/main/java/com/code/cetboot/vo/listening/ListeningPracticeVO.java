package com.code.cetboot.vo.listening;

import com.code.cetboot.entity.ListeningPractice;
import com.code.cetboot.vo.ExerciseVO;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ListeningPracticeVO {

    /**
     * 听力音频地址
     */
    private String audio;

    /**
     * 题目数量
     */
    private Integer exerciseCount;

    private List<ExerciseVO> exercises;

    /**
     * 听力练习id
     */
    private Integer listeningPracticeId;

    /**
     * 听力练习标题
     */
    private String title;

    public static ListeningPracticeVO from(ListeningPractice listeningPractice) {
        ListeningPracticeVO listeningPracticeVO = new ListeningPracticeVO();
        listeningPracticeVO.setListeningPracticeId(listeningPractice.getListeningPracticeId());
        listeningPracticeVO.setTitle(listeningPractice.getTitle());
        listeningPracticeVO.setAudio(listeningPractice.getAudio());
        listeningPracticeVO.setExerciseCount(listeningPractice.getExerciseCount());
        return listeningPracticeVO;
    }
}
