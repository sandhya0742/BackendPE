package net.arshaa.rat.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import net.arshaa.rat.entity.Buildings;

public interface BuildingRepository extends JpaRepository<Buildings, Integer> {


}
