package com.mzl.springbootstudentsystem.repository;

import com.mzl.springbootstudentsystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @InterfaceName :   StudentRepository
 * @Description: 学生jpa存储库
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:29
 * @Version: 1.0
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

     法1：
     //必须设置countQuery字段
     @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1 ORDER BY ?#{#pageable}",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
    Page<User> findByLastname(String lastname, Pageable pageable);

    法2：
    // 必须增加'countQuery = "select count(*) from XDM_Review_time where c_appointment_day = ?1 and    c_appointment_day = ?2 "'
   @Query(nativeQuery = true,
        value = "select a.* , b.c_clientname as clientName , b.c_phone1 as clientPhone from XDM_Review_time a left join     client_list b on a.c_clientid = b.id where a.c_clientid = ?1 and a.c_appointment_day = ?2 ",
        countQuery = "select count(*) from XDM_Review_time where c_appointment_day = ?1 and c_appointment_day     = ?2 ")
    Page<List<Map<String, Object>>> findAllReviewsByClientIdAndDay(String clientId, String day, Pageable     pageable);

    法3：
    @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1 ORDER BY ?#{#pageable}",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
    Page<User> findByLastname(String lastname, Pageable pageable);

    
   法4:
 @Query(value="SELECT * FROM User WHERE ENDTIME is null AND  ID = ?1 order by ?#{#pageable}",nativeQuery    = true)
  List<User> findByEndtimeIsNullAndIdOrderByStarttime(String id,  Pageable pageable);

  法5：
    @Query(value = "select * from user_liveapp where user_Id = :userId ORDER BY ?#{#pageable}", nativeQuery = true)
Page<UserLiveapp> search(@Param("userId") Long userId, Pageable pageable);

  法6：	
@Query(value = "select * from user_liveapp where user_Id = :userId ORDER BY ?#{#pageable}", 
	countQuery="select count(*) from user_liveapp where user_Id = :userId",
	nativeQuery = true)
Page<UserLiveapp> search1(@Param("userId") Long userId, Pageable pageable);

}
