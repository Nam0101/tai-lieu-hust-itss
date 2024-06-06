package com.hust.itss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hust.itss.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String getLogin() {
        return this.login;
    }

    public @NotNull @Size(min = 60, max = 60) String getPassword() {
        return this.password;
    }

    public @Size(max = 50) String getFirstName() {
        return this.firstName;
    }

    public @Size(max = 50) String getLastName() {
        return this.lastName;
    }

    public @Email @Size(min = 5, max = 254) String getEmail() {
        return this.email;
    }

    public @NotNull boolean isActivated() {
        return this.activated;
    }

    public @Size(min = 2, max = 10) String getLangKey() {
        return this.langKey;
    }

    public @Size(max = 256) String getImageUrl() {
        return this.imageUrl;
    }

    public @Size(max = 20) String getActivationKey() {
        return this.activationKey;
    }

    public @Size(max = 20) String getResetKey() {
        return this.resetKey;
    }

    public Instant getResetDate() {
        return this.resetDate;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(@NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String login) {
        this.login = login;
    }

    @JsonIgnore
    public void setPassword(@NotNull @Size(min = 60, max = 60) String password) {
        this.password = password;
    }

    public void setLastName(@Size(max = 50) String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@Email @Size(min = 5, max = 254) String email) {
        this.email = email;
    }

    public void setActivated(@NotNull boolean activated) {
        this.activated = activated;
    }

    public void setLangKey(@Size(min = 2, max = 10) String langKey) {
        this.langKey = langKey;
    }

    public void setImageUrl(@Size(max = 256) String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonIgnore
    public void setActivationKey(@Size(max = 20) String activationKey) {
        this.activationKey = activationKey;
    }

    @JsonIgnore
    public void setResetKey(@Size(max = 20) String resetKey) {
        this.resetKey = resetKey;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    @JsonIgnore
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setFirstName(@Size(max = 50) String firstName) {
        this.firstName = firstName;
    }
}
