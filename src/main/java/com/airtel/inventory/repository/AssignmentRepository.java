package com.airtel.inventory.repository;

import com.airtel.inventory.model.Assignment;
import com.airtel.inventory.model.Device;
import com.airtel.inventory.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByDeviceId(Long deviceId);
    
    List<Assignment> findByEmployeeId(Long employeeId);
    
    @Query("SELECT a FROM Assignment a WHERE a.returnDate IS NULL")
    List<Assignment> findActiveAssignments();
    
    @Query("SELECT a FROM Assignment a WHERE a.device.id = :deviceId AND a.returnDate IS NULL")
    Optional<Assignment> findActiveAssignmentByDevice(@Param("deviceId") Long deviceId);
    
    @Query("SELECT a FROM Assignment a WHERE a.employee.id = :employeeId AND a.returnDate IS NULL")
    List<Assignment> findActiveAssignmentsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.returnDate IS NULL")
    long countActiveAssignments();
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.returnDate IS NULL")
    long countByReturnDateIsNull();
    
    @Query("SELECT a FROM Assignment a WHERE a.device = :device")
    List<Assignment> findByDevice(@Param("device") Device device);
    
    @Query("SELECT a FROM Assignment a WHERE a.employee = :employee")
    List<Assignment> findByEmployee(@Param("employee") Employee employee);
    
    @Query("SELECT a FROM Assignment a WHERE a.device = :device AND a.returnDate IS NULL")
    Optional<Assignment> findByDeviceAndReturnDateIsNull(@Param("device") Device device);
    
    @Query("SELECT a FROM Assignment a WHERE a.employee = :employee AND a.returnDate IS NULL")
    List<Assignment> findByEmployeeAndReturnDateIsNull(@Param("employee") Employee employee);
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.device = :device")
    long countByDevice(@Param("device") Device device);
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.employee = :employee")
    long countByEmployee(@Param("employee") Employee employee);
    
    @Query("SELECT a FROM Assignment a WHERE a.device = :device ORDER BY a.assignedDate DESC")
    List<Assignment> findByDeviceOrderByAssignedDateDesc(@Param("device") Device device);
    
    @Query("SELECT a FROM Assignment a WHERE a.employee = :employee ORDER BY a.assignedDate DESC")
    List<Assignment> findByEmployeeOrderByAssignedDateDesc(@Param("employee") Employee employee);
}
