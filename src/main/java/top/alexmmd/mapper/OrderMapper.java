package top.alexmmd.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.alexmmd.constant.DBConstants;
import top.alexmmd.domain.OrderDO;

/**
 * @author 汪永晖
 */
@Repository
public interface OrderMapper {

    @DS(DBConstants.DATASOURCE_SLAVE)
    OrderDO selectById(@Param("id") Integer id);

    @DS(DBConstants.DATASOURCE_MASTER)
    int insert(OrderDO entity);

}
