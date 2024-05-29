package com.hust.itss.web.rest;

import static com.hust.itss.domain.UrlAsserts.*;
import static com.hust.itss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.IntegrationTest;
import com.hust.itss.domain.Url;
import com.hust.itss.repository.UrlRepository;
import com.hust.itss.service.dto.UrlDTO;
import com.hust.itss.service.mapper.UrlMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UrlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UrlResourceIT {

    private static final String DEFAULT_DRIVE_URL = "AAAAAAAAAA";
    private static final String UPDATED_DRIVE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/urls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlMapper urlMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUrlMockMvc;

    private Url url;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Url createEntity(EntityManager em) {
        Url url = new Url().driveUrl(DEFAULT_DRIVE_URL).type(DEFAULT_TYPE);
        return url;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Url createUpdatedEntity(EntityManager em) {
        Url url = new Url().driveUrl(UPDATED_DRIVE_URL).type(UPDATED_TYPE);
        return url;
    }

    @BeforeEach
    public void initTest() {
        url = createEntity(em);
    }

    @Test
    @Transactional
    void createUrl() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);
        var returnedUrlDTO = om.readValue(
            restUrlMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urlDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UrlDTO.class
        );

        // Validate the Url in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUrl = urlMapper.toEntity(returnedUrlDTO);
        assertUrlUpdatableFieldsEquals(returnedUrl, getPersistedUrl(returnedUrl));
    }

    @Test
    @Transactional
    void createUrlWithExistingId() throws Exception {
        // Create the Url with an existing ID
        url.setId(1L);
        UrlDTO urlDTO = urlMapper.toDto(url);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUrls() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        // Get all the urlList
        restUrlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(url.getId().intValue())))
            .andExpect(jsonPath("$.[*].driveUrl").value(hasItem(DEFAULT_DRIVE_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getUrl() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        // Get the url
        restUrlMockMvc
            .perform(get(ENTITY_API_URL_ID, url.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(url.getId().intValue()))
            .andExpect(jsonPath("$.driveUrl").value(DEFAULT_DRIVE_URL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingUrl() throws Exception {
        // Get the url
        restUrlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUrl() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the url
        Url updatedUrl = urlRepository.findById(url.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUrl are not directly saved in db
        em.detach(updatedUrl);
        updatedUrl.driveUrl(UPDATED_DRIVE_URL).type(UPDATED_TYPE);
        UrlDTO urlDTO = urlMapper.toDto(updatedUrl);

        restUrlMockMvc
            .perform(put(ENTITY_API_URL_ID, urlDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urlDTO)))
            .andExpect(status().isOk());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUrlToMatchAllProperties(updatedUrl);
    }

    @Test
    @Transactional
    void putNonExistingUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(put(ENTITY_API_URL_ID, urlDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(urlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUrlWithPatch() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the url using partial update
        Url partialUpdatedUrl = new Url();
        partialUpdatedUrl.setId(url.getId());

        restUrlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUrl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUrl))
            )
            .andExpect(status().isOk());

        // Validate the Url in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrlUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUrl, url), getPersistedUrl(url));
    }

    @Test
    @Transactional
    void fullUpdateUrlWithPatch() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the url using partial update
        Url partialUpdatedUrl = new Url();
        partialUpdatedUrl.setId(url.getId());

        partialUpdatedUrl.driveUrl(UPDATED_DRIVE_URL).type(UPDATED_TYPE);

        restUrlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUrl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUrl))
            )
            .andExpect(status().isOk());

        // Validate the Url in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrlUpdatableFieldsEquals(partialUpdatedUrl, getPersistedUrl(partialUpdatedUrl));
    }

    @Test
    @Transactional
    void patchNonExistingUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, urlDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(urlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(urlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUrl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        url.setId(longCount.incrementAndGet());

        // Create the Url
        UrlDTO urlDTO = urlMapper.toDto(url);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrlMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(urlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Url in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUrl() throws Exception {
        // Initialize the database
        urlRepository.saveAndFlush(url);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the url
        restUrlMockMvc.perform(delete(ENTITY_API_URL_ID, url.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return urlRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Url getPersistedUrl(Url url) {
        return urlRepository.findById(url.getId()).orElseThrow();
    }

    protected void assertPersistedUrlToMatchAllProperties(Url expectedUrl) {
        assertUrlAllPropertiesEquals(expectedUrl, getPersistedUrl(expectedUrl));
    }

    protected void assertPersistedUrlToMatchUpdatableProperties(Url expectedUrl) {
        assertUrlAllUpdatablePropertiesEquals(expectedUrl, getPersistedUrl(expectedUrl));
    }
}
