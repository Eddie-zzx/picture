package com.graduation.picture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.exception.ThrowUtils;
import com.graduation.picture.manager.FileManager;
import com.graduation.picture.mapper.PictureMapper;
import com.graduation.picture.mapper.UserMapper;
import com.graduation.picture.model.dto.PictureUploadDTO;
import com.graduation.picture.model.entity.Picture;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.PictureVO;
import com.graduation.picture.model.vo.UploadPictureVO;
import com.graduation.picture.service.PictureService;
import com.graduation.picture.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname PictureServiceImpl
 * @Date 2025/12/8 18:22
 * @Author by Eddie
 */
@Service
@Slf4j
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
    @Resource
    private FileManager fileManager;

    @Override
    public PictureVO uploadPicture(MultipartFile multipartFile, PictureUploadDTO pictureUploadDTO, User loginUser) {
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NO_AUTH_ERROR);
        // 用于判断是新增还是更新图片
        Long pictureId = null;
        if (pictureUploadDTO != null) {
            pictureId = pictureUploadDTO.getId();
        }
        // 如果是更新图片，需要校验图片是否存在
        if (pictureId != null) {
            boolean exists = this.lambdaQuery()
                    .eq(Picture::getId, pictureId)
                    .exists();
            ThrowUtils.throwIf(!exists, ErrorCode.NOT_FOUND_ERROR, "图片不存在");
        }
        // 上传图片，得到信息
        // 按照用户 id 划分目录
        String uploadPathPrefix = String.format("public/%s", loginUser.getId());
        UploadPictureVO uploadPictureVO = fileManager.uploadPicture(multipartFile, uploadPathPrefix);
        // 构造要入库的图片信息
        Picture picture = new Picture();
        picture.setUrl(uploadPictureVO.getUrl());
        picture.setName(uploadPictureVO.getPicName());
        picture.setPicSize(uploadPictureVO.getPicSize());
        picture.setPicWidth(uploadPictureVO.getPicWidth());
        picture.setPicHeight(uploadPictureVO.getPicHeight());
        picture.setPicScale(uploadPictureVO.getPicScale());
        picture.setPicFormat(uploadPictureVO.getPicFormat());
        picture.setUserId(loginUser.getId());
        // 如果 pictureId 不为空，表示更新，否则是新增
        if (pictureId != null) {
            // 如果是更新，需要补充 id 和编辑时间
            picture.setId(pictureId);
            picture.setEditTime(new Date());
        }
        boolean result = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "图片上传失败");
        return PictureVO.objToVo(picture);
    }

}
