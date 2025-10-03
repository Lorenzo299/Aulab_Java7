package it.aulab.spring_data_project.dtos;

public class AuthorDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String fullname;
    private Integer nPosts;

    public AuthorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getnPosts() {
        return nPosts;
    }

    public void setnPosts(Integer nPosts) {
        this.nPosts = nPosts;
    }

}
