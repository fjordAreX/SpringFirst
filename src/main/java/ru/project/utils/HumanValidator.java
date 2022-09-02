package ru.project.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.project.dao.HumanDAO;
import ru.project.models.Human;


@Component
public class HumanValidator implements Validator {

        private final HumanDAO humanDAO;

        @Autowired
                public HumanValidator(HumanDAO humanDAO){
            this.humanDAO = humanDAO;
        }

    @Override
    public boolean supports(Class<?> aClass) {
        return Human.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Human human = (Human) o;

        if (humanDAO.getHumanByName(human.getName()).isPresent()){
            errors.rejectValue("name","","Человек с таким именем уже существует");
        }
    }


}
