package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        order.setOrderId(IdGenerator.getOrderId());
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return Storage.orders
                .stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = get(order.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find order to update"));
        orderToUpdate.setUserId(order.getUserId());
        orderToUpdate.setItems(order.getItems());
        return orderToUpdate;
    }

    @Override
    public boolean deleteById(Long orderId) {
        Optional<Order> orderToDelete = get(orderId);
        orderToDelete.ifPresent(this::delete);
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }
}