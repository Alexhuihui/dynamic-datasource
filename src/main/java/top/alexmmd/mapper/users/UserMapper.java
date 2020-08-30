package top.alexmmd.mapper.users;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.alexmmd.domain.UserDO;

/**
 * @author 汪永晖
 */
@Repository
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
