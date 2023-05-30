package cn.ljcljc.controller;

import cn.ljcljc.mapper.UserMapper;
import cn.ljcljc.pojo.Result;
import cn.ljcljc.pojo.User;
import cn.ljcljc.pojo.UserPower;
import cn.ljcljc.pojo.Usergroup;
import cn.ljcljc.utils.JwtUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.ljcljc.utils.StringToMapUtils.mapStringToMap;

@RestController
@RequestMapping("/userApi")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    public Result login(String username, String password) {

        User e = userMapper.login(username, password);

        if(e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", e.getUserId());
            claims.put("username", e.getUserName());
            claims.put("name", e.getName());
            claims.put("usergroup", e.getUserGroup());

            String jwt = JwtUtils.generateJwt(claims);

            return Result.success(jwt);
        }
        else {
            return Result.error(400, "账号或密码错误", null);
        }
    }

    @RequestMapping("/register")
    public Result register(String username, String name, String password) {
        User e = userMapper.getUserDataByUserName(username);
        System.out.println(e);
        if(e == null) {
            userMapper.register(username, password, name);
        }
        else {
            return Result.error(100, "该用户名已存在，请重新输入用户名", null);
        }
        return Result.success();
    }

    @RequestMapping("/getHomeUserData")
    public Result getHomeUserData(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));

        return Result.success(currentUser);
    }

    @RequestMapping("/getAllUserData")
    public Result getAllUserData(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        List<User> e = userMapper.getAllUserData();
        return Result.success(e);
    }

    @RequestMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestHeader("token") String token, User user) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.updateUserInfo(user);
        return Result.success();
    }

    @RequestMapping("/delUser")
    public Result delUser(@RequestHeader("token") String token, @RequestParam("id") int userId) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.delUser(userId);
        return Result.success();
    }

    @RequestMapping("/addUser")
    public Result addUser(@RequestHeader("token") String token, User user) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.insertUser(user);
        return Result.success(user);
    }

    @RequestMapping("/resetUserPass")
    public Result resetUserPass(@RequestHeader("token") String token, @RequestParam("id") int userId) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.resetUserPasswd(userId);
        return Result.success();
    }

    @RequestMapping("/queryUserGroup")
    public Result queryUserGroup(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        List<Usergroup> e = userMapper.queryAllUserGroup();
        return Result.success(e);
    }

    @RequestMapping("/addUserGroup")
    public Result addUserGroup(@RequestHeader("token") String token, Usergroup usergroup) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.insertUserGroup(usergroup);
        return Result.success(usergroup);
    }

    @RequestMapping("/updateUserGroup")
    public Result updateUserGroup(@RequestHeader("token") String token, Usergroup usergroup) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.updateUserGroup(usergroup);
        return Result.success();
    }

    @RequestMapping("/delUserGroup")
    public Result delUserGroup(@RequestHeader("token") String token, int id) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        userMapper.delUserGroup(id);
        return Result.success();
    }

    @RequestMapping("/getUserPower")
    public Result getUserPower(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        String username = currentUser.getUserName();
        UserPower userPower = userMapper.queryUserPower(username);
        return Result.success(userPower);
    }

    @RequestMapping("/changeUserPasswd")
    public Result changeUserPasswd(int uid, String passwd) {
        userMapper.changeUserPasswd(passwd, uid);
        return Result.success();
    }

}
