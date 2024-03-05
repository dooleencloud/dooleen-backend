package com.dooleen.service.app.oauth.security;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 
 * @Author : name
 * @Update : 2020-06-06 19:37:42
 */
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.entity.SysUserInfoEntity;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MyUserDetails extends SysUserInfoEntity implements UserDetails{

//	private static final long serialVersionUID = 1L;
	
	private List<SysRoleEntity> roles;
    public MyUserDetails(SysUserInfoEntity user, List<SysRoleEntity> roles){
        super(user);
        this.roles = roles; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles == null || roles.size() <1){
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }
        StringBuilder commaBuilder = new StringBuilder();
        for(SysRoleEntity role : roles){
            commaBuilder.append(role.getRoleName()).append(",");
        }
        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
        log.debug(">>authorities="+authorities);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}