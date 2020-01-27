package com.auth0.jwk;

import java.util.Collections;
import java.util.List;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Represents a set of JSON Web Keys (JWK) used to verify the signature of JWTs.
 */
public class JwkSet {

    private final List<Jwk> keys;

    /**
     * Create a new empty JWK set.
     */
    public JwkSet() {
        this(Collections.<Jwk>emptyList());
    }

    /**
     * Creates a new JWK set with a single key.
     * @param key the JWK. Must not be {@code null}.
     */
    public JwkSet(final Jwk key) {
        this(Collections.singletonList(key));
        checkArgument(key != null, "The JWK must not be null");
    }

    /**
     * Creates a new JWK set with a list of keys.
     * @param keys the list of keys. Must not be {@code null}.
     */
    public JwkSet(final List<Jwk> keys) {
        checkArgument(keys != null, "The JWK list must not be null");
        this.keys = Collections.unmodifiableList(keys);
    }

    /**
     * Get the keys of this JWK set.
     * @return The keys, or an empty list if there are no keys.
     */
    public List<Jwk> getKeys() {
        return keys;
    }

    /**
     * Get the key in this JWK set matching the key ID.
     * @param keyId The key ID (kid) of the JWK to get.
     * @return The JWK or {@code null} if no JWK is found.
     */
    public Jwk getKey(String keyId) {
        for (Jwk key : keys) {
            if (key.getId() != null && key.getId().equals(keyId)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Get the key in this JWK set matching the key ID. If the key ID is null and there is only one JWK in this set,
     * that JWK will be returned.
     *
     * <p>This method enables backwards compatibility that allows the only JWK to be returned if the specified keyId is
     * {@code null}</p>
     *
     * @param keyId The key ID (kid) of the JWK to get. If {@code null} and the set contains one Jwk, that JWK will be returned.
     * @return The JWK or {@code null} if the set contains no JWKs.
     */
    Jwk getKeyByIdOrSingleEntry(String keyId) {
        if (keyId == null && keys.size() == 1) {
            return keys.get(0);
        }

        return getKey(keyId);
    }
}
