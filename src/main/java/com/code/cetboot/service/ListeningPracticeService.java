package com.code.cetboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.ListeningPractice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.cetboot.vo.listening.ListeningPracticeVO;

/**
* @author 19735
* @description 针对表【listening_practice】的数据库操作Service
* @createDate 2025-04-26 01:53:46
*/
public interface ListeningPracticeService extends IService<ListeningPractice> {

    /**
     * 获取听力练习题
     *
     * @param pageDto 分页参数
     * @param keyword 关键字
     * @return Result
     */
    Result getPractices(Page<ListeningPractice> pageDto, String keyword);

    /**
     * 提交听力练习题
     *
     * @param listeningPracticeId 听力练习题ID
     * @return Result
     */
    Result getPractice(Integer listeningPracticeId);

    /**
     * 提交听力练习题
     *
     * @param listeningPracticeDTO 听力练习题DTO
     * @return Result
     */
    Result submit(ListeningPracticeDTO listeningPracticeDTO);

    /**
     * 获取听力练习记录
     *
     * @param pageDto 分页参数
     * @return Result
     */
    Result getRecords(Page<ListeningPracticeVO> pageDto);

    /**
     * 获取听力练习记录
     *
     * @param listeningPracticeId 听力练习题ID
     * @return Result
     */
    Result getRecord(Integer listeningPracticeId);
}
