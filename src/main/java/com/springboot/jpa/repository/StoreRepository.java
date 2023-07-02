package com.springboot.jpa.repository;

import com.springboot.jpa.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
