package com.example.weathertelegrambot.bot.services.impl;

import com.example.weathertelegrambot.bot.dao.UserLocationDao;
import com.example.weathertelegrambot.bot.dto.SetUserLocationDto;
import com.example.weathertelegrambot.bot.models.UserLocationEntity;
import com.example.weathertelegrambot.bot.services.UserLocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserLocationServiceImpl implements UserLocationService {
    private UserLocationDao userLocationDao;

    @Override
    public UserLocationEntity findUserLocationById(Long id) {
        var optionalUser = this.userLocationDao.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new EntityNotFoundException("User has not been found.");
    }


    @Override
    public UserLocationEntity setUserLocationById(Long id, SetUserLocationDto userLocationDto) {
        var optionalUserLocation = this.userLocationDao.findById(id);
        return optionalUserLocation
                .map(userLocationEntity -> this.updateUserLocationByUserLocation(userLocationEntity, userLocationDto))
                .orElseGet(() -> this.createNewUserLocationById(id, userLocationDto));
    }


    private UserLocationEntity createNewUserLocationById(Long id, SetUserLocationDto dto) {
        UserLocationEntity userLocationEntity = new UserLocationEntity(id, dto.latitude(), dto.longitude());
        return userLocationDao.save(userLocationEntity);
    }

    private UserLocationEntity updateUserLocationByUserLocation(UserLocationEntity userLocationEntity, SetUserLocationDto dto) {
        userLocationEntity.setLatitude(dto.latitude());
        userLocationEntity.setLongitude(dto.longitude());
        return this.userLocationDao.save(userLocationEntity);
    }
}
