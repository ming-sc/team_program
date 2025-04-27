package com.code.cetboot.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> {

    private long total;

    private long current;

    private long size;

    private long pages;

    private List<T> records;

    public static <T> PageResult<T> of(Page<T> page) {
        return PageResult.<T>builder()
                .total(page.getTotal())
                .current(page.getCurrent())
                .size(page.getSize())
                .pages(page.getPages())
                .records(page.getRecords())
                .build();
    }
}
