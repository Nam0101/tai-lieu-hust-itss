package com.hust.itss.web.rest;

import static com.hust.itss.domain.CommentsAsserts.*;
import static com.hust.itss.web.rest.TestUtil.createUpdateProxyForBean;
import static com.hust.itss.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.IntegrationTest;
import com.hust.itss.domain.Comments;
import com.hust.itss.repository.CommentsRepository;
import com.hust.itss.service.dto.CommentsDTO;
import com.hust.itss.service.mapper.CommentsMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link CommentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommentsResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final UUID DEFAULT_ANONYMOUS_ID = UUID.randomUUID();
    private static final UUID UPDATED_ANONYMOUS_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentsMockMvc;

    private Comments comments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createEntity(EntityManager em) {
        Comments comments = new Comments().createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT).anonymousId(DEFAULT_ANONYMOUS_ID);
        return comments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createUpdatedEntity(EntityManager em) {
        Comments comments = new Comments().createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).anonymousId(UPDATED_ANONYMOUS_ID);
        return comments;
    }

    @BeforeEach
    public void initTest() {
        comments = createEntity(em);
    }

    @Test
    @Transactional
    void createComments() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);
        var returnedCommentsDTO = om.readValue(
            restCommentsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commentsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommentsDTO.class
        );

        // Validate the Comments in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedComments = commentsMapper.toEntity(returnedCommentsDTO);
        assertCommentsUpdatableFieldsEquals(returnedComments, getPersistedComments(returnedComments));
    }

    @Test
    @Transactional
    void createCommentsWithExistingId() throws Exception {
        // Create the Comments with an existing ID
        comments.setId(1L);
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get all the commentsList
        restCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comments.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].anonymousId").value(hasItem(DEFAULT_ANONYMOUS_ID.toString())));
    }

    @Test
    @Transactional
    void getComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get the comments
        restCommentsMockMvc
            .perform(get(ENTITY_API_URL_ID, comments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comments.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.anonymousId").value(DEFAULT_ANONYMOUS_ID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingComments() throws Exception {
        // Get the comments
        restCommentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comments
        Comments updatedComments = commentsRepository.findById(comments.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedComments are not directly saved in db
        em.detach(updatedComments);
        updatedComments.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).anonymousId(UPDATED_ANONYMOUS_ID);
        CommentsDTO commentsDTO = commentsMapper.toDto(updatedComments);

        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommentsToMatchAllProperties(updatedComments);
    }

    @Test
    @Transactional
    void putNonExistingComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommentsWithPatch() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comments using partial update
        Comments partialUpdatedComments = new Comments();
        partialUpdatedComments.setId(comments.getId());

        partialUpdatedComments.updatedAt(UPDATED_UPDATED_AT);

        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComments.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComments))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommentsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedComments, comments), getPersistedComments(comments));
    }

    @Test
    @Transactional
    void fullUpdateCommentsWithPatch() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comments using partial update
        Comments partialUpdatedComments = new Comments();
        partialUpdatedComments.setId(comments.getId());

        partialUpdatedComments.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).anonymousId(UPDATED_ANONYMOUS_ID);

        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComments.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComments))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommentsUpdatableFieldsEquals(partialUpdatedComments, getPersistedComments(partialUpdatedComments));
    }

    @Test
    @Transactional
    void patchNonExistingComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comments.setId(longCount.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(commentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the comments
        restCommentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, comments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commentsRepository.count();
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

    protected Comments getPersistedComments(Comments comments) {
        return commentsRepository.findById(comments.getId()).orElseThrow();
    }

    protected void assertPersistedCommentsToMatchAllProperties(Comments expectedComments) {
        assertCommentsAllPropertiesEquals(expectedComments, getPersistedComments(expectedComments));
    }

    protected void assertPersistedCommentsToMatchUpdatableProperties(Comments expectedComments) {
        assertCommentsAllUpdatablePropertiesEquals(expectedComments, getPersistedComments(expectedComments));
    }
}
