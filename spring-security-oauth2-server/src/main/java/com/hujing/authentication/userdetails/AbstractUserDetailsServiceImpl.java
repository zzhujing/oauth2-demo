package com.hujing.authentication.userdetails;

import com.hujing.domain.TbUser;
import com.hujing.mapper.TbPermissionMapper;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hj
 * 2019-05-09 15:31
 */
@Component
public abstract class AbstractUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbPermissionMapper tbPermissionMapper;

    public User generatorUser(TbUser user) {
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
}
