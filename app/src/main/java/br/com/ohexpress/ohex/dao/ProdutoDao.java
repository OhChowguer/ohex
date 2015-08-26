package br.com.ohexpress.ohex.dao;

import java.sql.SQLException;

import br.com.ohexpress.ohex.model.Produto;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ProdutoDao extends BaseDaoImpl<Produto, Integer> {
	public ProdutoDao(ConnectionSource cs) throws SQLException{
		super(Produto.class);
		setConnectionSource(cs);
		initialize();
	}
}
