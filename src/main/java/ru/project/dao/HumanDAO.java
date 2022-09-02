package ru.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.project.models.Book;
import ru.project.models.Human;

import java.util.List;
import java.util.Optional;

@Component
public class HumanDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HumanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Human> index(){
        return jdbcTemplate.query("SELECT * FROM client",new BeanPropertyRowMapper<>(Human.class));
    }

    public void save(Human human){
        jdbcTemplate.update("INSERT INTO client(name,yearOfBirth) VALUES (?,?)",
                human.getName(),human.getYearOfBirth());
    }

    public Human showOne(int id){
        return jdbcTemplate.query("SELECT * FROM client WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Human.class)).stream().findFirst().orElse(null);
    }

    public void update(int id,Human newHuman){
        jdbcTemplate.update("UPDATE client SET name=?,yearofbirth=? WHERE id=?",
                newHuman.getName(),newHuman.getYearOfBirth(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM client WHERE id=?",id);
    }

    public Optional<Human> getHumanByName(String name){
        return jdbcTemplate.query("SELECT * FROM client WHERE name=?",new Object[]{name},
                new BeanPropertyRowMapper<>(Human.class)).stream().findAny();
    }

    public List<Book> getBooksByHumanId(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE client_id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
