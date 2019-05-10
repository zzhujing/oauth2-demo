package com.hujing.authentication.userdetails;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hujing.authentication.code.common.enums.LoginType;
import com.hujing.authentication.code.exception.CustomerSecurityExcpetion;
import com.hujing.domain.TbUser;
import com.hujing.mapper.TbPermissionMapper;
import com.hujing.mapper.TbUserMapper;
import com.hujing.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hj
 * @create 2019-04-27 22:15
 */
@Component("userDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbPermissionMapper tbPermissionMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SecurityProperties securityProperties;
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
        TbUser user = findUserFromRequest(username);
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
        throw new UsernameNotFoundException("用户名不存在");
    }


    /**
     * 根据传递的username参数获取对应的用户对象
     * @param paramValue
     * @return
     */
    public TbUser findUserFromRequest(String paramValue) {
        String type = request.getParameter(securityProperties.getAuth().getLoginTypeParamName());
        String refreshToken = request.getParameter("refresh_token");
        TbUser result = null;
        log.info("【获取refreshToken】 token : {} ", refreshToken);

        if (StringUtils.isNotEmpty(type)) {
            LoginType loginType = LoginType.findByType(Integer.parseInt(type));
            if (loginType == null) throw new CustomerSecurityExcpetion("登录表单类型异常");
            if (loginType.equals(LoginType.SMS)) {
                result = tbUserMapper.selectOne(Wrappers.<TbUser>lambdaQuery().eq(TbUser::getPhone, paramValue));
            } else if (loginType.equals(LoginType.IMAGE)) {
                result = tbUserMapper.selectOne(Wrappers.<TbUser>lambdaQuery().eq(TbUser::getUsername, paramValue));
            }
        }
        if (StringUtils.isNotBlank(refreshToken)) {
            result = tbUserMapper.selectOne(Wrappers.<TbUser>lambdaQuery().eq(TbUser::getUsername, paramValue));
        }
        return result;
    }
}
