package com.winter.yuso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winter.yuso.model.dto.picture.PictureQueryRequest;
import com.winter.yuso.model.vo.PictureVO;

/**
 * @program: yuso-backend
 * @description: 图片处理
 * @author: Mr.Ye
 * @create: 2023-11-27 21:46
 **/
public interface PictureService {
    Page<PictureVO> getPictureVOPage(PictureQueryRequest pictureQueryRequest);
}
