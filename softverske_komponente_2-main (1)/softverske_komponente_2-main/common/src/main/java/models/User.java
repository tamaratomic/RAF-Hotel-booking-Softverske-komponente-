package models;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import enums.UserRanks;
import enums.UserTypes;

import javax.persistence.*;
import java.time.Instant;

@SuppressWarnings("all")
@Entity(name = "User")
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullname;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "birth_date", nullable = false)
    private String dateOfBirth;
    @Column(name = "ban")
    private boolean isBanned = false;
    @Column(name = "user_type")
    private String userType = UserTypes.STANDARD;
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;
    @Column(name = "points")
    private Integer numberOfreservations = 0;
    @Column(name = "hotel_name")
    private String hotelname;
    @Column(name = "employment_date")
    private String dateOfEmployment;
    @Column(name = "rank")
    private Rank rank = UserRanks.defaultRank;
    @CreatedDate
    public Instant createdAt = Instant.now();
    @LastModifiedDate
    public Instant lastModified;



    public void setNumberOfreservations(Integer numberOfReservations) {
        this.numberOfreservations = numberOfReservations;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(String dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Integer getNumberOfreservations() {
        return numberOfreservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfreservations = numberOfReservations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", isBanned=" + isBanned +
                ", userType='" + userType + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", points=" + numberOfreservations +
                ", hotelname='" + hotelname + '\'' +
                ", dateOfEmployment='" + dateOfEmployment + '\'' +
                ", rank=" + rank +
                ", createdAt=" + createdAt +
                ", lastModified=" + lastModified +
                '}';
    }
}
