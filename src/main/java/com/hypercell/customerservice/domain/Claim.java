package com.hypercell.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.customerservice.domain.enumeration.ClaimType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Claim.
 */
@Entity
@Table(name = "claim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "accident_date")
    private LocalDate accidentDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_type")
    private ClaimType claimType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "line_of_business")
    private Long lineOfBusiness;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "paid")
    private Double paid;

    @Column(name = "outstanding")
    private Double outstanding;

    @Column(name = "claim_amount")
    private Double claimAmount;

    @Column(name = "commulative")
    private Boolean commulative;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ids" }, allowSetters = true)
    private Customer customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Claim id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Claim description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Claim customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getAccidentDate() {
        return this.accidentDate;
    }

    public Claim accidentDate(LocalDate accidentDate) {
        this.accidentDate = accidentDate;
        return this;
    }

    public void setAccidentDate(LocalDate accidentDate) {
        this.accidentDate = accidentDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Claim createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public ClaimType getClaimType() {
        return this.claimType;
    }

    public Claim claimType(ClaimType claimType) {
        this.claimType = claimType;
        return this;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Claim createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLineOfBusiness() {
        return this.lineOfBusiness;
    }

    public Claim lineOfBusiness(Long lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
        return this;
    }

    public void setLineOfBusiness(Long lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Claim productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPaid() {
        return this.paid;
    }

    public Claim paid(Double paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getOutstanding() {
        return this.outstanding;
    }

    public Claim outstanding(Double outstanding) {
        this.outstanding = outstanding;
        return this;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Double getClaimAmount() {
        return this.claimAmount;
    }

    public Claim claimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
        return this;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Boolean getCommulative() {
        return this.commulative;
    }

    public Claim commulative(Boolean commulative) {
        this.commulative = commulative;
        return this;
    }

    public void setCommulative(Boolean commulative) {
        this.commulative = commulative;
    }

    public Customer getCustomerId() {
        return this.customerId;
    }

    public Claim customerId(Customer customer) {
        this.setCustomerId(customer);
        return this;
    }

    public void setCustomerId(Customer customer) {
        this.customerId = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Claim)) {
            return false;
        }
        return id != null && id.equals(((Claim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Claim{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", customerId=" + getCustomerId() +
            ", accidentDate='" + getAccidentDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", claimType='" + getClaimType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lineOfBusiness=" + getLineOfBusiness() +
            ", productId=" + getProductId() +
            ", paid=" + getPaid() +
            ", outstanding=" + getOutstanding() +
            ", claimAmount=" + getClaimAmount() +
            ", commulative='" + getCommulative() + "'" +
            "}";
    }
}
