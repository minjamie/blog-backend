package com.example.blog.domain;


import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseTime {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "password", length = 30)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "user")
//    private List<Like> likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User that = (User) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}