package top.alexmmd.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.alexmmd.constant.DBConstants;
import top.alexmmd.domain.OrderDO;
import top.alexmmd.mapper.OrderMapper;

/**
 * @author 汪永晖
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    @Transactional
    @DS(DBConstants.DATASOURCE_MASTER)
    public void add(OrderDO order) {
        // 这里先假模假样的读取一下
        orderMapper.selectById(order.getId());

        // 插入订单
        orderMapper.insert(order);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }

}
