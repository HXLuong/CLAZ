package com.claz.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.claz.models.Staff;
import com.claz.models.Staff;
import com.claz.models.Staff;
import com.claz.repositories.StaffRepository;
import com.claz.repositories.StaffRepository;
import com.claz.repositories.StaffRepository;
import com.claz.serviceImpls.StaffServiceImpl;
import com.claz.serviceImpls.StaffServiceImpl;
import com.claz.serviceImpls.StaffServiceImpl;
import com.claz.services.StaffService;

@SpringBootTest
public class StaffServiceTest {

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	StaffServiceImpl staffServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Staff mockStaffRequest() {
		Staff staff = new Staff();
		staff.setUsername("example_user4");
		staff.setFullname("Example Fullname");
		staff.setPassword("example_password");
		staff.setEmail("example@example.com");
		staff.setPhone("1234567890");
		staff.setImage("example_image.jpg");
		staff.setGender(true);
		staff.setRole(false);
		staff.setCreated_at(LocalDateTime.now());
		return staff;
	}

	// Create
	@Test
	public void testCreateStaff_CreateNull() {
		assertThrows(Exception.class, () -> {
			Staff model = null;
			staffServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateStaff_Create() {
		assertThrows(Exception.class, () -> {
			Staff model = mockStaffRequest();
			staffServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateStaff_updateNull() {
		assertThrows(Exception.class, () -> {
			Staff model = null;
			staffServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateStaff_updateValidate() {
		Staff staff = new Staff();
		staff.setUsername("example_user");
		staff.setFullname("Example Fullname");
		staff.setPassword("example_ssspassword");
		staff.setEmail("example@example.com");
		staff.setPhone("1234567890");
		staff.setImage("example_image.jpg");
		staff.setGender(true);
		staff.setRole(false);
		staff.setCreated_at(LocalDateTime.now());
		staffServiceImpl.update(staff);
	}

	// Delete
	@Test
	public void testDeleteStaff_deleteNullId() {
		String id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			staffServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteStaff_deleteEmpty() {
		String id = "";
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			staffServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteStaff_deleteValite() {
		String id = "example_user2";
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			staffServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindByStaff
	@Test
	void testFindAllStaff() {
		try {
			List<Staff> list = staffServiceImpl.findAll();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindById
	@Test
	void testSelectByFindByIdWithNull() {
		try {
			String id = null;
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Optional<Staff> list = staffServiceImpl.findByUsername(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			String id = "example_user";
			Optional<Staff> list = staffServiceImpl.findByUsername(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

// FindByUserName
	@Test
	void testSelectByFindByGetUsernameWithNull() {
		try {
			String id = null;
			Optional<Staff> list = staffServiceImpl.getname(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByGetUsernameWithEmpty() {
		try {
			String id = "";
			Optional<Staff> list = staffServiceImpl.getname(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByGetUsernameWithValidate() {
		try {
			String id = "example_user";
			Optional<Staff> list = staffServiceImpl.getname(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindByEmail

	@Test
	void testSelectByFindByEmailWithEmpty() {
		try {
			String id = "";
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Optional<Staff> list = staffServiceImpl.findByEmail(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByEmailWithNull() {
		try {
			String id = null;
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Optional<Staff> list = staffServiceImpl.findByEmail(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByEmailWithValidate() {
		try {
			String id = "example@example.com";
			Optional<Staff> list = staffServiceImpl.findByUsername(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}