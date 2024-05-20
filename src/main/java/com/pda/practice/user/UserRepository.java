package com.pda.practice.user;

import com.pda.practice.exception.DuplicatedKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {

    @Autowired
    DataSource dataSource;

    private Map<String, User> userTable;
    private int increment;

    public UserRepository() {
        this.increment = 1;
        this.userTable = new HashMap<>() {{
            put("podif", User.builder().userId("podif").password("1234").name("tester").email("podif@aa.com").contact("1234").build());
        }};
    }

    /**
     * create user
     * @param signupReqDto requires id, password, email, name, contact
     * @return created user
     * @throws DuplicatedKeyException user already exists
     */
    public User create(User.SignupReqDto signupReqDto) throws DuplicatedKeyException {
        if (userTable.get(signupReqDto.getUserId()) != null)
            throw new DuplicatedKeyException("Given user id already exists");

        User user = User.builder()
                .userId(signupReqDto.getUserId())
                .password(signupReqDto.getPassword())
                .email(signupReqDto.getEmail())
                .name(signupReqDto.getName())
                .contact(signupReqDto.getContact())
                .build();

        log.debug("created user : {}", user.getUserId());
        userTable.put(user.getUserId(), user);

        // conn
        try(Connection conn = dataSource.getConnection()) {
            log.info("DB Connected : {}", conn);

            PreparedStatement ps = conn.prepareStatement("INSERT INTO `user` (`user_id`, `password`, `name`, `email`, `contact`) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, signupReqDto.getUserId());
            ps.setString(2, signupReqDto.getPassword());
            ps.setString(3, signupReqDto.getName());
            ps.setString(4, signupReqDto.getEmail());
            ps.setString(5, signupReqDto.getContact());

            ps.execute();
            ps.close();
        } catch (Exception e) {
            log.info("failed to create db connection : {}", e.getMessage());
        }

        return user;
    }

    public User findById(String userId) throws Exception {
        log.info("{}", userTable.get(userId));
        return userTable.get(userId);
    }
}
