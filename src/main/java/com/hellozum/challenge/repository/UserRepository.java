package com.hellozum.challenge.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hellozum.challenge.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	List<User> findAll();
}
