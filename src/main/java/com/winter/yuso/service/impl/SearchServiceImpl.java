package com.winter.yuso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winter.yuso.common.ErrorCode;
import com.winter.yuso.exception.BusinessException;
import com.winter.yuso.model.dto.picture.PictureQueryRequest;
import com.winter.yuso.model.dto.post.PostQueryRequest;
import com.winter.yuso.model.dto.search.SearchRequest;
import com.winter.yuso.model.dto.user.UserQueryRequest;
import com.winter.yuso.model.vo.PictureVO;
import com.winter.yuso.model.vo.PostVO;
import com.winter.yuso.model.vo.SearchVO;
import com.winter.yuso.model.vo.UserVO;
import com.winter.yuso.service.PictureService;
import com.winter.yuso.service.PostService;
import com.winter.yuso.service.SearchService;
import com.winter.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @program: yuso-backend
 * @description: 聚合搜索业务层
 * @author: Mr.Ye
 * @create: 2023-11-28 21:24
 **/
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    @Override
    public SearchVO listSearchVOByPage(SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        // 搜索贴子
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        // 搜索图片
        PictureQueryRequest pictureQueryRequest = new PictureQueryRequest();
        pictureQueryRequest.setSearchText(searchText);
        Page<PictureVO> pictureVOPage = pictureService.getPictureVOPage(pictureQueryRequest);

        // 搜索用户
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        SearchVO searchVO = new SearchVO();
        searchVO.setUserList(userVOPage.getRecords());
        searchVO.setPostList(postVOPage.getRecords());
        searchVO.setPictureList(pictureVOPage.getRecords());
        return searchVO;
    }

    /**
     * 异步请求
     *
     * @param searchRequest
     * @param request
     * @return
     */
    @Override
    public SearchVO listSearchVOByPageSync(SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            // 搜索贴子
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
            return postVOPage;
        });

        CompletableFuture<Page<PictureVO>> pictureTask = CompletableFuture.supplyAsync(() -> {
            // 搜索图片
            PictureQueryRequest pictureQueryRequest = new PictureQueryRequest();
            pictureQueryRequest.setSearchText(searchText);
            Page<PictureVO> pictureVOPage = pictureService.getPictureVOPage(pictureQueryRequest);
            return pictureVOPage;
        });

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            // 搜索用户
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
            return userVOPage;
        });

        SearchVO searchVO = new SearchVO();
        try {
            searchVO.setUserList(userTask.get().getRecords());
            searchVO.setPostList(postTask.get().getRecords());
            searchVO.setPictureList(pictureTask.get().getRecords());
        } catch (Exception e) {
            log.error("异步查询数据库报错", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询错误");
        }
        return searchVO;
    }
}
