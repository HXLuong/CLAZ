package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Customer;
import com.claz.models.Staff;
import com.claz.repositories.StaffRepository;
import com.claz.services.StaffService;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public List<Staff> findAll() {
		return staffRepository.findAll();
	}

	@Override
	public Optional<Staff> findByUsername(String username) {
		return staffRepository.findByUsername(username);
	}

	@Override
	public Staff create(Staff staff) {
		return staffRepository.save(staff);
	}

	@Override
	public Staff update(Staff staff) {
		return staffRepository.save(staff);
	}

	@Override
	public void delete(String username) {
		staffRepository.deleteById(username);
	}

	@Override
	public Staff GetUsername(String username) {
		return staffRepository.findByUsername(username).get();
	}

	public Optional<Staff> getname(String username) {
		return staffRepository.findByUsername(username);
	}

}
