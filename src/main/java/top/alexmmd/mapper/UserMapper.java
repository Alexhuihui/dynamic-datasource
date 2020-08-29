package top.alexmmd.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.alexmmd.constant.DBConstants;
import top.alexmmd.domain.UserDO;

/**
 * @author 汪永晖
 */
@Repository
@DS(DBConstants.DATASOURCE_USERS)
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
