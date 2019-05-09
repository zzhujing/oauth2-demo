package com.hujing.authentication.userdetails;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hujing.domain.TbUser;
import com.hujing.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-04-27 22:15
 */
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl extends AbstractUserDetailsServiceImpl {

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * spring security oauth2 实现的根据用户名来认证权限以及角色
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名获取用户
        TbUser user = tbUserMapper.selectOne(Wrappers.<TbUser>query().eq("username", username));
        if (user != null) {
            return super.generatorUser(user);
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
