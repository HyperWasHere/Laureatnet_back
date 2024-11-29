package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);

    @Query("SELECT u FROM UserAccount u WHERE u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = ?1)")
    public Collection<UserAccount> findAllWithoutUsersBlocked(Long id);

    @Query("SELECT u FROM UserAccount u WHERE u.id <> ?1")
    List<UserAccount> findAllUsersExceptThisUserId(Long userId);
}
