package dto.user;

@SuppressWarnings("all")
public class ManagerInformationDto {

    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String passportNumber;
    private String hotelname;
    private String dateOfEmployment;

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public void setDateOfEmployment(String dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getHotelname() {
        return hotelname;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    @Override
    public String toString() {
        return fullname + ' ' + username + ' ' + password + ' ' +
                 email + ' ' +
                phoneNumber + ' ' +
                dateOfBirth + ' ' +
                passportNumber + ' ' +
                hotelname + ' ' +
                dateOfEmployment + ' ' ;
    }
}
