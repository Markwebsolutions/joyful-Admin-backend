package com.joyful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.ProductEnquiry;

public interface ProductEnquiryRepository extends JpaRepository<ProductEnquiry, Long> {
}
