package net.arshaa.rat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.arshaa.rat.entity.Rooms;

public interface RoomRepository extends JpaRepository<Rooms, Integer>{

	public Optional<List<Rooms>> findByFloorId(Integer id);



}
