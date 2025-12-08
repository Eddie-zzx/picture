package com.graduation.picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.PictureUploadDTO;
import com.graduation.picture.model.entity.Picture;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname PictureService
 * @Date 2025/12/8 18:21
 * @Author by Eddie
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadDTO
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadDTO pictureUploadDTO,
                            User loginUser);

}
