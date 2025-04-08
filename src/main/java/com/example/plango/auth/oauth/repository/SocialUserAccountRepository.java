package com.example.plango.auth.oauth.repository;

import com.example.plango.auth.oauth.model.SocialUserAccount;
import com.example.plango.auth.oauth.model.SocialUserAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserAccountRepository extends JpaRepository<SocialUserAccount, SocialUserAccountKey> {
}
