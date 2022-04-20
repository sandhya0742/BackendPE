package net.arshaa.rat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Models.BedSummary;
import Models.BedsCount;
import Models.BedsInfo;
import Models.BuildingId;
import Models.BuildingInfo;
import Models.BuildingModel;
import Models.FloorsInfo;
import Models.NewBuildModel;
import Models.RoomsInfo;
import common.Guest;
import common.User;
import net.arshaa.rat.entity.Bed;
import net.arshaa.rat.entity.Buildings;
import net.arshaa.rat.entity.Floors;
import net.arshaa.rat.entity.Rooms;
import net.arshaa.rat.entity.UsersMaster;
import net.arshaa.rat.repository.BedRepository;
import net.arshaa.rat.repository.BedSummaryRepo;
import net.arshaa.rat.repository.FloorRepository;
import net.arshaa.rat.repository.BuildingRepository;
import net.arshaa.rat.repository.RoomRepository;
import net.arshaa.rat.repository.UsersMasterRepo;

import org.springframework.web.client.RestTemplate;


@CrossOrigin("*")
@RestController
@RequestMapping("/bed")
public class BedController {

	@Autowired
	private BedRepository bedrepo;

	@Autowired
	private BuildingRepository buildingRepo;

	@Autowired
	private FloorRepository floorRepo;

	@Autowired
	private RoomRepository roomRepo;

	@Autowired
	private BedSummaryRepo bedsumRepo;

	@Autowired(required = true)
	private UsersMasterRepo uMaster;
	// @Autowired
//	private NewBedRepository newBedRepo;

	@Autowired
	@Lazy
	private RestTemplate template;

//Api for test

	@GetMapping(path = "/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("hello", HttpStatus.OK);
	}
// POST API TO ADD MASTER DATA
	@PostMapping(path = "/addBuilding")
	public ResponseEntity<UsersMaster> addBuilding(@RequestBody UsersMaster newMaster) {
		try {
			boolean check=uMaster.existsByEmail(newMaster.getEmail());
			if(check==true)
			{
				System.out.println(check);
				return new ResponseEntity("Email already exist", HttpStatus.OK);
			}
			else {
				UsersMaster usersMaster = uMaster.save(newMaster);
			    //CommonBuildings buildings=new CommonBuildings();
			    Buildings builds = new Buildings();
			    builds.setBuilding_name(newMaster.getBuilding_name());
			    builds.setCreatedBy(newMaster.getCreatedBy());
			    java.sql.Date tSqlDate = new java.sql.Date(newMaster.getCreatedOn().getTime());
			    builds.setCreatedOn(tSqlDate);
			    Buildings building = buildingRepo.save(builds);
			    User user=new User();
			    user.setEmail(newMaster.getEmail());
			    user.setPassword(newMaster.getPassword());
			    user.setUserName(newMaster.getUserName());
			    user.setUserType(newMaster.getUserType());
			    user.setBuildingId(builds.getBuilding_id());
			    user.setUserPhoneNumber(newMaster.getUserPhoneNumber());
			    
			    template.postForObject("http://loginService/login/addUsers", user, User.class);
			    return new ResponseEntity("Added Sucessfull", HttpStatus.OK);
			}
		}
		
	    catch (Exception e) {
		    return new ResponseEntity("Something went wrong", HttpStatus.OK);
		}

	}
	// GET API TO GET ALL MASTER DATA
	@GetMapping("/getAllMasterData")
	public ResponseEntity<List<UsersMaster>> getAllData() {
	    try {
	        List<UsersMaster> usersMasterList = uMaster.findAll();
	        return new ResponseEntity(usersMasterList, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity("Something went wrong", HttpStatus.OK);
	    }
	}
	// DELETE API TO DELETE MASTER DATA
	@DeleteMapping("/deleteMasterData/{id}")
	public ResponseEntity<String> deleteMasterData(@PathVariable int id) {

		try {
			uMaster.deleteById(id);
			return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong", HttpStatus.OK);
		}
	}
	
	//UPDATE API TO UPDATE ALL MASTER DATA
	
	/*
	 * @PutMapping("/updateMasterDataById/{id}") public ResponseEntity<Bed>
	 * updateMasterData(@PathVariable int id, @RequestBody UsersMaster newMaster) {
	 * try { UsersMaster master = uMaster.getById(id);
	 * 
	 * master.setBuilding_name(newMaster.getBuilding_name());
	 * master.setEmail(newMaster.getEmail());
	 * master.setUserName(newMaster.getUserName());
	 * master.setUserType(newMaster.getUserType());
	 * master.setUserPhoneNumber(newMaster.getUserPhoneNumber()); UsersMaster
	 * updatedMaster = uMaster.save(master); Buildings builds = new Buildings();
	 * builds.setBuilding_name(newMaster.getBuilding_name()); Buildings building =
	 * buildingRepo.save(builds); User user=new User();
	 * user.setUserName(newMaster.getUserName());
	 * user.setEmail(newMaster.getEmail());
	 * user.setUserPhoneNumber(newMaster.getUserPhoneNumber()); return new
	 * ResponseEntity("Updated Successfully", HttpStatus.OK); } catch (Exception e)
	 * { return new ResponseEntity("Something went wrong can't able to update",
	 * HttpStatus.ALREADY_REPORTED);
	 * 
	 * } }
	 */
	@PostMapping(path = "/addRoom")
	public ResponseEntity<String> addRoom(@RequestBody Rooms newRoom) {
		try {
			java.sql.Date tSqlDate = new java.sql.Date(newRoom.getCreatedOn().getTime());
			newRoom.setCreatedOn(tSqlDate);
			Rooms room = roomRepo.save(newRoom);
			return new ResponseEntity<>("Room Added Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Something Went Wrong", HttpStatus.OK);
		}
	}

	// Api to add Floor
	@PostMapping(path = "/addFloor")
	public ResponseEntity<String> addFloor(@RequestBody Floors newFloor) {
		try {
			java.sql.Date tSqlDate = new java.sql.Date(newFloor.getCreatedOn().getTime());
			newFloor.setCreatedOn(tSqlDate);
			Floors floor = floorRepo.save(newFloor);
			return new ResponseEntity<>("Floor Added Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Something Went Wrong", HttpStatus.OK);
		}
	}

	@GetMapping("/getAllFloors")
	public ResponseEntity<List<Floors>> getAllFloors() {
		try {
			List<Floors> floor = floorRepo.findAll();
			return new ResponseEntity<>(floor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);
		}
	}

	@GetMapping("/getAllRooms")
	public ResponseEntity<List<Rooms>> getAllRooms() {
		try {
			List<Rooms> room = roomRepo.findAll();
			return new ResponseEntity(room, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);
		}
	}

	
// Post API to add Bed by id

	@PostMapping(path = "/addBed")
	public ResponseEntity<Bed> addBed(@RequestBody Bed newBed) {
		try {
			java.sql.Date tSqlDate = new java.sql.Date(newBed.getCreatedOn().getTime());
			newBed.setCreatedOn(tSqlDate);
			boolean check=bedrepo.existsByBedId(newBed.getBedId());
			if(check==true)
			{
				System.out.println(check);
				return new ResponseEntity("BedId already exist", HttpStatus.OK);
			}
			Bed bed = bedrepo.save(newBed);
			return new ResponseEntity("Bed Added Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);
		}
	}

// APi to update bed by id

	@PutMapping("/updateBedById/{id}")
	public ResponseEntity<Bed> updateBed(@PathVariable int id, @RequestBody Bed bedDetails) {
		try {
			Bed bed = bedrepo.getById(id);

			bed.setAc(bedDetails.isAc());
			bed.setBedId(bedDetails.getBedId());
			bed.setBedName(bedDetails.getBedName());
			bed.setBedStatus(bedDetails.isBedStatus());
			bed.setDefaultRent(bedDetails.getDefaultRent());
			bed.setGuestId(bedDetails.getGuestId());
			bed.setRoomId(bedDetails.getRoomId());
			bed.setSecurityDeposit(bedDetails.getSecurityDeposit());
			Bed updatedBed = bedrepo.save(bed);
			return new ResponseEntity("Bed Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong can't able to update", HttpStatus.ALREADY_REPORTED);

		}
	}

	// Update api to update building data
	@PutMapping("updateBuildingById/{id}")
	public ResponseEntity<Buildings> updateBuilding(@PathVariable int id, @RequestBody Buildings buildingDetils) {
		try {
			Buildings building = buildingRepo.getById(id);
			building.setBuilding_name(buildingDetils.getBuilding_name());
			Buildings updatedBuilding = buildingRepo.save(building);
			return new ResponseEntity("Building Updated Successfully", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity("Something went wrong can't able to update", HttpStatus.ALREADY_REPORTED);
		}

	}

// Api  to delete bed by id

	@DeleteMapping("/deleteBuilding/{id}")
	public ResponseEntity<String> deleteBuilding(@PathVariable int id) {

		try {
			buildingRepo.deleteById(id);
			return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong", HttpStatus.OK);

		}

	}

// Api  to delete bed by id

	@DeleteMapping("/deleteBed/{id}")
	public ResponseEntity<String> deletebed(@PathVariable int id) {

		try {
			bedrepo.deleteById(id);
			return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong", HttpStatus.OK);

		}

	}

	// Api to get beddata
	@GetMapping("/getAllBeds")
	public ResponseEntity<List<Bed>> getAllBeds() {
		try {
			List<Bed> bed = bedrepo.findAll();

			return new ResponseEntity<>(bed, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);

		}
	}

	// Api to get Building data
	@GetMapping("/getAllBuildings")
	public ResponseEntity<List<Buildings>> getAllBuildings() {
		try {
			List<Buildings> buildings = buildingRepo.findAll();

			return new ResponseEntity<>(buildings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);

		}
	}
	// Api to get Building By ID
		@GetMapping("/getBuildingsById/{building_id}")
		public ResponseEntity<List<Buildings>> getBuildingsById(@PathVariable int building_id) {
			try {
				Optional<Buildings> buildings = buildingRepo.findById(building_id);

				return new ResponseEntity(buildings, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity("Something went wrong", HttpStatus.OK);

			}
		}


	// Api to get the count of all beds
	@GetMapping("/getAllBedsCount")
	public ResponseEntity<Integer> getAllBedsCount() {
		List<Bed> bed = bedrepo.findAll();
		List<Bed> bedsCount = bedrepo.findByBedStatus(true);
		int bedCount = bed.size();
		int availableBedsCount = bedsCount.size();
		BedsCount bs = new BedsCount();
		bs.setTotalBeds(bedCount);
		bs.setTotalAvailbleBeds(availableBedsCount);
		return new ResponseEntity(bs, HttpStatus.OK);
	}

	@GetMapping("/getAvailableBeds")
	public ResponseEntity<List<Bed>> getAllAvailableBeds() {
		try {
			List<Bed> bedList = bedrepo.findByBedStatus(true);
			// Bed bed=new Bed();

			return new ResponseEntity<>(bedList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);
		}
	}

// GET ALL BUILDINGS

	@GetMapping(path = "/getBedsByAllBuildings")
	public ResponseEntity<List<BuildingInfo>> getBedsByBuildings() {
		try {
			List<BuildingInfo> info = new ArrayList<>();
			List<Buildings> getBuildings = buildingRepo.findAll();
			if (!getBuildings.isEmpty()) {
				getBuildings.forEach(building -> {
					BuildingInfo newBuild = new BuildingInfo();
					Optional<Buildings> getBuilding = buildingRepo.findById(building.getBuilding_id());
					if (getBuilding.isPresent()) {
						newBuild.setBuildingName(getBuilding.get().getBuilding_name());
						List<FloorsInfo> floorsList = new ArrayList<>();
						Optional<List<Floors>> getFloors = floorRepo
								.findByBuildingId(getBuilding.get().getBuilding_id());
						if (getFloors.isPresent()) {
							getFloors.get().forEach(floor -> {
								FloorsInfo newFloor = new FloorsInfo();
								newFloor.setFloorName(floor.getFloorNumber());
								List<RoomsInfo> roomList = new ArrayList<>();
								Optional<List<Rooms>> getRooms = roomRepo.findByFloorId(floor.getFloorId());
								if (getRooms.isPresent()) {
									getRooms.get().forEach(room -> {
										RoomsInfo newRoom = new RoomsInfo();
										newRoom.setRoomNumber(room.getRoomNumber());
										List<BedsInfo> bedsList = new ArrayList<>();
										Optional<List<Bed>> getBeds = bedrepo.findByroomId(room.getRoomId());
										if (getBeds.isPresent()) {
											getBeds.get().forEach(bed -> {
												BedsInfo newBed = new BedsInfo();
												newBed.setBedId(bed.getBedId());
												newBed.setBedStatus(bed.isBedStatus());
												newBed.setBuildingId(bed.getBuildingId());
												newBed.setDefaultRent(bed.getDefaultRent());
												newBed.setRoomId(bed.getRoomId());
												newBed.setAc(bed.isAc());
												newBed.setBedName(bed.getBedName());
												newBed.setBedNum(bed.getId());
												newBed.setSecurityDeposit(bed.getSecurityDeposit());
												newBed.setBuildingName(getBuilding.get().getBuilding_name());
												if(bed.isBedStatus()==false)
												{
													Guest listOfGuests=template.getForObject("http://guestService/guest/getGuestByBedId/" + newBed.getBedId(),Guest.class);
													//newBed.setGuest(listOfGuests);
													newBed.setGuestId(listOfGuests.getId());
													newBed.setGuestName(listOfGuests.getFirstName());
													bedsList.add(newBed);
												}
												else {
													bedsList.add(newBed);
												}		
											});
										}
										newRoom.setBeds(bedsList);
										roomList.add(newRoom);
									});
								}
								newFloor.setRooms(roomList);
								floorsList.add(newFloor);
							});
						}
						newBuild.setFloors(floorsList);
					}
					info.add(newBuild);
				});
			}
			return new ResponseEntity<>(info, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
	}

// Get beds by building Id

	@GetMapping(path = "/getBedsByBuildingId/{id}")
	public ResponseEntity<BuildingInfo> getByBuildingId(@PathVariable Integer id) {
		List<BuildingInfo> infoList = new ArrayList<>();

		BuildingInfo info = new BuildingInfo();
		Optional<Buildings> getBuilding = buildingRepo.findById(id);
		if (getBuilding.isPresent()) {
			info.setBuildingName(getBuilding.get().getBuilding_name());
			List<FloorsInfo> floorsList = new ArrayList<>();
			Optional<List<Floors>> getFloors = floorRepo.findByBuildingId(getBuilding.get().getBuilding_id());
			if (getFloors.isPresent()) {
				getFloors.get().forEach(floor -> {
					FloorsInfo newFloor = new FloorsInfo();
					newFloor.setFloorName(floor.getFloorNumber());
					List<RoomsInfo> roomList = new ArrayList<>();
					Optional<List<Rooms>> getRooms = roomRepo.findByFloorId(floor.getFloorId());
					if (getRooms.isPresent()) {
						getRooms.get().forEach(room -> {
							RoomsInfo newRoom = new RoomsInfo();
							newRoom.setRoomNumber(room.getRoomNumber());
							List<BedsInfo> bedsList = new ArrayList<>();
							Optional<List<Bed>> getBeds = bedrepo.findByroomId(room.getRoomId());
							if (getBeds.isPresent()) {
								getBeds.get().forEach(bed -> {
									BedsInfo newBed = new BedsInfo();
									newBed.setBuildingId(bed.getBuildingId());
									newBed.setRoomId(bed.getRoomId());
									newBed.setBedId(bed.getBedId());
									newBed.setBedName(bed.getBedName());
									newBed.setBedStatus(bed.isBedStatus());
									newBed.setDefaultRent(bed.getDefaultRent());
									newBed.setAc(bed.isAc());
									newBed.setBedNum(bed.getId());
									if(bed.isBedStatus()==false)
									{
										Guest listOfGuests=template.getForObject("http://guestService/guest/getGuestByBedId/" + newBed.getBedId(),Guest.class);
										//newBed.setGuest(listOfGuests);
										newBed.setGuestId(listOfGuests.getId());
										newBed.setGuestName(listOfGuests.getFirstName());
										bedsList.add(newBed);
									}
									else {
										bedsList.add(newBed);
									}
								});
							}
							newRoom.setBeds(bedsList);
							roomList.add(newRoom);
						});
					}
					newFloor.setRooms(roomList);
					floorsList.add(newFloor);
				});
			}
			info.setFloors(floorsList);
		}
		infoList.add(info);
		return new ResponseEntity(infoList, HttpStatus.OK);
	}

	// GET MAPPING API FOR AVAILABLE BEDS BY BUILDING ID

	@GetMapping(path = "/getAvailableBedsByBuildingId/{id}")
	public ResponseEntity<java.util.List<Bed>> buildingId(@PathVariable Integer id) {
		List<Bed> listbed = new ArrayList<>();
		Optional<Buildings> getBuilding = buildingRepo.findById(id);
		if (getBuilding.isPresent()) {
			List<FloorsInfo> floorsList = new ArrayList<>();
			Optional<List<Floors>> getFloors = floorRepo.findByBuildingId(getBuilding.get().getBuilding_id());
			if (getFloors.isPresent()) {
				getFloors.get().forEach(floor -> {
					Optional<List<Rooms>> getRooms = roomRepo.findByFloorId(floor.getFloorId());
					if (getRooms.isPresent()) {
						getRooms.get().forEach(room -> {
							List<BedsInfo> bedsList = new ArrayList<>();
							Optional<List<Bed>> getBeds = bedrepo.findByroomIdAndBedStatus(room.getRoomId(), true);
							if (getBeds.isPresent()) {
								getBeds.get().forEach(bed -> {
									BedsInfo newBed = new BedsInfo();
									newBed.setBedId(bed.getBedId());
									newBed.setBedName(bed.getBedName());
									newBed.setBedStatus(bed.isBedStatus());
									newBed.setRoomId(bed.getRoomId());
									newBed.setBedNum(bed.getId());
									newBed.setBuildingId(bed.getBuildingId());
									newBed.setAc(bed.isAc());
									newBed.setDefaultRent(bed.getDefaultRent());
									newBed.setSecurityDeposit(bed.getSecurityDeposit());
									newBed.setBuildingName(getBuilding.get().getBuilding_name());
									bedsList.add(newBed);
								});
							}
						});
					}
				});
			}
		}
		return new ResponseEntity<List<Bed>>(listbed, HttpStatus.OK);
	}

// GET MAPPING API FOR NOT AVAILABLE BEDS BY BUILDING ID

	@GetMapping(path = "/getNotAvailableBedsByBuildingId/{id}")
	public ResponseEntity<java.util.List<Bed>> getNotAvailableBedsByBuildingId(@PathVariable Integer id) {
		List<Bed> listbed = new ArrayList<>();
		Optional<Buildings> getBuilding = buildingRepo.findById(id);
		if (getBuilding.isPresent()) {
			List<FloorsInfo> floorsList = new ArrayList<>();
			Optional<List<Floors>> getFloors = floorRepo.findByBuildingId(getBuilding.get().getBuilding_id());
			if (getFloors.isPresent()) {
				getFloors.get().forEach(floor -> {
					Optional<List<Rooms>> getRooms = roomRepo.findByFloorId(floor.getFloorId());
					if (getRooms.isPresent()) {
						getRooms.get().forEach(room -> {
							List<BedsInfo> bedsList = new ArrayList<>();
							Optional<List<Bed>> getBeds = bedrepo.findByroomIdAndBedStatus(room.getRoomId(), false);
							if (getBeds.isPresent()) {
								getBeds.get().forEach(bed -> {
									BedsInfo newBed = new BedsInfo();
									newBed.setBedId(bed.getBedId());
									newBed.setBedName(bed.getBedName());
									newBed.setBedStatus(bed.isBedStatus());
									newBed.setRoomId(bed.getRoomId());
									newBed.setBedNum(bed.getId());
									newBed.setBuildingId(bed.getBuildingId());
									newBed.setAc(bed.isAc());
									newBed.setDefaultRent(bed.getDefaultRent());
									newBed.setSecurityDeposit(bed.getSecurityDeposit());
									newBed.setBuildingName(getBuilding.get().getBuilding_name());
									bedsList.add(newBed);
								});
							}
						});
					}
				});
			}
		}
		return new ResponseEntity<List<Bed>>(listbed, HttpStatus.OK);
	}

//    getApi for all buldings available beds

	@GetMapping(path = "/getAvailableBedsByBuildings")
	public ResponseEntity<List<BuildingModel>> getAvailableBedsByBuildings() {
		List<BuildingModel> info = new ArrayList<>();
		List<Buildings> getBuildings = buildingRepo.findAll();
		if (!getBuildings.isEmpty()) {
			getBuildings.forEach(building -> {
				BuildingModel newBuild = new BuildingModel();
				Optional<Buildings> getBuilding = buildingRepo.findById(building.getBuilding_id());
				if (getBuilding.isPresent()) {
					newBuild.setBuildingId(getBuilding.get().getBuilding_id());
					newBuild.setBuildingName(getBuilding.get().getBuilding_name());
					List<BedsInfo> bedsList = new ArrayList<>();
					Optional<List<Bed>> getBeds = bedrepo
							.findBybuildingIdAndBedStatus(getBuilding.get().getBuilding_id(), true);
					if (getBeds.isPresent()) {
						getBeds.get().forEach(bed -> {
							BedsInfo newBed = new BedsInfo();
							newBed.setBedId(bed.getBedId());
							newBed.setBedName(bed.getBedName());
							newBed.setBedStatus(bed.isBedStatus());
							newBed.setRoomId(bed.getRoomId());
							newBed.setBedNum(bed.getId());
							newBed.setBuildingId(bed.getBuildingId());
							newBed.setAc(bed.isAc());
							newBed.setDefaultRent(bed.getDefaultRent());
							newBed.setSecurityDeposit(bed.getSecurityDeposit());
							newBed.setBuildingName(getBuilding.get().getBuilding_name());
							bedsList.add(newBed);

						});
					}
					newBuild.setBeds(bedsList);
					info.add(newBuild);
				}
			});
		}
		return new ResponseEntity<>(info, HttpStatus.OK);
	}

//UPDATE API FOR BED STATUS AND GUEST ID BY BEDID

	@PutMapping("/updateBedStatusBydBedId")
	public void updateBedStatusByBedId(@RequestBody Bed bed) {
		Bed getBed = bedrepo.findByBedId(bed.getBedId());
		getBed.setGuestId(bed.getGuestId());
		getBed.setBedId(bed.getBedId());
		getBed.setBedStatus(!getBed.isBedStatus());
		bedrepo.save(getBed);
	}

//GET API FOR GETTING THE COUNT OF TOTAL BEDS AND OCCUPIED BEDS FOR RAT PIE CHART FOR ALL BUILDINGS

	@GetMapping(path = "/getBedSummaryForPieChartByAllBuildings")
	public ResponseEntity<List<NewBuildModel>> getAvailableBedsByBuilding() {
		List<NewBuildModel> info = new ArrayList<NewBuildModel>();
		List<Buildings> getBuildings = buildingRepo.findAll();
		if (!getBuildings.isEmpty()) {
			getBuildings.forEach(building -> {
				NewBuildModel newBuild = new NewBuildModel();
				Optional<Buildings> getBuilding = buildingRepo.findById(building.getBuilding_id());
				if (getBuilding.isPresent()) {
					newBuild.setBuildingId(getBuilding.get().getBuilding_id());
					newBuild.setBuildingName(getBuilding.get().getBuilding_name());
					List<Bed> bedsList = bedrepo.findAllByBuildingId(building.getBuilding_id());
					List<Bed> listbeds = bedsumRepo.findByBuildingIdAndBedStatus(building.getBuilding_id(), false);
					List<Bed> listOfAvailablebeds = bedsumRepo.findByBuildingIdAndBedStatus(building.getBuilding_id(),
							true);
					int bedsCount = bedsList.size();
					int occupiedBedsCount = listbeds.size();
					int totalAvailableBeds = listOfAvailablebeds.size();
					newBuild.setTotalBeds(bedsCount);
					newBuild.setOccupiedBeds(occupiedBedsCount);
					newBuild.setAvailableBeds(totalAvailableBeds);
					info.add(newBuild);
				}
			});
		}
		return new ResponseEntity<>(info, HttpStatus.OK);
	}

//GET API FOR GETTING THE COUNT OF TOTAL BEDS AND OCCUPIED BEDS FOR RAT PIE CHART BY BUILDING ID

	@GetMapping(path = "/getBedSummaryForPieChartByBuildingId/{buildingId}")
	public ResponseEntity<List<NewBuildModel>> getAvailableBedsByBuildingId(@PathVariable int buildingId) {
		List<NewBuildModel> info = new ArrayList<>();
		NewBuildModel newBuild = new NewBuildModel();
		Optional<Buildings> getBuilding = buildingRepo.findById(buildingId);
		if (getBuilding.isPresent()) {
			newBuild.setBuildingId(getBuilding.get().getBuilding_id());
			newBuild.setBuildingName(getBuilding.get().getBuilding_name());
			List<Bed> bedsList = bedrepo.findAllByBuildingId(buildingId);
			List<Bed> listbeds = bedsumRepo.findByBuildingIdAndBedStatus(buildingId, false);
			List<Bed> listOfAvailablebeds = bedsumRepo.findByBuildingIdAndBedStatus(buildingId, true);
			int bedsCount = bedsList.size();
			int occupiedBedsCount = listbeds.size();
			int totalAvailableBeds = listOfAvailablebeds.size();
			newBuild.setTotalBeds(bedsCount);
			newBuild.setOccupiedBeds(occupiedBedsCount);
			newBuild.setAvailableBeds(totalAvailableBeds);
			info.add(newBuild);
		}
		return new ResponseEntity<>(info, HttpStatus.OK);
	}

	@GetMapping("/getBedByBedId/{bedId}")
	public Bed getBedByBuildingId(@PathVariable String bedId) {
		Bed getBed = bedrepo.findByBedId(bedId);
		return getBed;
	}

	
	// Get api to get buildingid and buildingname
	@GetMapping(path="/getBuildingIdAndName")
	public ResponseEntity<List<BuildingId>> getBuildingIdAndName() {
		try {
			List<BuildingId> info = new ArrayList<BuildingId>();
			List<Buildings> getBuildings = buildingRepo.findAll();
			if (!getBuildings.isEmpty()) {
				getBuildings.forEach(building -> {
					BuildingId newBuild = new BuildingId();
					Optional<Buildings> getBuilding = buildingRepo.findById(building.getBuilding_id());
					if (getBuilding.isPresent()) {
						newBuild.setBuildingId(getBuilding.get().getBuilding_id());
						newBuild.setBuildingName(getBuilding.get().getBuilding_name());
						info.add(newBuild);
					}
				});
			}
			return new ResponseEntity<>(info, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Something went wrong", HttpStatus.OK);

		}
	}

}
