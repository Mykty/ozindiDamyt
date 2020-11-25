package kz.dostyk.ozindidamyt.ui.library.models;

import java.io.Serializable;
import java.util.Comparator;

public class Book implements Serializable {
    private String book_key;
    private String name;
    private String author;
    private String language;
    private String desc;
    private int page_number;
    private String rating;
    private String photo;
    private String url;
    private String imgStorageName;

    public Book() {}

    public Book(String book_key, String author, String name) {
        this.book_key = book_key;
        this.author = author;
        this.name = name;
    }

    public Book(String photo, String author, String name, String url) {
        this.photo = photo;
        this.author = author;
        this.name = name;
        this.url = url;
    }


    public Book(String book_key, String name, String author, String desc, String language, int page_number, String rating, String photo, String url, String imgStorageName) {
        this.book_key = book_key;
        this.name = name;
        this.author = author;
        this.desc = desc;
        this.language = language;
        this.page_number = page_number;
        this.rating = rating;
        this.photo = photo;
        this.url = url;
        this.imgStorageName = imgStorageName;
    }

    public static Comparator<Book> bookNameComprator = new Comparator<Book>() {

        public int compare(Book s1, Book s2) {
            String bookName1 = s1.getName().toUpperCase();
            String bookName2 = s2.getName().toUpperCase();

            //ascending order
            return bookName1.compareTo(bookName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    public static Comparator<Book> bookAuthorComprator = new Comparator<Book>() {

        public int compare(Book s1, Book s2) {
            String bookAuthor1 = s1.getAuthor().toUpperCase();
            String bookAuthor2 = s2.getAuthor().toUpperCase();

            //ascending order
            return bookAuthor1.compareTo(bookAuthor2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    public static Comparator<Book> ratingComparator = new Comparator<Book>() {

        public int compare(Book s1, Book s2) {

            String rating1 = s1.getRating();
            String rating2 = s2.getRating();

	   /*For ascending order*/
            return rating2.compareTo(rating1);
//            return rollno1-rollno2;

	   /*For descending order*/
            //rollno2-rollno1;
        }
    };

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImgStorageName() {
        return imgStorageName;
    }

    public void setImgStorageName(String imgStorageName) {
        this.imgStorageName = imgStorageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBook_key() {
        return book_key;
    }

    public void setBook_key(String book_key) {
        this.book_key = book_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
