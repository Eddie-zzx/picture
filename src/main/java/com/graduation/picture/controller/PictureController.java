package com.graduation.picture.controller;

import com.graduation.picture.annotation.AuthCheck;
import com.graduation.picture.common.BaseResponse;
import com.graduation.picture.common.ResultUtils;
import com.graduation.picture.constant.UserConstant;
import com.graduation.picture.model.dto.PictureUploadDTO;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.PictureVO;
import com.graduation.picture.service.PictureService;
import com.graduation.picture.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Classname PictureController
 * @Date 2025/12/8 18:14
 * @Author by Eddie
 */
@RestController
@RequestMapping("/user")
@Api(tags = "图片接口")
public class PictureController {

    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;

    /**
     * 上传图片（可重新上传）
     */
    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUploadDTO pictureUploadDTO,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadDTO, loginUser);
        return ResultUtils.success(pictureVO);
    }

}
