package ru.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.dao.BookDAO;
import ru.project.dao.HumanDAO;
import ru.project.models.Book;
import ru.project.models.Human;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

 private final BookDAO bookDAO;
 private final HumanDAO humanDAO;

    @Autowired
    public BookController(BookDAO bookDAO, HumanDAO humanDAO) {
        this.bookDAO = bookDAO;
        this.humanDAO = humanDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model, @ModelAttribute("human")Human human){
        model.addAttribute("book",bookDAO.showOne(id));

        Optional<Human> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent()){
            model.addAttribute("owner",bookOwner.get());
        } else{
            model.addAttribute("people",humanDAO.index());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book",bookDAO.showOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book newBook,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.update(id,newBook);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("human")Human human){
        bookDAO.assign(id,human);
        return "redirect:/books/"+id;
    }


}
