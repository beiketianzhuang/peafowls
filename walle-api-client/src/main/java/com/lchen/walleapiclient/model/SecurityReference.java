package com.lchen.walleapiclient.model;


import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.lchen.walleapiclient.builders.BuilderDefault.defaultIfAbsent;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class SecurityReference {
    private final String reference;
    private final List<AuthorizationScope> scopes;

    public SecurityReference(String reference, AuthorizationScope[] scopes) {
        this.scopes = newArrayList(scopes);
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public List<AuthorizationScope> getScopes() {
        return scopes;
    }

    public static SecurityReferenceBuilder builder() {
        return new SecurityReferenceBuilder();
    }

    public static class SecurityReferenceBuilder {
        SecurityReferenceBuilder() {
        }

        private String reference;
        private AuthorizationScope[] scopes;

        public SecurityReferenceBuilder reference(String reference) {
            this.reference = defaultIfAbsent(reference, this.reference);
            return this;
        }

        public SecurityReferenceBuilder scopes(AuthorizationScope[] scopes) {
            this.scopes = defaultIfAbsent(scopes, this.scopes);
            return this;
        }

        public SecurityReference build() {
            return new SecurityReference(reference, scopes);
        }
    }
}
