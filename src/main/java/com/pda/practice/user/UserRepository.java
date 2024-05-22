package com.pda.practice.user;

import com.pda.practice.entity.User;
import com.pda.practice.exception.DuplicatedKeyException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {

    @Autowired
    DataSource dataSource;

    @Autowired
    EntityManager entityManager;
//    EntityManagerFactory entityManagerFactory;

    private Map<String, UserDto> userTable;
    private int increment;

    public UserRepository() {
        this.increment = 1;
//        this.userTable = new HashMap<>() {{
//            put("podif", UserDto.builder().userId("podif").password("1234").name("tester").email("podif@aa.com").contact("1234").build());
//        }};
    }

    /**
     * create user
     * @param signupReqDto requires id, password, email, name, contact
     * @return created user
     * @throws DuplicatedKeyException user already exists
     */
    public void create(UserDto.SignupReqDto signupReqDto) throws DuplicatedKeyException {
        // map
//        if (userTable.get(signupReqDto.getUserId()) != null)
//            throw new DuplicatedKeyException("Given user id already exists");
//
//        User user = User.builder()
//                .userId(signupReqDto.getUserId())
//                .password(signupReqDto.getPassword())
//                .email(signupReqDto.getEmail())
//                .name(signupReqDto.getName())
//                .contact(signupReqDto.getContact())
//                .build();
//
//        log.debug("created user : {}", user.getUserId());
//        userTable.put(user.getUserId(), user);

        // conn
//        try(Connection conn = dataSource.getConnection()) {
//            log.info("DB Connected : {}", conn);
//
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO `user` (`user_id`, `password`, `name`, `email`, `contact`) VALUES (?, ?, ?, ?, ?)");
//            ps.setString(1, signupReqDto.getUserId());
//            ps.setString(2, signupReqDto.getPassword());
//            ps.setString(3, signupReqDto.getName());
//            ps.setString(4, signupReqDto.getEmail());
//            ps.setString(5, signupReqDto.getContact());
//
//            ps.execute();
//            ps.close();
//        } catch (Exception e) {
//            log.info("failed to create db connection : {}", e.getMessage());
//        }

        User user = User.builder()
                .userId(signupReqDto.getUserId())
                .password(signupReqDto.getPassword())
                .email(signupReqDto.getEmail())
                .name(signupReqDto.getName())
                .contact(signupReqDto.getContact())
                .build();


//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction txm = entityManager.getTransaction();

//        txm.begin();
        entityManager.persist(user);

//        System.out.println(user.getId());

//        txm.commit();
//        System.out.println(user.getId());
//        System.out.println(entityManager.find(User.class, 1).getId());

        log.info("created entity with id : {}", user.getId());
    }

    public User findByUserId(String userId) throws Exception {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();

        log.info("Searching for userId : {}", userId);

//        return userTable.get(userId);
        User user = entityManager.createQuery("SELECT u FROM User AS u WHERE userId= :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        log.info("Retrieved from DB : {}", user);
        return user;
    }
}
