package net.arshaa.rat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import net.arshaa.rat.entity.Floors;

public interface FloorRepository extends JpaRepository<Floors, Integer>{

	public Optional<List<Floors>> findByBuildingId(Integer id);

}
