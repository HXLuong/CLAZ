package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claz.models.Staff;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
	Optional<Staff> findByUsername(String name);
}
