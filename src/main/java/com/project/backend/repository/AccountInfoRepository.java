package com.project.backend.repository;

import com.project.backend.model.AccountInfo;
import com.project.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<Member, Long> {

    void save(AccountInfo accountInfo);
}
