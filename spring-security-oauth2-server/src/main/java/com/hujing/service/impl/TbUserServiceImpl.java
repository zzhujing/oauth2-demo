package com.hujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hujing.domain.TbUser;
import com.hujing.mapper.TbUserMapper;
import com.hujing.service.TbUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2019-04-27
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

}
