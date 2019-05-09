package com.hujing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hujing.domain.TbPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author hujing
 * @since 2019-04-27
 */
@Repository
public interface TbPermissionMapper extends BaseMapper<TbPermission> {
    List<String> queryPermissionListByUserId(Long userId);
}
