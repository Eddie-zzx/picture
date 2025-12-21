package com.graduation.picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.SpaceCategoryAnalyzeDTO;
import com.graduation.picture.model.dto.SpaceRankAnalyzeDTO;
import com.graduation.picture.model.dto.SpaceSizeAnalyzeDTO;
import com.graduation.picture.model.dto.SpaceTagAnalyzeDTO;
import com.graduation.picture.model.dto.SpaceUsageAnalyzeDTO;
import com.graduation.picture.model.dto.SpaceUserAnalyzeDTO;
import com.graduation.picture.model.entity.Space;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.SpaceCategoryAnalyzeVO;
import com.graduation.picture.model.vo.SpaceSizeAnalyzeVO;
import com.graduation.picture.model.vo.SpaceTagAnalyzeVO;
import com.graduation.picture.model.vo.SpaceUsageAnalyzeVO;
import com.graduation.picture.model.vo.SpaceUserAnalyzeVO;

import java.util.List;

/**
 * @author 李鱼皮
 * @createDate 2024-12-18 19:53:34
 */
public interface SpaceAnalyzeService extends IService<Space> {

    /**
     * 获取空间使用情况分析
     *
     * @param spaceUsageAnalyzeDTO
     * @param loginUser
     * @return
     */
    SpaceUsageAnalyzeVO getSpaceUsageAnalyze(SpaceUsageAnalyzeDTO spaceUsageAnalyzeDTO, User loginUser);

    /**
     * 获取空间图片分类分析
     *
     * @param spaceCategoryAnalyzeDTO
     * @param loginUser
     * @return
     */
    List<SpaceCategoryAnalyzeVO> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeDTO spaceCategoryAnalyzeDTO, User loginUser);

    /**
     * 获取空间图片标签分析
     *
     * @param spaceTagAnalyzeDTO
     * @param loginUser
     * @return
     */
    List<SpaceTagAnalyzeVO> getSpaceTagAnalyze(SpaceTagAnalyzeDTO spaceTagAnalyzeDTO, User loginUser);

    /**
     * 获取空间图片大小分析
     *
     * @param spaceSizeAnalyzeDTO
     * @param loginUser
     * @return
     */
    List<SpaceSizeAnalyzeVO> getSpaceSizeAnalyze(SpaceSizeAnalyzeDTO spaceSizeAnalyzeDTO, User loginUser);

    /**
     * 获取空间用户上传行为分析
     *
     * @param spaceUserAnalyzeDTO
     * @param loginUser
     * @return
     */
    List<SpaceUserAnalyzeVO> getSpaceUserAnalyze(SpaceUserAnalyzeDTO spaceUserAnalyzeDTO, User loginUser);

    /**
     * 空间使用排行分析（仅管理员）
     *
     * @param spaceRankAnalyzeDTO
     * @param loginUser
     * @return
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeDTO spaceRankAnalyzeDTO, User loginUser);
}
