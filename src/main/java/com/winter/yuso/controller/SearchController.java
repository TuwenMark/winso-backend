package com.winter.yuso.controller;

import com.winter.yuso.common.BaseResponse;
import com.winter.yuso.common.ResultUtils;
import com.winter.yuso.model.dto.search.SearchRequest;
import com.winter.yuso.model.vo.SearchVO;
import com.winter.yuso.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: yuso-backend
 * @description: 聚合搜索接口类
 * @author: Mr.Ye
 * @create: 2023-11-28 20:47
 **/
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("/list/page/all")
    public BaseResponse<SearchVO> listSearchVOByPage(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        SearchVO searchVO = searchService.listSearchVOByPage(searchRequest, request);
        return ResultUtils.success(searchVO);
    }

    @PostMapping("/list/page/all/sync")
    public BaseResponse<SearchVO> listSearchVOByPageSync(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        SearchVO searchVO = searchService.listSearchVOByPageSync(searchRequest, request);
        return ResultUtils.success(searchVO);
    }
}
