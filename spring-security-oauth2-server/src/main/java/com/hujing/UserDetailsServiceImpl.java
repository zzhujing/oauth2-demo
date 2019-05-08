package com.hujing;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hujing.domain.TbUser;
import com.hujing.mapper.TbPermissionMapper;
import com.hujing.mapper.TbUserMapper;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hj
 * @create 2019-04-27 22:15
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbPermissionMapper tbPermissionMapper;

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
            List<GrantedAuthority> permissionList = Lists.newArrayList();
            //2.存在则获取该用户对应的角色，并且添加到认证角色集合中
            List<String> tbPermissions = tbPermissionMapper.queryPermissionListByUserId(user.getId());
            tbPermissions.forEach(per -> {
                GrantedAuthority authority = new SimpleGrantedAuthority(per);
                permissionList.add(authority);
            });
            //3.返回UserDetails
            return new User(user.getUsername(), user.getPassword(), permissionList);
        }
        return null;
    }
}
