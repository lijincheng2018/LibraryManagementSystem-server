package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookrent {
    int id;
    int bookId;
    String rentTime;
    String returnTime;
    String rentUser;
    String rentDuration;
    String rentStatus;
    String bookName;

}
