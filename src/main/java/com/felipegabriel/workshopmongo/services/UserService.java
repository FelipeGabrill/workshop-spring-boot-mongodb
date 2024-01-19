package com.felipegabriel.workshopmongo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipegabriel.workshopmongo.domain.User;
import com.felipegabriel.workshopmongo.dto.UserDTO;
import com.felipegabriel.workshopmongo.repository.UserRepository;
import com.felipegabriel.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		try {
			Optional<User> newObj = repo.findById(obj.getId());
			User user = newObj.get();
			updateData(user, obj);
			return repo.save(user);
		} catch (NoSuchElementException e) {
		throw new ObjectNotFoundException("Object not found");
		}
	}

	public void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
