package cn.ljcljc.service;

import cn.ljcljc.pojo.Bookrent;

public interface BookService {
    public Bookrent insertBookRentService(Bookrent bookrent);
    public boolean updateBookRentService(Bookrent bookrent);
    public void delBookRentService(int id);
    public int dealUserRent(String token, String rentDuration, int bookId);
    public void returnBookService(String token, int id);

}
