package cn.jbit.easybuy.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jbit.easybuy.entity.Order;
import cn.jbit.easybuy.entity.OrderDetail;
import cn.jbit.easybuy.entity.Pager;
import cn.jbit.easybuy.entity.Product;

public interface OrderDao {

	void saveOrder(Order order) throws SQLException;//保存订单

	void saveOrderDetail(OrderDetail detail, int orderId) throws SQLException;//保存订单详情

	void deleteOrder(long id) throws SQLException;//删除订单

	void deleteOrderDetails(long id) throws SQLException;//删除订单详情

	Order findById(int id) throws SQLException;//根据ID获取订单

	void updateOrder(Order order) throws SQLException;//更新订单

	long getOrderRowCount(String condition) throws SQLException;//获取订单共有多少条记录

	//获取订单及其下属的详情记录
	Map <Order,ArrayList<Product>> getOrders(String condition, Pager pager) throws SQLException;

}
