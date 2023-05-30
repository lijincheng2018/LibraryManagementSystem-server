package cn.ljcljc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookISBN;
    private String bookClass;
    private String bookAllNum;
    private String bookCostNum;
    private String isOpen;
}
