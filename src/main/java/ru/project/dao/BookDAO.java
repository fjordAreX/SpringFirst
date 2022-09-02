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
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(title,yearOfCreation) VALUES (?,?)",
                book.getTitle(),book.getYearOfCreation());
    }

    public Book showOne(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findFirst().orElse(null);
    }

    public void update(int id,Book newBook){
        jdbcTemplate.update("UPDATE book SET title=?,yearofcreation=? WHERE id=?",
                newBook.getTitle(),newBook.getYearOfCreation(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?",id);
    }

    public Optional<Human> getBookOwner(int id){
        return jdbcTemplate.query("SELECT client.* FROM book JOIN client ON book.client_id = client.id WHERE book.id=?",
                new Object[]{id},new BeanPropertyRowMapper<>(Human.class)).stream().findAny();
    }

    public void release (int id){
        jdbcTemplate.update("UPDATE book SET client_id=NULL WHERE id=?",id);
    }

    public void assign(int id, Human human){
        jdbcTemplate.update("UPDATE book SET client_id=? WHERE id=?",human.getId(),id);
    }
}
