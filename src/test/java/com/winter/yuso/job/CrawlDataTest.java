package com.winter.yuso.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.winter.yuso.model.vo.PictureVO;
import com.winter.yuso.model.entity.Post;
import com.winter.yuso.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: yuso-backend
 * @description: 数据爬取测试
 * @author: Mr.Ye
 * @create: 2023-11-26 09:29
 **/
@SpringBootTest
public class CrawlDataTest {
    @Resource
    private PostService postService;

    @Test
    void testCrawlPictures() throws IOException {
        String url = "https://cn.bing.com/images/search?q=小黑子&form=HDRSC2&first=1";
        Document doc = Jsoup.connect(url).get();
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
        }
        System.out.println(pictureList);
    }

    @Test
    void testCrawlArticles() {
        // 准备请求体，抓取数据
        String json = "{\n" +
                "\t\"current\": 2,\n" +
                "\t\"pageSize\": 8,\n" +
                "\t\"sortField\": \"createTime\",\n" +
                "\t\"sortOrder\": \"descend\",\n" +
                "\t\"category\": \"文章\",\n" +
                "\t\"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
        // 数据处理 + 入库
        List<Post> postList = new ArrayList<>();
        Map<String, Object> articleMap = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) articleMap.get("data");
        JSONArray records = (JSONArray) data.get("records");
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            post.setTags(tempRecord.getStr("tags"));
            post.setUserId(1712099913634193409L);
            postList.add(post);
        }
//        System.out.println(postList);
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }
}
