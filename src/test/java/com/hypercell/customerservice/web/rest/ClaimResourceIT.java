package com.hypercell.customerservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.customerservice.IntegrationTest;
import com.hypercell.customerservice.domain.Claim;
import com.hypercell.customerservice.domain.enumeration.ClaimType;
import com.hypercell.customerservice.repository.ClaimRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClaimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final LocalDate DEFAULT_ACCIDENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCIDENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ClaimType DEFAULT_CLAIM_TYPE = ClaimType.Internal;
    private static final ClaimType UPDATED_CLAIM_TYPE = ClaimType.External;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_LINE_OF_BUSINESS = 1L;
    private static final Long UPDATED_LINE_OF_BUSINESS = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Double DEFAULT_PAID = 1D;
    private static final Double UPDATED_PAID = 2D;

    private static final Double DEFAULT_OUTSTANDING = 1D;
    private static final Double UPDATED_OUTSTANDING = 2D;

    private static final Double DEFAULT_CLAIM_AMOUNT = 1D;
    private static final Double UPDATED_CLAIM_AMOUNT = 2D;

    private static final Boolean DEFAULT_COMMULATIVE = false;
    private static final Boolean UPDATED_COMMULATIVE = true;

    private static final String ENTITY_API_URL = "/api/claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimMockMvc;

    private Claim claim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createEntity(EntityManager em) {
        Claim claim = new Claim()
            .description(DEFAULT_DESCRIPTION)
            .customerId(DEFAULT_CUSTOMER_ID)
            .accidentDate(DEFAULT_ACCIDENT_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .claimType(DEFAULT_CLAIM_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .lineOfBusiness(DEFAULT_LINE_OF_BUSINESS)
            .productId(DEFAULT_PRODUCT_ID)
            .paid(DEFAULT_PAID)
            .outstanding(DEFAULT_OUTSTANDING)
            .claimAmount(DEFAULT_CLAIM_AMOUNT)
            .commulative(DEFAULT_COMMULATIVE);
        return claim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createUpdatedEntity(EntityManager em) {
        Claim claim = new Claim()
            .description(UPDATED_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .accidentDate(UPDATED_ACCIDENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .claimType(UPDATED_CLAIM_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lineOfBusiness(UPDATED_LINE_OF_BUSINESS)
            .productId(UPDATED_PRODUCT_ID)
            .paid(UPDATED_PAID)
            .outstanding(UPDATED_OUTSTANDING)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .commulative(UPDATED_COMMULATIVE);
        return claim;
    }

    @BeforeEach
    public void initTest() {
        claim = createEntity(em);
    }

    @Test
    @Transactional
    void createClaim() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();
        // Create the Claim
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate + 1);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClaim.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testClaim.getAccidentDate()).isEqualTo(DEFAULT_ACCIDENT_DATE);
        assertThat(testClaim.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClaim.getClaimType()).isEqualTo(DEFAULT_CLAIM_TYPE);
        assertThat(testClaim.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClaim.getLineOfBusiness()).isEqualTo(DEFAULT_LINE_OF_BUSINESS);
        assertThat(testClaim.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testClaim.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testClaim.getOutstanding()).isEqualTo(DEFAULT_OUTSTANDING);
        assertThat(testClaim.getClaimAmount()).isEqualTo(DEFAULT_CLAIM_AMOUNT);
        assertThat(testClaim.getCommulative()).isEqualTo(DEFAULT_COMMULATIVE);
    }

    @Test
    @Transactional
    void createClaimWithExistingId() throws Exception {
        // Create the Claim with an existing ID
        claim.setId(1L);

        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaims() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].accidentDate").value(hasItem(DEFAULT_ACCIDENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].claimType").value(hasItem(DEFAULT_CLAIM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lineOfBusiness").value(hasItem(DEFAULT_LINE_OF_BUSINESS.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].outstanding").value(hasItem(DEFAULT_OUTSTANDING.doubleValue())))
            .andExpect(jsonPath("$.[*].claimAmount").value(hasItem(DEFAULT_CLAIM_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].commulative").value(hasItem(DEFAULT_COMMULATIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get the claim
        restClaimMockMvc
            .perform(get(ENTITY_API_URL_ID, claim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claim.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.accidentDate").value(DEFAULT_ACCIDENT_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.claimType").value(DEFAULT_CLAIM_TYPE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lineOfBusiness").value(DEFAULT_LINE_OF_BUSINESS.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.doubleValue()))
            .andExpect(jsonPath("$.outstanding").value(DEFAULT_OUTSTANDING.doubleValue()))
            .andExpect(jsonPath("$.claimAmount").value(DEFAULT_CLAIM_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.commulative").value(DEFAULT_COMMULATIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingClaim() throws Exception {
        // Get the claim
        restClaimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim
        Claim updatedClaim = claimRepository.findById(claim.getId()).get();
        // Disconnect from session so that the updates on updatedClaim are not directly saved in db
        em.detach(updatedClaim);
        updatedClaim
            .description(UPDATED_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .accidentDate(UPDATED_ACCIDENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .claimType(UPDATED_CLAIM_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lineOfBusiness(UPDATED_LINE_OF_BUSINESS)
            .productId(UPDATED_PRODUCT_ID)
            .paid(UPDATED_PAID)
            .outstanding(UPDATED_OUTSTANDING)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .commulative(UPDATED_COMMULATIVE);

        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaim.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testClaim.getAccidentDate()).isEqualTo(UPDATED_ACCIDENT_DATE);
        assertThat(testClaim.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClaim.getClaimType()).isEqualTo(UPDATED_CLAIM_TYPE);
        assertThat(testClaim.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClaim.getLineOfBusiness()).isEqualTo(UPDATED_LINE_OF_BUSINESS);
        assertThat(testClaim.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testClaim.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testClaim.getOutstanding()).isEqualTo(UPDATED_OUTSTANDING);
        assertThat(testClaim.getClaimAmount()).isEqualTo(UPDATED_CLAIM_AMOUNT);
        assertThat(testClaim.getCommulative()).isEqualTo(UPDATED_COMMULATIVE);
    }

    @Test
    @Transactional
    void putNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .description(UPDATED_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .accidentDate(UPDATED_ACCIDENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .lineOfBusiness(UPDATED_LINE_OF_BUSINESS)
            .productId(UPDATED_PRODUCT_ID)
            .commulative(UPDATED_COMMULATIVE);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaim.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testClaim.getAccidentDate()).isEqualTo(UPDATED_ACCIDENT_DATE);
        assertThat(testClaim.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClaim.getClaimType()).isEqualTo(DEFAULT_CLAIM_TYPE);
        assertThat(testClaim.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClaim.getLineOfBusiness()).isEqualTo(UPDATED_LINE_OF_BUSINESS);
        assertThat(testClaim.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testClaim.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testClaim.getOutstanding()).isEqualTo(DEFAULT_OUTSTANDING);
        assertThat(testClaim.getClaimAmount()).isEqualTo(DEFAULT_CLAIM_AMOUNT);
        assertThat(testClaim.getCommulative()).isEqualTo(UPDATED_COMMULATIVE);
    }

    @Test
    @Transactional
    void fullUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .description(UPDATED_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .accidentDate(UPDATED_ACCIDENT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .claimType(UPDATED_CLAIM_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lineOfBusiness(UPDATED_LINE_OF_BUSINESS)
            .productId(UPDATED_PRODUCT_ID)
            .paid(UPDATED_PAID)
            .outstanding(UPDATED_OUTSTANDING)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .commulative(UPDATED_COMMULATIVE);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaim.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testClaim.getAccidentDate()).isEqualTo(UPDATED_ACCIDENT_DATE);
        assertThat(testClaim.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClaim.getClaimType()).isEqualTo(UPDATED_CLAIM_TYPE);
        assertThat(testClaim.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClaim.getLineOfBusiness()).isEqualTo(UPDATED_LINE_OF_BUSINESS);
        assertThat(testClaim.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testClaim.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testClaim.getOutstanding()).isEqualTo(UPDATED_OUTSTANDING);
        assertThat(testClaim.getClaimAmount()).isEqualTo(UPDATED_CLAIM_AMOUNT);
        assertThat(testClaim.getCommulative()).isEqualTo(UPDATED_COMMULATIVE);
    }

    @Test
    @Transactional
    void patchNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeDelete = claimRepository.findAll().size();

        // Delete the claim
        restClaimMockMvc
            .perform(delete(ENTITY_API_URL_ID, claim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
