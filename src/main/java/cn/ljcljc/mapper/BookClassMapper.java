package cn.ljcljc.mapper;

import cn.ljcljc.pojo.Book;
import cn.ljcljc.pojo.Bookclass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookClassMapper {
    @Select("select id, class as bookClass from book_class")
    public List<Bookclass> queryAllBookClassInfo();

    @Select("select * from book_class where id = #{classId}")
    public Bookclass queryBookClassInfo(int classId);

    @Delete("delete from book_class where id = #{classID}")
    public void deleteBookClass(int classId);

    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into book_class(class) values (#{bookClass})")
    public void insertBookClass(Bookclass book);

    @Update("update book_class set class = #{bookClass} where id=#{id}")
    public void updateBookClass(Bookclass book);
}
