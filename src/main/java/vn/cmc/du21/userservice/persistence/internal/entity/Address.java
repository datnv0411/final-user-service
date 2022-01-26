package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    private boolean isDefault;
    private String typeAddress;
    private String fullName;
    private String cellphone;
    private String province;
    private String district;
    private String town;
    private String specificAddress;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Address() {
    }

    public Address(long addressId, boolean isDefault, String typeAddress, String fullName, String cellphone, String province, String district, String town, String specificAddress, User user) {
        this.addressId = addressId;
        this.isDefault = isDefault;
        this.typeAddress = typeAddress;
        this.fullName = fullName;
        this.cellphone = cellphone;
        this.province = province;
        this.district = district;
        this.town = town;
        this.specificAddress = specificAddress;
        this.user = user;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(String typeAddress) {
        this.typeAddress = typeAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
