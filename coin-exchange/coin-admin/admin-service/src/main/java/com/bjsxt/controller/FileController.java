package com.bjsxt.controller;

import cn.hutool.core.date.DateUtil;
import com.bjsxt.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api(tags = "文件上传控制器")
public class FileController {

    //@Autowired
    //private OSS ossClient;

    /*@Value("${oss.bucket.name:coin-exchange-imgs}")
    private String bucketName;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endPoint;*/

    @PostMapping("/image/AliYunImgUpload")
    @ApiOperation(value = "上传图像文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "要上传的文件")
    })
    public R<String> fileUpload(MultipartFile file) throws IOException {
        String fileName = DateUtil.today().replaceAll("-","/") + "/" +file.getOriginalFilename();
        //ossClient.putObject("bucketName",fileName,file.getInputStream());
        return R.ok("访问图片的url地址");
    }
}
