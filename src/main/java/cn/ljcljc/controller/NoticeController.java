package cn.ljcljc.controller;

import cn.ljcljc.mapper.NoticeMapper;
import cn.ljcljc.mapper.UserMapper;
import cn.ljcljc.pojo.Notice;
import cn.ljcljc.pojo.Result;
import cn.ljcljc.pojo.User;
import cn.ljcljc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/userApi")
public class NoticeController {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryNoticeList")
    public Result queryNoticeList() {
        List<Notice> e = noticeMapper.queryAllNotice();
        return Result.success(e);
    }

    @RequestMapping("/addNotice")
    public Result addNotice(@RequestHeader("token") String token, Notice notice) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = formatter.format(date);

        notice.setAuthor(currentUser.getName());
        notice.setTime(nowTime);
        noticeMapper.insertNotice(notice);
        return Result.success(notice);
    }

    @RequestMapping("/updateNotice")
    public Result updateNotice(@RequestHeader("token") String token, Notice notice) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        noticeMapper.updateNotice(notice);
        return Result.success();
    }

    @RequestMapping("/delNotice")
    public Result delNotice(@RequestHeader("token") String token, int id) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        noticeMapper.delNotice(id);
        return Result.success();
    }
}
