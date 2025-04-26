package com.code.cetboot.vo.reading;

import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ReadingRecord;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ReadingRecordVO {

    private List<ExerciseRecord> exerciseRecords;

    private ReadingPracticeVO readingPractice;

    private ReadingRecord readingRecord;
}
