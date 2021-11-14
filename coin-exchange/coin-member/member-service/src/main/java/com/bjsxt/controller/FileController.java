package com.bjsxt.controller;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;
import com.bjsxt.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private OSSClient ossClient;

    /**
     * web 直传的实现
     * 1 前台访问后台获取票据
     * 2 前台使用该票据直接去上传图片
     * https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.15.16076e28Cw94Ga#concept-ahk-rfz-2fb
     *
     * @return
     */
    @GetMapping("/image/pre/upload")
    @ApiOperation(value = "获取一个上传的票据")
    public R<Map<String, String>> asyncUpload() {
        String dir = DateUtil.today().replaceAll("-", "/");
        Map<String, String> uploadPolicy = getUploadPolicy(30L,3*1024*1024L,dir,"");
        return R.ok(uploadPolicy);
    }


    /**
     * 获取上传的票据
     *
     * @param expireTime  票据的过期时间
     * @param dir         文件上传的文件夹
     * @param maxFileSize 上传文件的最大体积
     * @param callbackUrl 前端把文件上传成功后的回调地址
     * @return 票据
     */
    private Map<String, String> getUploadPolicy(Long expireTime, Long maxFileSize, String dir, String callbackUrl) {
        // 返回给前端的值
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime); // 过期时间
        // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
        PolicyConditions policyConds = new PolicyConditions();
        // 设置上传的大小 ,最大为3M
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 3 * 1024 * 1024);
        // 上传的到那个文件夹
       // policyConds.addConditionItem(ExampleMatcher.MatchMode, PolicyConditions.COND_KEY, dir);
        try {
            // 生成一个票据
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);

            // 对票据加密
            byte[] binaryData = new byte[0];

            binaryData = postPolicy.getBytes("utf-8");

            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            //respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            //respMap.put("host", "https://" + bucketName + "." + endPoint); // host的格式为 https://bucketname.endpoint
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("callbackUrl", callbackUrl); // 前端上传成功了,可以使用该地址来通知后端.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return respMap;
    }
}
