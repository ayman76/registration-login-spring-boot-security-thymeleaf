package com.global.regLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.regLog.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

}
