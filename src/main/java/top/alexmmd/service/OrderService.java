package top.alexmmd.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.alexmmd.domain.OrderDO;
import top.alexmmd.mapper.OrderMapper;

/**
 * @author 汪永晖
 */
import org.springframework.transaction.annotation.Propagation;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    @Transactional
    public void add(OrderDO order) {
        // 这里先假模假样的读取一下。读取从库
        OrderDO exists = orderMapper.selectById(1);
        System.out.println(exists);

        // 插入订单
        orderMapper.insert(order);

        // 这里先假模假样的读取一下。读取主库
        exists = orderMapper.selectById(1);
        System.out.println(exists);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }

}
