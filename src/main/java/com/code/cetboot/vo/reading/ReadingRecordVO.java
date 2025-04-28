package com.code.cetboot.vo.reading;

import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ReadingRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingRecordVO {

    private List<ExerciseRecord> exerciseRecords;

    private ReadingPracticeVO readingPractice;

    private ReadingRecord readingRecord;
}
