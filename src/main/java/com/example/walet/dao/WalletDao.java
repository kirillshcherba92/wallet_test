package com.example.walet.dao;

import com.example.walet.model.WalletModel;
import com.example.walet.properties.DatasourceTableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableConfigurationProperties()
public class WalletDao implements BaseDao<WalletModel, UUID> {

    private final Logger LOGGER = LoggerFactory.getLogger(WalletDao.class);

    private String tableForQuery;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WalletDao(NamedParameterJdbcTemplate jdbcTemplate, DatasourceTableProperties tableProperties) {
        this.jdbcTemplate = jdbcTemplate;
        tableForQuery = tableProperties.getTable().getName();
    }

    @Override
    public void create(final WalletModel walletModel) {
        final MapSqlParameterSource allParametersOfWallet = createAllParametersOfWallet(walletModel);
        String sql = String.format("INSERT INTO %s (id, amount) VALUES(:id, :amount)", tableForQuery);
        jdbcTemplate.update(sql, allParametersOfWallet);
    }

    @Override
    public Optional<WalletModel> findById(final UUID uuid) {
        MapSqlParameterSource paramsOfSqlQuery = new MapSqlParameterSource();
        paramsOfSqlQuery.addValue("id", uuid.toString());
        String sql = String.format("SELECT * from %s where id=:id", tableForQuery);
        WalletModel walletModel = null;
        try {
            walletModel = jdbcTemplate.queryForObject(sql, paramsOfSqlQuery, new WalletMapper());
        } catch (DataAccessException dataAccessException) {
            LOGGER.warn("WalletModel with UUID: \"{}\" not found", uuid);
        }

        return Optional.ofNullable(walletModel);
    }

    @Override
    public List<UUID> findAllId() {
        String sql = String.format("SELECT id from %s", tableForQuery);
        List<String> walletIdList = jdbcTemplate.query(sql, rs -> {
            List<String> idList = new ArrayList<>();
            while (!rs.next()) {
                idList.add(rs.getString(1));
            }
            return idList;
        });
        return walletIdList.stream().map(UUID::fromString).toList();
    }

    @Override
    public List<WalletModel> findAll() {
        String sql = String.format("SELECT * from %s", tableForQuery);
        return jdbcTemplate.query(sql, new WalletMapper());
    }

    @Override
    public void update(final WalletModel walletModel) {
        final MapSqlParameterSource allParametersOfWallet = createAllParametersOfWallet(walletModel);
        String sql = String.format("UPDATE %s SET amount=:amount where id=:id", tableForQuery);
        jdbcTemplate.update(sql, allParametersOfWallet);
    }

    public void update(final UUID id, final int newAmount) {
        final MapSqlParameterSource paramsOfSqlQuery = new MapSqlParameterSource();
        paramsOfSqlQuery.addValue("id", id.toString());
        paramsOfSqlQuery.addValue("amount", newAmount);
        String sql = String.format("UPDATE %s SET amount=:amount where id=:id", tableForQuery);
        jdbcTemplate.update(sql, paramsOfSqlQuery);
    }

    private MapSqlParameterSource createAllParametersOfWallet(final WalletModel walletModel) {
        MapSqlParameterSource paramsOfSqlQuery = new MapSqlParameterSource();
        paramsOfSqlQuery.addValue("id", walletModel.getWalletId());
        paramsOfSqlQuery.addValue("amount", walletModel.getAmount());
        return paramsOfSqlQuery;
    }
}

class WalletMapper implements RowMapper<WalletModel> {
    @Override
    public WalletModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WalletModel(
                rs.getString(1),
                rs.getInt(2));
    }
}
