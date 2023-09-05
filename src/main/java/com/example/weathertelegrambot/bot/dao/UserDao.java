package com.example.weathertelegrambot.bot.dao;

import com.example.weathertelegrambot.bot.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
}