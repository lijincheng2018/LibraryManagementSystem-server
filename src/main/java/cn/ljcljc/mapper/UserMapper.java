package cn.ljcljc.mapper;

import cn.ljcljc.pojo.User;
import cn.ljcljc.pojo.UserPower;
import cn.ljcljc.pojo.Usergroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select user.uid as userId, user.username as userName, user.name, user.usergroup as userGroup, usergroup.groupName as userGroupName, usergroup.maxDay, usergroup.maxRent, user.rentNum from user, usergroup where user.username = #{userName} and user.password = #{passWord} and user.usergroup = usergroup.id")
    public User login(String userName, String passWord);

    @Insert("insert into user(username, password, name, usergroup, rentNum) VALUES (#{username}, #{password}, #{name}, '2', '0')")
    public void register(String username, String password, String name);

    @Select("select user.uid as userId, user.username as userName, user.name, user.usergroup as userGroup, usergroup.groupName as userGroupName, usergroup.maxDay, usergroup.maxRent, user.rentNum from user, usergroup where user.usergroup = usergroup.id and user.uid = #{userId}")
    public User getUserData(String userId);

    @Select("select user.uid as userId, user.username as userName, user.name, user.usergroup as userGroup, usergroup.groupName as userGroupName, usergroup.maxDay, usergroup.maxRent, user.rentNum from user, usergroup where user.usergroup = usergroup.id and user.username = #{username}")
    public User getUserDataByUserName(String username);

    @Select("select user.uid as userId, user.username as userName, user.name, user.usergroup as userGroup, usergroup.groupName as userGroupName, usergroup.maxDay, usergroup.maxRent, user.rentNum from user, usergroup where user.usergroup = usergroup.id")
    public List<User> getAllUserData();

    @Update("update user set name = #{name}, usergroup = #{userGroup}, rentNum = #{rentNum} where uid = #{userId}")
    public void updateUserInfo(User user);

    @Update("update user set password = '123456' where uid = #{userId}")
    public void resetUserPasswd(int userId);

    @Delete("delete from user where uid = #{userId}")
    public void delUser(int userId);

    @Options(keyProperty = "userId", useGeneratedKeys = true)
    @Insert("insert into user(username, password, name, usergroup) VALUES (#{userName}, '123456',#{name}, #{userGroup})")
    public void insertUser(User user);

    @Select("select * from usergroup")
    public List<Usergroup> queryAllUserGroup();

    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into usergroup(groupName, maxDay, maxRent) VALUES (#{groupName}, #{maxDay}, #{maxRent})")
    public void insertUserGroup(Usergroup usergroup);

    @Update("update usergroup set groupName = #{groupName}, maxDay = #{maxDay}, maxRent = #{maxRent} where id = #{id}")
    public void updateUserGroup(Usergroup usergroup);

    @Delete("delete from usergroup where id = #{id}")
    public void delUserGroup(int id);

    @Select("select usergroup.maxRent, usergroup.maxDay, user.rentNum from usergroup, user where user.usergroup = usergroup.id and user.username = #{username}")
    public UserPower queryUserPower(String username);

    @Update("update user set password = #{password} where uid = #{uid}")
    public void changeUserPasswd(String password, int uid);
}
