package com.graduation.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.PictureEditDTO;
import com.graduation.picture.model.dto.PictureReviewDTO;
import com.graduation.picture.model.dto.PictureUploadByBatchDTO;
import com.graduation.picture.model.dto.PictureUploadDTO;
import com.graduation.picture.model.entity.Picture;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.qo.PictureQueryQo;
import com.graduation.picture.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname PictureService
 * @Date 2025/12/8 18:21
 * @Author by Eddie
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource
     * @param pictureUploadDTO
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadDTO pictureUploadDTO,
                            User loginUser);

    /**
     * 获取查询对象
     *
     * @param pictureQueryQo
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryQo pictureQueryQo);

    /**
     * 获取图片包装类（单条）
     *
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片
     *
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     *
     * @param pictureReviewDTO
     * @param loginUser
     */
    void doPictureReview(PictureReviewDTO pictureReviewDTO, User loginUser);

    /**
     * 填充审核参数
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchDTO
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(
            PictureUploadByBatchDTO pictureUploadByBatchDTO,
            User loginUser
    );

    /**
     * 清理图片
     * @param oldPicture
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 检查图片权限
     * @param loginUser
     * @param picture
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 删除图片
     * @param pictureId
     * @param loginUser
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 编辑图片
     * @param pictureEditDTO
     * @param loginUser
     */
    void editPicture(PictureEditDTO pictureEditDTO, User loginUser);

}
