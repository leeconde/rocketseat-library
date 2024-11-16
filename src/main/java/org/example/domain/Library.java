package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.LoanCodeGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public Loan makeLoan(Book book, Customer customer) {
        if (!book.checkBookAvailability()) {
            System.out.println("Book is not available");
        }
        Loan loan = new Loan();
        loan.setId(LoanCodeGenerator.getNextCode());
        loan.setDateLoan(LocalDate.now());
        loan.setBook(book);
        loan.setCustomer(customer);
        System.out.println("The loan was successfully registered. The code is: " + loan.getId());
        book.setAvailable(false);
        return loan;
    }

    public void makeReturn(Loan loan) {
        loan.setDateReturn(LocalDate.now());
        loan.getBook().setAvailable(true);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void getAllAvailableBooks() {
        List<Book> bookList = books.stream().filter(Book::isAvailable).collect(Collectors.toList());
        if (bookList.isEmpty()) {
            System.out.println("No available books found.");
        } else {
            for (Book b : bookList) {
                System.out.println(b.toString());
            }
        }
    }


    public Author getAuthorByName(String name) {
        return authors.stream().filter(author -> author.getName().equals(name)).findFirst().orElse(null);
    }


    public void getBooksRentedByCustomer(String name) {
        Map<Book, LocalDate> map = loans.stream()
                .filter(loan -> loan.getCustomer().getName().equals(name))
                .collect(Collectors.toMap(Loan::getBook, Loan::getDateLoan));
        if (!map.isEmpty()) {
            System.out.println("Rented books by customer: " + name);
            for (Map.Entry<Book, LocalDate> entry : map.entrySet()) {
                Book book = entry.getKey();
                LocalDate loanDate = entry.getValue();
                System.out.println("Title: " + book.getTitle() + ", Loan Date: " + loanDate);
            }
        } else {
            System.out.println("Customer " + name + " has no rented books.");
        }
    }

    public List<Loan> getLoansByBook(String book) {
        return loans.stream()
                .filter(loan -> loan.getBook().getTitle().equals(book))
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByCustomer(String name) {
        return loans.stream()
                .filter(loan -> loan.getCustomer().getName().equals(name))
                .collect(Collectors.toList());
    }

    public Loan getLoanByCode(int code) {
        return loans.stream().filter(loan -> loan.getId() == code).findFirst().orElse(null);
    }

    public Book getBookByTitle(String title) {
        return books.stream().filter(book -> book.getTitle().equals(title)).findFirst().orElse(null);
    }

    public List<Book> getBookByAuthor(String author) {
        return books.stream().filter(book -> book.getAuthor().getName().equals(author)).collect(Collectors.toList());
    }

    public List<Book> getBookByGenre(Genre genre) {
        return books.stream().filter(book -> book.getGenre().equals(genre)).collect(Collectors.toList());
    }

    public List<Book> getAllNewBooks() {
        LocalDate daysAgo = LocalDate.now().minusDays(30);
        return books.stream().filter(book -> book.getDateRegistration().isAfter(daysAgo))
                .collect(Collectors.toList());
    }

    public Customer getCustomerByName(String name) {
        return customers.stream().filter(customer -> customer.getName().equals(name)).findFirst().orElse(null);
    }

    public Book getBookByCode(int code) {
        return books.stream().filter(b -> b.getId() == code).findFirst().orElse(null);
    }

}
