package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
    private int id;
    private String title;
    private String content;
    private String author;
    private String time;
}
