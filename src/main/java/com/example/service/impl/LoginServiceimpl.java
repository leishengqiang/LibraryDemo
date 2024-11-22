package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Result.LoginResult;
import com.example.entity.Admin;
import com.example.entity.Sysadmin;
import com.example.entity.User;
import com.example.form.LoginForm;
import com.example.mapper.AdminMapper;
import com.example.mapper.SysadminMapper;
import com.example.mapper.UserMapper;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceimpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysadminMapper sysadminMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public LoginResult login(LoginForm loginform) {
        LoginResult loginResult=new LoginResult();
        switch (loginform.getType()){
            case 0:
                loginResult.setCode(-3);
                loginResult.setMsg("请选择身份！");
                break;
            case 1:
                QueryWrapper<User> queryWrapper1=new QueryWrapper<>();
                queryWrapper1.eq("username",loginform.getUsername());
                User user = this.userMapper.selectOne(queryWrapper1);
                if (user==null){
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!user.getPassword().equals(loginform.getPassword())){
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(user);
                break;
            case 2:
                QueryWrapper<Admin> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("username",loginform.getUsername());
                Admin admin = this.adminMapper.selectOne(queryWrapper2);
                if (admin==null){
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!admin.getPassword().equals(loginform.getPassword())){
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(admin);
                break;
            case 3:
                QueryWrapper<Sysadmin> queryWrapper3=new QueryWrapper<>();
                queryWrapper3.eq("username",loginform.getUsername());
                Sysadmin sysadmin = this.sysadminMapper.selectOne(queryWrapper3);
                if (sysadmin==null){
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!sysadmin.getPassword().equals(loginform.getPassword())){
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(sysadmin);
                break;
        }
        return loginResult;
    }
}
