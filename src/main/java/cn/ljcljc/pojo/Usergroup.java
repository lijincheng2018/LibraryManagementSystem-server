package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usergroup {
    private int id;
    private String groupName;
    private String maxDay;
    private String maxRent;
}
