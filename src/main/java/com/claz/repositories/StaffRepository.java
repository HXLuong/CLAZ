package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Staff;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
	Optional<Staff> findByUsername(String name);
	
	@Query("select o from Staff o where o.email like ?1")
	Optional<Staff> findByEmail(String username);
}
