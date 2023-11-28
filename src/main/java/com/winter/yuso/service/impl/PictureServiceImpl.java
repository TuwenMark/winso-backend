package com.winter.yuso.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winter.yuso.common.ErrorCode;
import com.winter.yuso.exception.BusinessException;
import com.winter.yuso.model.dto.picture.PictureQueryRequest;
import com.winter.yuso.model.vo.PictureVO;
import com.winter.yuso.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: yuso-backend
 * @description: 图片处理类
 * @author: Mr.Ye
 * @create: 2023-11-27 21:53
 **/
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<PictureVO> getPictureVOPage(PictureQueryRequest pictureQueryRequest) {
        String searchText = pictureQueryRequest.getSearchText();
        long pageNum = pictureQueryRequest.getPageNum();
        long pageSize = pictureQueryRequest.getPageSize();
        long first = (pageNum - 1) * pageSize + 1;
        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d", searchText, first);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Elements elements = doc.select(".iuscp.isv");
        List<PictureVO> pictureList = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map mMap = JSONUtil.toBean(m, Map.class);
            PictureVO picture = new PictureVO();
            picture.setTitle(title);
            picture.setUrl((String) mMap.get("murl"));
            pictureList.add(picture);
            if (pictureList.size() >= pageSize) {
                break;
            }
        }
        Page<PictureVO> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
