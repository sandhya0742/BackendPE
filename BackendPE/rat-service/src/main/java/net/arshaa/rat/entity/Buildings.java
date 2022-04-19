package net.arshaa.rat.entity;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import javax.persistence.*;



@Entity
public class Buildings {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int building_id;
@Column
private String building_name;
@Column
private String createdBy;
@Column
@Temporal(TemporalType.DATE)
private java.util.Date createdOn= new java.util.Date(System.currentTimeMillis());
public int getBuilding_id() {
return building_id;
}
public void setBuilding_id(int building_id) {
this.building_id = building_id;
}
public String getBuilding_name() {
return building_name;
}
public void setBuilding_name(String building_name) {
this.building_name = building_name;
}
public String getCreatedBy() {
return createdBy;
}
public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}
public java.util.Date getCreatedOn() {
return createdOn;
}
public void setCreatedOn(java.util.Date createdOn) {
this.createdOn = createdOn;
}
public Buildings(int building_id, String building_name, String manager_name, long phone_number, String createdBy,
Date createdOn) {
super();
this.building_id = building_id;
this.building_name = building_name;
this.createdBy = createdBy;
this.createdOn = createdOn;
}
public Buildings() {
super();
// TODO Auto-generated constructor stub
}

}