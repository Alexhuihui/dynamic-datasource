package top.alexmmd.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.alexmmd.domain.OrderDO;

/**
 * @author 汪永晖
 */
@Repository
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);

    int insert(OrderDO entity);

}