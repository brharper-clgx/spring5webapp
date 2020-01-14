package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.models.Author;
import guru.springframework.spring5webapp.models.Book;
import guru.springframework.spring5webapp.models.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    @Autowired
    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        // Eric
        Author eric = new Author("Eric", "Evans");
        Publisher harperCollins = new Publisher("Harper Collins", "1234 Place Ave.");
        Book ddd = new Book("Domain Driven Design", "1234");

        this.connectBookInfo(new HashSet<>(Collections.singletonList(eric)), harperCollins, ddd);
        this.saveBookInfo(eric, harperCollins, ddd);

        // Rod
        Author rod = new Author("Rod", "Johnson");
        Publisher worx = new Publisher("Worx", "1234 Nowhere Dr.");
        Book noEJB = new Book("J2EE Development without EJB", "23444");

        this.connectBookInfo(new HashSet<>(Collections.singletonList(rod)), worx, noEJB);
        this.saveBookInfo(rod, worx, noEJB);
    }

    private void connectBookInfo(Set<Author> authors, Publisher publisher, Book book) {
        book.setAuthors(authors);
        book.setPublisher(publisher);
    }

    private void saveBookInfo(Author author, Publisher publisher, Book book) {
        this.authorRepository.save(author);
        this.publisherRepository.save(publisher);
        this.bookRepository.save(book);
    }
}
