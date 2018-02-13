package com.custu.project.walktogether.data;

import com.custu.project.walktogether.data.master.District;
import com.custu.project.walktogether.data.master.Province;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.data.master.SubDistrict;

/**
 * Created by pannawatnokket on 2/2/2018 AD.
 */

public class Caretaker {

    private Long id;
    private String caretakerNumber;
    private String userName;
    private String password;
    private String titleName;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String dob;
    private String address;
    private Province province;
    private District district;
    private SubDistrict subDistrict;
    private String tell;
    private String occupation;
    private String email;
    private String image;
    private String age;
    private String qrCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaretakerNumber() {
        return caretakerNumber;
    }

    public void setCaretakerNumber(String caretakerNumber) {
        this.caretakerNumber = caretakerNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public SubDistrict getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(SubDistrict subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
