package com.example.lab6_1;

import com.example.lab6_1.entity.Student;
import com.example.lab6_1.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentRepositoryTest {

    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeAll
    void startContainer() {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @BeforeEach
    void setUp() {
        student = new Student(null, "Alice", "alice@example.com");
        student = studentRepository.save(student);
    }

    @Test
    void shouldSaveStudent() {
        assertThat(student.getId()).isNotNull();
    }

    @Test
    void shouldFindStudentById() {
        Optional<Student> found = studentRepository.findById(student.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    void shouldUpdateStudent() {
        student.setEmail("updated@example.com");
        studentRepository.save(student);

        Student updated = studentRepository.findById(student.getId()).orElseThrow();
        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void shouldDeleteStudent() {
        studentRepository.deleteById(student.getId());
        Optional<Student> deleted = studentRepository.findById(student.getId());
        assertThat(deleted).isEmpty();
    }

	@Test
	void shouldFindAllStudents() {
		List<Student> students = studentRepository.findAll();
		assertThat(students).hasSizeGreaterThanOrEqualTo(3);
	}

	@Test
	void shouldFindStudentByEmail() {
		Optional<Student> student = studentRepository.findAll().stream()
				.filter(s -> s.getEmail().equals("alice@example.com"))
				.findFirst();
		assertThat(student).isPresent();
		assertThat(student.get().getName()).isEqualTo("Alice");
	}

}
