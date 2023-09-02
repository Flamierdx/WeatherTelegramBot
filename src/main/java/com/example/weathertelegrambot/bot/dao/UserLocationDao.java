package com.example.weathertelegrambot.bot.dao;

import com.example.weathertelegrambot.bot.models.UserLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationDao extends JpaRepository<UserLocationEntity, Long> {
}