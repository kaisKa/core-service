package com.core.Core.Service.data_submission.submission;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {


    List<Submission> findByServiceId(BigInteger serviceId, Pageable pageable);

    List<Submission> findByServiceId(BigInteger serviceId);

    List<Submission> findByCustomerId(Long customerId, Pageable pageable);

    List<Submission> findByCustomerId(Long customerId);


}
