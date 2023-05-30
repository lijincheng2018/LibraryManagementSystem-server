package cn.ljcljc.controller;

import cn.ljcljc.mapper.BookClassMapper;
import cn.ljcljc.mapper.BookMapper;
import cn.ljcljc.mapper.UserMapper;
import cn.ljcljc.pojo.*;
import cn.ljcljc.service.BookService;
import cn.ljcljc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/userApi")
@RestController
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookClassMapper bookClassMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryAllBook")
    public Result queryAllBook() {
        List<Book> e = bookMapper.queryAllBookInfo();
        return Result.success(e);
    }

    @RequestMapping("/queryBook")
    public Result queryBook(@RequestParam("id") int bookId) {
        Book e = bookMapper.queryBookInfo(bookId);
        return Result.success(e);
    }

    @RequestMapping("/addBook")
    public Result addBook(@RequestHeader("token") String token, Book book) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookMapper.insertBook(book);
        book.setBookCostNum("0");
        return Result.success(book);
    }

    @RequestMapping("/delBook")
    public Result delBook(@RequestHeader("token") String token, @RequestParam("id") int bookId) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookMapper.deleteBook(bookId);
        return Result.success();
    }

    @RequestMapping("/updateBook")
    public Result updateBook(@RequestHeader("token") String token, Book book) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookMapper.updateBook(book);
        return Result.success();
    }

    @RequestMapping("/queryAllBookClass")
    public Result queryAllBookClass() {
        List<Bookclass> e = bookClassMapper.queryAllBookClassInfo();
        return Result.success(e);
    }

    @RequestMapping("/queryRentList")
    public Result queryRentList(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        List<Bookrent> e = bookMapper.queryRent();
        return Result.success(e);
    }

    @RequestMapping("/queryUserRentList")
    public Result queryUserRentList(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));

        String username = currentUser.getUserName();
        System.out.println(username);
        List<Bookrent> e = bookMapper.queryRentByUser(username);
        return Result.success(e);
    }

    @RequestMapping("/addRentList")
    public Result addRentList(@RequestHeader("token") String token, Bookrent bookrent) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        Bookrent e = bookService.insertBookRentService(bookrent);
        if(e.getId() == 0) {
            return Result.error(100, "剩余书本不足", null);
        }
        return Result.success(e);
    }

    @RequestMapping("/updateRentList")
    public Result updateRentList(@RequestHeader("token") String token, Bookrent bookrent) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        Boolean isOK = bookService.updateBookRentService(bookrent);
        if(isOK == false) return Result.error(100,"剩余书本不足", null);
        else return Result.success();
    }

    @RequestMapping("/delRent")
    public Result updateRentList(@RequestHeader("token") String token, int id) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookService.delBookRentService(id);
        return Result.success();
    }

    @RequestMapping("/rentBook")
    public Result rentBook(@RequestHeader("token") String token, String rentDuration, int bookId) {
        int status = bookService.dealUserRent(token, rentDuration, bookId);
        if(status == 0) return Result.error(100,"剩余书本不足", null);
        else if(status == 2) return Result.error(100,"你已经到达借阅上限", null);
        else return Result.success();
    }

    @RequestMapping("/returnBook")
    public Result returnBook(@RequestHeader("token") String token, int id) {
        bookService.returnBookService(token, id);
        return Result.success();
    }

    @RequestMapping("/addBookClass")
    public Result addBookClass(@RequestHeader("token") String token, Bookclass bookclass) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookClassMapper.insertBookClass(bookclass);
        return Result.success(bookclass);
    }

    @RequestMapping("/updateBookClass")
    public Result updateBookClass(@RequestHeader("token") String token, Bookclass bookclass) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookClassMapper.updateBookClass(bookclass);
        return Result.success();
    }

    @RequestMapping("/delBookClass")
    public Result delBookClass(@RequestHeader("token") String token, int id) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));
        if(!currentUser.getUserGroup().equals("1")) {
            return Result.error(100, "权限不足", null);
        }

        bookClassMapper.deleteBookClass(id);
        return Result.success();
    }

    @RequestMapping("/getUserRentChartData")
    public Result getUserChartData(@RequestHeader("token") String token) {
        User currentUser = userMapper.getUserData(JwtUtils.verifyUser(token));

        UserRentChartData e = bookMapper.queryUserRentChartData(currentUser.getUserName());
        return Result.success(e);
    }

    @RequestMapping("/queryLibraryInfo")
    public Result queryLibraryInfo() {
        LibraryInfo e = bookMapper.queryLibraryInfo();
        return Result.success(e);
    }
}
