package com.intuit.cg.backendtechassessment.Entity;

public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private  String phone;

    public User() { }

    public User(String userName, String firstName, String lastName
        , String email, String phone) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result = 1;
        result = prime * result + (int) (Integer.parseInt(userName) ^ (Integer.parseInt(userName) >> 32));
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        User buyer = (User) object;
        if (userName != buyer.userName)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [ UserName: " + userName + ", First Name: " + firstName
                + ", Last Name: " + lastName + ", email: " +email
                + ", Phone: " +phone + " ]";
    }
}

