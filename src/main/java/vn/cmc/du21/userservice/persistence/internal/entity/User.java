package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;
import java.util.Collection;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String fullName;
    private Date dob;
    private String gender;
    @Column(unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String cellphone;

    @ManyToMany
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Address> address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Session> sessions;

    public User() {
    }

    public User(long userId, String fullName, Date dob, String gender, String email, String cellphone) {
        this.userId = userId;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.cellphone = cellphone;
    }

    public User(long userId, String fullName, Date dob, String gender, String email, String cellphone, Set<Role> roles, Collection<Address> address, Collection<Session> sessions) {
        this.userId = userId;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.cellphone = cellphone;
        this.roles = roles;
        this.address = address;
        this.sessions = sessions;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Collection<Address> getAddress() {
        return address;
    }

    public void setAddress(Collection<Address> address) {
        this.address = address;
    }

    public Collection<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<Session> sessions) {
        this.sessions = sessions;
    }
}
