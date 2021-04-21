package com.kuang.controller;


import com.kuang.pojo.Books;
import com.kuang.service.BookService;
import com.kuang.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class bookController {

    @Autowired
    @Qualifier("ServiceImpl")
    private BookService bookService;

    //查询全部的书籍，并且返回到一个书籍展示页面
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> list = bookService.queryAllBooks();
        model.addAttribute("list",list);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddPage(){
        return "addBook";
    }

    //添加书籍的请求
    @RequestMapping("/AddBook")
    public String addBook(Books books){
        System.out.println("addBook=>"+books);
        bookService.addBook(books);
        return "redirect:/book/allBook";    //重定向到@RequestMapping("/allBook")
    }
    //修改书籍
    @RequestMapping("/toUpdate")
    public String toUpdatePaper(int id,Model model){
        Books books = bookService.queryBookById(id);
        model.addAttribute("QBook",books);
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Books books){
        System.out.println("updateBook=>"+books);
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/deleteBook/{bookID}")
    public String deleteBook(@PathVariable("bookID") int id){
        System.out.println("============"+id);
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName,Model model){
        Books books = bookService.queryBookByName(queryBookName);
        List<Books> list = new ArrayList<>();
        list.add(books);
        if(books==null){
            list = bookService.queryAllBooks();
            model.addAttribute("error","未查到");
        }
        model.addAttribute("list",list);
        return "allBook";
    }
}
