package com.winter.yuso.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: yuso-backend
 * @description: 聚合搜索VO
 * @author: Mr.Ye
 * @create: 2023-11-28 20:48
 **/
@Data
public class SearchVO implements Serializable {

    private static final long serialVersionUID = 2388654800574009589L;

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<PictureVO> pictureList;
}
