package com.winter.yuso.model.dto.picture;

import com.winter.yuso.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @program: yuso-backend
 * @description: 图片查询类
 * @author: Mr.Ye
 * @create: 2023-11-27 21:50
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -1367754193471242626L;

    private String searchText;
}
