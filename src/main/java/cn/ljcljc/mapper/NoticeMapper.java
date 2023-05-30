package cn.ljcljc.mapper;

import cn.ljcljc.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from notice")
    public List<Notice> queryAllNotice();

    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into notice(title, content, author, time) VALUES (#{title}, #{content}, #{author}, #{time})")
    public void insertNotice(Notice notice);

    @Update("update notice set title = #{title}, content = #{content} where id = #{id}")
    public void updateNotice(Notice notice);

    @Delete("delete from notice where id = #{id}")
    public void delNotice(int id);
}
