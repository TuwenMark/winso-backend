package com.winter.yuso.model.dto.search;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yuso-backend
 * @description: 图片查询类
 * @author: Mr.Ye
 * @create: 2023-11-27 21:50
 **/
@Data
public class SearchRequest implements Serializable {
    private static final long serialVersionUID = -3786880907056770116L;
    private String searchText;
}
