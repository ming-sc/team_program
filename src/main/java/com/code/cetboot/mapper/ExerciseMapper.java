package com.code.cetboot.mapper;

import com.code.cetboot.entity.Exercise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.cetboot.vo.ExerciseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 19735
* @description 针对表【exercise】的数据库操作Mapper
* @createDate 2025-04-26 01:53:30
* @Entity com.code.cetboot.entity.Exercise
*/
@Mapper
public interface ExerciseMapper extends BaseMapper<Exercise> {

    /**
     * 根据听力练习题 ID 查询对应的练习题
     *
     * @param listeningPracticeId 听力练习题 ID
     * @return 练习题列表
     */
    List<ExerciseVO> selectExerciseByListeningPracticeId(@Param("needAnswer") boolean needAnswer, @Param("listeningPracticeId") Integer listeningPracticeId);


    /**
     * 根据听力练习题 ID 查询对应的练习题
     *
     * @param listeningPracticeId 听力练习题 ID
     * @return 练习题列表
     */
    @Select("SELECT *" +
            "FROM listening_to_exercise lte " +
            "LEFT JOIN exercise e ON lte.exercise_id = e.exercise_id " +
            "WHERE lte.listening_practice_id = #{listeningPracticeId}")
    List<Exercise> selectExerciseByListeningPracticeId(@Param("listeningPracticeId") Integer listeningPracticeId);

    /**
     * 根据阅读练习题 ID 查询对应的练习题
     * <p>
     * TODO: 和上面的方法重复度很高，考虑是否需要合并
     * @param readingPracticeId 阅读练习题 ID
     * @return 练习题列表
     */
    List<ExerciseVO> selectExerciseByReadingPracticeId(@Param("needAnswer") boolean needAnswer, @Param("readingPracticeId") Integer readingPracticeId);
}




