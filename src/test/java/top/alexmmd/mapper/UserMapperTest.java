package top.alexmmd.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.alexmmd.DynamicDatasourceApplication;
import top.alexmmd.domain.UserDO;
import top.alexmmd.mapper.users.UserMapper;

/**
 * @author 汪永晖
 */
@SpringBootTest(classes = DynamicDatasourceApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }
}
