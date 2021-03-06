﻿package cn.jbit.easybuy.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jbit.easybuy.entity.Order;
import cn.jbit.easybuy.entity.Pager;
import cn.jbit.easybuy.entity.Product;
import cn.jbit.easybuy.entity.ShoppingCart;
import cn.jbit.easybuy.entity.User;

public interface OrderService {
	void payShoppingCart(ShoppingCart cart, User user,String adress);//购物

	Order findById(String parameter);//根据ID获取订单

	void delete(String id);//删除订单

	void saveOrder(Order order);//保存订单

	void updateOrder(Order order);//更新订单

	long getOrderRowCount(String condition);//获取订单记录条数

	Map <Order,ArrayList<Product>> getOrders(String condition, Pager pager);//获取多条订单并带有详情

}
