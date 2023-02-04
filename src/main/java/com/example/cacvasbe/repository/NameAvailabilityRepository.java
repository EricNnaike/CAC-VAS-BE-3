package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.NameAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NameAvailabilityRepository extends JpaRepository<NameAvailability, Long> {
    @Query(value = "select * from name_availability where reservation_approved = false and (upper(option1) like UPPER(concat('%', :keyword, '%')) or upper(option2) like UPPER(concat('%', :keyword, '%')))", nativeQuery = true)
    List<NameAvailability> findAllByOption1IgnoreCaseOrOption2IgnoreCase(String keyword);
}
