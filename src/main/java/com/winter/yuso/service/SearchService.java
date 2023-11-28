package com.winter.yuso.service;

import com.winter.yuso.model.dto.search.SearchRequest;
import com.winter.yuso.model.vo.SearchVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: yuso-backend
 * @description: 聚合搜索业务层
 * @author: Mr.Ye
 * @create: 2023-11-28 21:23
 **/
public interface SearchService {

    SearchVO listSearchVOByPage(SearchRequest searchRequest, HttpServletRequest request);

    SearchVO listSearchVOByPageSync(SearchRequest searchRequest, HttpServletRequest request);
}
