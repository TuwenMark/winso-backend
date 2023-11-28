package com.winter.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winter.yuso.common.BaseResponse;
import com.winter.yuso.common.ErrorCode;
import com.winter.yuso.common.ResultUtils;
import com.winter.yuso.exception.BusinessException;
import com.winter.yuso.exception.ThrowUtils;
import com.winter.yuso.model.dto.picture.PictureQueryRequest;
import com.winter.yuso.model.vo.PictureVO;
import com.winter.yuso.service.PictureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: yuso-backend
 * @description: 图片接口
 * @author: Mr.Ye
 * @create: 2023-11-27 22:08
 **/
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<PictureVO>> listPictureVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        ThrowUtils.throwIf(pictureQueryRequest == null, new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空"));
        Page<PictureVO> pictureVOPage = pictureService.getPictureVOPage(pictureQueryRequest);
        return ResultUtils.success(pictureVOPage);
    }
}
