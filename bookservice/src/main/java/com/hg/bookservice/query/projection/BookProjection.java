package com.hg.bookservice.query.projection;

import com.hg.bookservice.command.data.Book;
import com.hg.bookservice.command.data.BookRepository;
import com.hg.bookservice.query.model.BookResponseModel;
import com.hg.bookservice.query.queries.GetAllBookQuery;
import com.hg.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query){
        List<Book> list = bookRepository.findAll();
        List<BookResponseModel> listBookResponse = new ArrayList<>();
        list.forEach(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book,model);
            listBookResponse.add(model);
        });
        return listBookResponse;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) throws Exception {
        Book book = bookRepository.findById(query.getId())
                .orElseThrow(() -> new Exception("Book not found with id: " + query.getId()));
        BookResponseModel bookResponseModel = new BookResponseModel();
        BeanUtils.copyProperties(book, bookResponseModel);
        return bookResponseModel;
    }
}
