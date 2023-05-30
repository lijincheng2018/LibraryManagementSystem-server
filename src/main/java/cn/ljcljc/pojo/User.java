package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String name;
    private String userGroup;
    private String userGroupName;
    private String maxRent;
    private String maxDay;
    private String rentNum;
}
