package com.pda.practice.user;

import com.pda.practice.exception.DuplicatedKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {

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

        return user;
    }

    public User findById(String userId) throws Exception {
        log.info("{}", userTable.get(userId));
        return userTable.get(userId);
    }
}
