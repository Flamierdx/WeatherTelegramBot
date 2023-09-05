package com.example.weathertelegrambot.bot.services.impl;

import com.example.weathertelegrambot.bot.dao.UserDao;
import com.example.weathertelegrambot.bot.dto.SetUserDto;
import com.example.weathertelegrambot.bot.models.UserEntity;
import com.example.weathertelegrambot.bot.models.UserState;
import com.example.weathertelegrambot.bot.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return this.userDao.findById(id);
    }

    @Override
    public UserEntity findUserByIdOrThrow(Long id) {
        var optionalUser = this.findUserById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new EntityNotFoundException("User has not been found.");
    }


    @Override
    public UserEntity setUserById(Long id, SetUserDto dto) {
        var optionalUserLocation = this.userDao.findById(id);
        return optionalUserLocation
                .map(userEntity -> this.updateUserByUserLocation(userEntity, dto))
                .orElseGet(() -> this.createNewUserById(id, dto));
    }


    private UserEntity createNewUserById(Long id, SetUserDto dto) {
        UserEntity userEntity = new UserEntity(id, dto.getLatitude(), dto.getState(), dto.getLongitude());
        return userDao.save(userEntity);
    }

    private UserEntity updateUserByUserLocation(UserEntity userEntity, SetUserDto dto) {
        userEntity.setLocationLatitude(dto.getLatitude());
        userEntity.setLocationLongitude(dto.getLongitude());
        userEntity.setState(dto.getState());
        return this.userDao.save(userEntity);
    }
}
