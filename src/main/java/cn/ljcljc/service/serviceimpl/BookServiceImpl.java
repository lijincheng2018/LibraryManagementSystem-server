package cn.ljcljc.service.serviceimpl;

import cn.ljcljc.controller.UserController;
import cn.ljcljc.mapper.BookMapper;
import cn.ljcljc.mapper.UserMapper;
import cn.ljcljc.pojo.Bookrent;
import cn.ljcljc.pojo.User;
import cn.ljcljc.service.BookService;
import cn.ljcljc.utils.JwtUtils;
import cn.ljcljc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookmapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Bookrent insertBookRentService(Bookrent bookrent) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStart = bookrent.getRentTime();
        String dateEnd = bookrent.getReturnTime();


        bookrent.setRentDuration(TimeUtils.calcTimeDiff(dateStart, dateEnd));

        bookrent.setBookName(bookmapper.queryBookName(bookrent.getBookId()));
        int costNum = bookmapper.queryBookCost(bookrent.getBookId());
        int leftNum = bookmapper.queryBookLeftNum(bookrent.getBookId());


        if(bookrent.getRentStatus().equals("1") && leftNum >= 0) {
            bookmapper.updateBookCostNum( (costNum + 1), bookrent.getBookId());
            bookmapper.insertBookRent(bookrent);
        }
        else {
            bookrent.setId(0);
        }

        return bookrent;
    }

    @Override
    public boolean updateBookRentService(Bookrent bookrent) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStart = bookrent.getRentTime();
        String dateEnd = bookrent.getReturnTime();


        bookrent.setRentDuration(TimeUtils.calcTimeDiff(dateStart, dateEnd));

        Bookrent bookrent1 = bookmapper.queryRentById(bookrent.getId());

        User user = userMapper.getUserDataByUserName(bookrent1.getRentUser());

        int rentNum = Integer.parseInt(user.getRentNum());


        if(bookrent1.getRentStatus().equals("1") && bookrent.getRentStatus().equals("2")) {
            int costNum = bookmapper.queryBookCost(bookrent.getBookId());
            bookmapper.updateBookCostNum( (costNum - 1), bookrent.getBookId());

            user.setRentNum(String.valueOf(rentNum - 1));
            userMapper.updateUserInfo(user);
        }

        if(bookrent1.getRentStatus().equals("2") && bookrent.getRentStatus().equals("1")) {
            int costNum = bookmapper.queryBookCost(bookrent.getBookId());
            int leftNum = bookmapper.queryBookLeftNum(bookrent.getBookId());

            if(leftNum > 0) {
                bookmapper.updateBookCostNum((costNum + 1), bookrent.getBookId());
                user.setRentNum(String.valueOf(rentNum + 1));
                userMapper.updateUserInfo(user);
            }
            else {
                return false;
            }
        }

        bookmapper.updateRentBook(bookrent);
        return true;
    }

    @Override
    public void delBookRentService(int id) {
        Bookrent bookrent = bookmapper.queryRentById(id);

        User user = userMapper.getUserDataByUserName(bookrent.getRentUser());
        int rentNum = Integer.parseInt(user.getRentNum());

        if(bookrent.getRentStatus().equals("1")) {
            int costNum = bookmapper.queryBookCost(bookrent.getBookId());
            int leftNum = bookmapper.queryBookLeftNum(bookrent.getBookId());

            if(leftNum > 0) {
                bookmapper.updateBookCostNum((costNum - 1), bookrent.getBookId());
                user.setRentNum(String.valueOf(rentNum - 1));
                userMapper.updateUserInfo(user);
            }
        }

        bookmapper.deleteRent(id);
    }

    @Override
    public int dealUserRent(String token, String rentDuration, int bookId) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = formatter.format(date);
        String newTime = TimeUtils.addTime(rentDuration);


        Bookrent bookrent = new Bookrent();
        bookrent.setBookId(bookId);
        bookrent.setRentTime(nowTime);
        bookrent.setReturnTime(newTime);
        bookrent.setRentUser(currentUser.getUserName());
        bookrent.setRentDuration(TimeUtils.calcTimeDiff(nowTime, newTime));
        bookrent.setRentStatus("1");
        bookrent.setBookName(bookmapper.queryBookName(bookrent.getBookId()));

        int rentNum = Integer.parseInt(currentUser.getRentNum());
        int maxRent = Integer.parseInt(currentUser.getMaxRent());

        if(rentNum >= maxRent) {
            return 2;  // 已经达到借阅上限
        }

        int costNum = bookmapper.queryBookCost(bookrent.getBookId());
        int leftNum = bookmapper.queryBookLeftNum(bookrent.getBookId());

        if(leftNum > 0) {
            bookmapper.updateBookCostNum( (costNum + 1), bookrent.getBookId());
            bookmapper.insertBookRent(bookrent);

            currentUser.setRentNum(String.valueOf(rentNum + 1));
            userMapper.updateUserInfo(currentUser);
        }
        else {
            return 0;
        }
        return 1;
    }

    @Override
    public void returnBookService(String token, int id) {
        String currentUserId = JwtUtils.verifyUser(token);
        User currentUser = userMapper.getUserData(currentUserId);

        Bookrent bookrent = bookmapper.queryRentById(id);
        bookrent.setRentStatus("2");
        int costNum = bookmapper.queryBookCost(bookrent.getBookId());
        bookmapper.updateBookCostNum( (costNum - 1), bookrent.getBookId());
        bookmapper.updateRentBook(bookrent);

        int rentNum = Integer.parseInt(currentUser.getRentNum());
        currentUser.setRentNum(String.valueOf(rentNum - 1));
        userMapper.updateUserInfo(currentUser);
    }
}
