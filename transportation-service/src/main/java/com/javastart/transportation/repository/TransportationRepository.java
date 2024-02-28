package com.javastart.transportation.repository;

import com.javastart.transportation.entity.Transportation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportationRepository extends CrudRepository<Transportation, Long> {
    

    Optional<Transportation> save(Transportation transportation);

    // возвращает количество обновленных записей
    @Modifying
    @Transactional
    @Query("UPDATE Transportation t SET t.state = :newState WHERE t.transportationId = :id")
    int updateStateById(@Param("id") Long id, @Param("newState") Transportation.State newState);

    @Query("SELECT t FROM Transportation t WHERE t.state = :state")
    Optional<List<Transportation>> getTransportationsByState(@Param("state") Transportation.State state);

    @Query("SELECT t.idOfUser FROM Transportation t WHERE t.transportationId = :transportationId")
    Long findUserIdByTransportationId(@Param("transportationId") Long transportationId);


}
