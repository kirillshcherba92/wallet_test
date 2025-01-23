package com.example.walet.dao;

import com.example.walet.model.WalletOperationModel;
import com.example.walet.properties.DatasourceTableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableConfigurationProperties
public class WalletOperationDao implements BaseDao<WalletOperationModel, Integer>{

//    @Value("${datasource.table.logName}")
    private String tableForQuery;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WalletOperationDao(NamedParameterJdbcTemplate jdbcTemplate, DatasourceTableProperties tableProperties) {
        this.jdbcTemplate = jdbcTemplate;
        tableForQuery = tableProperties.getTable().getLogName();
    }

    @Override
    public void create(WalletOperationModel walletOperationModel) {
        final MapSqlParameterSource allParametersOfWallet = createAllParametersOfWallet(walletOperationModel);
        String sql = String.format("INSERT INTO %s (operation_type, amount, id_wallet) VALUES(:operation_type, :amount, :id_wallet)", tableForQuery);
        jdbcTemplate.update(sql, allParametersOfWallet);
    }

    @Override
    public Optional<WalletOperationModel> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Integer> findAllId() {
        return null;
    }

    @Override
    public List<WalletOperationModel> findAll() {
        return null;
    }

    @Override
    public void update(WalletOperationModel walletOperationModel) {

    }

    private MapSqlParameterSource createAllParametersOfWallet(final WalletOperationModel walletOperationModel) {
        MapSqlParameterSource paramsOfSqlQuery = new MapSqlParameterSource();
        paramsOfSqlQuery.addValue("operation_type", walletOperationModel.getOperationType().getName());
        paramsOfSqlQuery.addValue("amount", walletOperationModel.getAmount());
        paramsOfSqlQuery.addValue("id_wallet", walletOperationModel.getWalletId());
        return paramsOfSqlQuery;
    }
}
