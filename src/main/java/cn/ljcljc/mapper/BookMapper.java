package cn.ljcljc.mapper;

import cn.ljcljc.pojo.Book;
import cn.ljcljc.pojo.Bookrent;
import cn.ljcljc.pojo.LibraryInfo;
import cn.ljcljc.pojo.UserRentChartData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select id as bookId, bookname as bookName, ISBN as bookISBN, author as bookAuthor, num as bookAllNum, costNum as bookCostNum, classify as bookClass, isOpen from book_info")
    public List<Book> queryAllBookInfo();

    @Select("select * from book_info where id = #{bookId}")
    public Book queryBookInfo(int bookId);

    @Delete("delete from book_info where id = #{bookID}")
    public void deleteBook(int bookId);

    @Options(keyProperty = "bookId", useGeneratedKeys = true)
    @Insert("insert into book_info(bookname, ISBN, author, num, costNum, classify, isOpen)" +
            " values (#{bookName}, #{bookISBN}, #{bookAuthor}, #{bookAllNum}, '0', #{bookClass}, #{isOpen})")
    public void insertBook(Book book);

    @Update("update book_info set bookname = #{bookName}, ISBN = #{bookISBN}, author = #{bookAuthor}, num = #{bookAllNum}, costNum = #{bookCostNum}, classify = #{bookClass}, isOpen = #{isOpen} where id=#{bookId}")
    public void updateBook(Book book);

    @Select("select book_rent.id, book_rent.bookid, book_rent.renttime, book_rent.returntime, book_rent.rentduration, book_rent.rentuser, book_rent.rentStatus, book_info.bookname as bookName from book_rent, book_info where book_info.id = book_rent.bookId ORDER BY book_rent.id DESC")
    public List<Bookrent> queryRent();

    @Select("select book_rent.id, book_rent.bookid, book_rent.renttime, book_rent.returntime, book_rent.rentduration, book_rent.rentuser, book_rent.rentStatus, book_info.bookname as bookName from book_rent, book_info where book_info.id = book_rent.bookId and book_rent.rentUser = #{username} ORDER BY book_rent.id DESC")
    public List<Bookrent> queryRentByUser(String username);

    @Select("select book_rent.id, book_rent.bookid, book_rent.renttime, book_rent.returntime, book_rent.rentduration, book_rent.rentuser, book_rent.rentStatus, book_info.bookname as bookName from book_rent, book_info where book_info.id = book_rent.bookId and book_rent.id = #{id}")
    public Bookrent queryRentById(int id);

    @Select("select bookname from book_info where id = #{bookId}")
    public String queryBookName(int bookId);

    @Select("select costNum from book_info where id = #{bookId}")
    public int queryBookCost(int bookId);

    @Select("select num - costNum as diff from book_info where id = #{bookId}")
    public int queryBookLeftNum(int bookId);

    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into book_rent(bookId, rentTime, returnTime, rentDuration, rentUser, rentStatus)" +
            " values (#{bookId}, #{rentTime}, #{returnTime}, #{rentDuration}, #{rentUser}, #{rentStatus})")
    public void insertBookRent(Bookrent bookrent);

    @Update("update book_info set costNum = #{costNum} where id = #{bookId}")
    public void updateBookCostNum(int costNum, int bookId);

    @Update("update book_rent set rentTime = #{rentTime}, returnTime = #{returnTime}, rentDuration = #{rentDuration}, rentStatus = #{rentStatus} where id = #{id}")
    public void updateRentBook(Bookrent bookrent);

    @Delete("delete from book_rent where id = #{id}")
    public void deleteRent(int id);

    @Select("select COUNT(*) as total, SUM(CASE WHEN rentStatus = '2' THEN 1 ELSE 0 END) as returned from book_rent where rentUser=#{username}")
    public UserRentChartData queryUserRentChartData(String username);

    @Select("SELECT (SELECT COUNT(*) FROM book_info) AS bookNum, (SELECT COUNT(*) FROM book_class) AS bookClass, (SELECT COUNT(*) FROM book_rent) AS bookRent, (SELECT COUNT(*) FROM user) AS userNum;\n")
    public LibraryInfo queryLibraryInfo();
}
