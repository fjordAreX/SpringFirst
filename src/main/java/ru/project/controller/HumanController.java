package ru.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.dao.HumanDAO;
import ru.project.models.Human;
import ru.project.utils.HumanValidator;


@Controller
@RequestMapping("/people")
public class HumanController {

    private final HumanDAO humanDAO;
    private final HumanValidator humanValidator;

    @Autowired
    public HumanController(HumanDAO humanDAO, HumanValidator humanValidator) {
        this.humanDAO = humanDAO;
        this.humanValidator = humanValidator;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",humanDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model){
        model.addAttribute("human",humanDAO.showOne(id));
        model.addAttribute("books",humanDAO.getBooksByHumanId(id));
    return "people/show";
    }

    @GetMapping("/new")
    public String newPerson (@ModelAttribute("human") Human human){
        return "people/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("human") @Valid Human human, BindingResult bindingResult){
        humanValidator.validate(human,bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        humanDAO.save(human);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("human",humanDAO.showOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Human newHuman,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        humanDAO.update(id,newHuman);
        return "redirect:/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        humanDAO.delete(id);
        return "redirect:/people";
    }

}
