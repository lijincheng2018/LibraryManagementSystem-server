package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryInfo {
    private String bookNum;
    private String bookClass;
    private String bookRent;
    private String userNum;
}
