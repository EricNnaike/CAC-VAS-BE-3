package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.ProcessTaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessTaskHistoryRepository extends JpaRepository<ProcessTaskHistory, Long> {
    Optional<ProcessTaskHistory> findProcessTypeHistoryByprocessTypeFk(Long processTypeFk);
}
