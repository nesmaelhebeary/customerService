package com.hypercell.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.customerservice.domain.enumeration.CustomerStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_dial")
    private String contactDial;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CustomerStatus status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "activity")
    private String activity;

    @Column(name = "address")
    private String address;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "commercial_registery_id")
    private String commercialRegisteryId;

    @OneToMany(mappedBy = "customerId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customerId" }, allowSetters = true)
    private Set<Claim> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Customer companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public Customer contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDial() {
        return this.contactDial;
    }

    public Customer contactDial(String contactDial) {
        this.contactDial = contactDial;
        return this;
    }

    public void setContactDial(String contactDial) {
        this.contactDial = contactDial;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public Customer contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Customer createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public CustomerStatus getStatus() {
        return this.status;
    }

    public Customer status(CustomerStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Customer createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Customer groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getActivity() {
        return this.activity;
    }

    public Customer activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddress() {
        return this.address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxId() {
        return this.taxId;
    }

    public Customer taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getCommercialRegisteryId() {
        return this.commercialRegisteryId;
    }

    public Customer commercialRegisteryId(String commercialRegisteryId) {
        this.commercialRegisteryId = commercialRegisteryId;
        return this;
    }

    public void setCommercialRegisteryId(String commercialRegisteryId) {
        this.commercialRegisteryId = commercialRegisteryId;
    }

    public Set<Claim> getIds() {
        return this.ids;
    }

    public Customer ids(Set<Claim> claims) {
        this.setIds(claims);
        return this;
    }

    public Customer addId(Claim claim) {
        this.ids.add(claim);
        claim.setCustomerId(this);
        return this;
    }

    public Customer removeId(Claim claim) {
        this.ids.remove(claim);
        claim.setCustomerId(null);
        return this;
    }

    public void setIds(Set<Claim> claims) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setCustomerId(null));
        }
        if (claims != null) {
            claims.forEach(i -> i.setCustomerId(this));
        }
        this.ids = claims;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactDial='" + getContactDial() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", address='" + getAddress() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", commercialRegisteryId='" + getCommercialRegisteryId() + "'" +
            "}";
    }
}
