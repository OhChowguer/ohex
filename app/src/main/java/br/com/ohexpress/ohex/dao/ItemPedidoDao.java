package br.com.ohexpress.ohex.dao;

import java.sql.SQLException;

import br.com.ohexpress.ohex.model.ItemPedido;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ItemPedidoDao extends BaseDaoImpl<ItemPedido, Integer> {
	public ItemPedidoDao(ConnectionSource cs) throws SQLException{
		super(ItemPedido.class);
		setConnectionSource(cs);
		initialize();
	}
}
