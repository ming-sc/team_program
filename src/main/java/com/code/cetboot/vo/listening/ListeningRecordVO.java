package com.code.cetboot.vo.listening;

import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ListeningRecord;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ListeningRecordVO {

    private List<ExerciseRecord> exerciseRecords;
    private ListeningPracticeVO listeningPractice;
    private ListeningRecord listeningRecord;
}
